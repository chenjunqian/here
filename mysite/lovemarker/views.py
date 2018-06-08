#coding=utf-8
from django.shortcuts import render
from django.http import HttpResponse
from models import User, Post, PostTag, Report, ReportIssue
from utils.Model import HttpResultResponse, ErrorMessage
from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
from rest_framework.views import APIView
from rest_framework.renderers import JSONRenderer
from django.shortcuts import get_object_or_404
from django_redis import get_redis_connection
from django.core.cache.backends.base import DEFAULT_TIMEOUT
from django.core.cache import cache
from django.core import serializers
from serializers import PostSerializer, UserSerializer, PostTagSerializer, ReportSerializer, ReportIssueSerializer
from utils import util, SQLUtils

CACHE_TTL = getattr(settings, 'CACHE_TTL', DEFAULT_TIMEOUT)

# Create your views here.
def index(request):
	return HttpResponse(u"This is lovemarker")

# 注册
@api_view(['POST'])
def register(request):
	httpResultResponse = HttpResultResponse()
	if request.method == 'POST':
		username = request.POST.get('username')
		password = request.POST.get('password')
		gender = request.POST.get('gender')
		pushKey = request.POST.get('pushKey')
		birthday = request.POST.get('birthday')
		nickname = request.POST.get('nickname')
		if username and password :
			try:
				user = User.objects.get(username = username,password = password)
			except User.DoesNotExist:
				user = None

			if user:
				errors = { 
					'error_code': "8002",
					'error': 'POST_FAILED',
					'message': ErrorMessage.USERNAME_IS_EXIST,
				}
				return Response(errors,status=status.HTTP_400_BAD_REQUEST)
			else:
				httpResultResponse.errorMessage = ErrorMessage.REGISTER_SUCCESS
				httpResultResponse.status = "0"
				# 将用户数据存入数据库
                newUser = User.objects.create(
					username=username, password=password, gender=gender, 
					pushKey=pushKey, birthday=birthday, nickname=nickname
				)
				newUser.save()
				# 注册环信账号
                util.registerEMChat(userid,password)

				serializer = UserSerializer(newUser)
				return Response(serializer.data)
		else:
    		errors = { 
				'error_code': '8001',
				'error': 'POST_FAILED',
				'message': ErrorMessage.USERNAME_OR_PASSWORD_INVALID,
			}
			return Response(errors,status=status.HTTP_400_BAD_REQUEST)

@api_view(['POST'])
def login(request):
    httpResultResponse = HttpResultResponse()
    if request.method == 'POST':
		username = request.POST.get('username')
		password = request.POST.get('password')
		gender = request.POST.get('gender')
		pushKey = request.POST.get('pushKey')
		user = User.objects.get(username = username, password = password)
		if user:
			user.pushKey = pushKey
			user.save()
			serializer = UserSerializer(user)
			return Response(serializer.data)
		else:
			errors = { 
				'error_code': '8003',
				'error': 'POST_FAILED',
				'message': ErrorMessage.NO_SUCH_USER_OR_PASSWORD_IS_INVALID,
			}
			return Response(errors,status=status.HTTP_400_BAD_REQUEST)

@api_view(['POST'])
def checkUserIsExist(request):
	if request.method == 'POST':
		username = request.POST.get('username')
		if username:
    		try:
				user = User.objects.get(username = username)
			except User.DoesNotExist:
				errors = { 
					'error_code': '8003',
					'error': 'POST_FAILED',
					'message': User.DoesNotExist,
				}
				return Response(errors,status=status.HTTP_404_NOT_FOUND)

			return Response(user, status=0)
		else:
			errors = { 
				'error_code': '8001',
				'error': 'POST_FAILED',
				'message': ErrorMessage.USERNAME_IS_NULL,
			}
			return Response(data=errors,status=status.HTTP_404_NOT_FOUND)


@api_view(['POST'])	
def uploadAvatar(request):
	if request.method == 'POST':
		username = request.POST.get('username')
		headImg = request.FILES.get('file')
		try:
			user = User.objects.get(username = username)
		except User.DoesNotExist:
			return Response(status=status.HTTP_404_NOT_FOUND)

		serializer = UserSerializer(user)
		if username:
			user = User.objects.get(username = username)
			if headImg:
				user.avatar = headImg
				user.save()
				return Response(serializer, status=status.HTTP_201_CREATED)
			else:
    			errors = { 
					'error_code': status.HTTP_400_BAD_REQUEST,
					'error': 'AVATAR_IS_INVALID',
					'message': ErrorMessage.AVATAR_IS_INVALID,
				}
				return Response(errors, status=status.HTTP_400_BAD_REQUEST)
		else:
			errors = { 
				'error_code': status.HTTP_400_BAD_REQUEST,
				'error': 'USERNAME_IS_NULL',
				'message': ErrorMessage.USERNAME_IS_NULL,
			}
    		return Response(errors, status=status.HTTP_400_BAD_REQUEST)
    
@api_view(['POST'])		
def modifyUserInfo(request):
    	
	if request.method == 'POST':
		serializer = UserSerializer(data=request.data)
		if serializer.is_valid():
    		serializer.save()
			return Response(serializer.data, status=status.HTTP_201_CREATED)
		return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)


# 用户发帖上传位置信息以及内容
@api_view(['POST'])
def updateUserPostLocation(request):
    	
	if request.method == 'POST':
    	longitude = request.POST.get('longitude')
    	latitude = request.POST.get('latitude')
    	city = request.POST.get('city')
    	cityCode = request.POST.get('cityCode')
    	address = request.POST.get('address')
    	username = request.POST.get('username')
    	tag = request.POST.get('username')
		try:

			post = Post(
				longitude=longitude,
				latitude=latitude,
				city=city,
				cityCode=cityCode,
				address=address,
				username=username,
				tag=tag,

			)
			post.save()
			serializer = PostSerializer(post)
    		redis = get_redis_connection()
			json_serializer = JSONRenderer().render(serializer.data)
			redis.hmset('Post',post.id, json_serializer)
    		serializer.save()
			return Response(serializer.data, status=status.HTTP_201_CREATED)
		except Post.ProtectedError as e:
			errors = { 
				'error_code': '8003',
				'error': 'POST_FAILED',
				'message': e,
			}
			return Response(errors,status=status.HTTP_404_NOT_FOUND)


# 获取发帖的标签
@api_view(['GET'])
def getPostTag(request):
	if request.method == 'GET':
    	postId = request.GET.get('postId')
		
		redis = get_redis_connection()
		postTagCache = redis.hmget('PostTag',postId)
		if postTagCache:
    		serializer = PostTagSerializer(postTagCache)
			return Response(serializer.data, status=status.HTTP_200_OK)
		else:
			try:
				tags = PostTag.objects.filter(postId = postId)
				serializer = PostTagSerializer(tags)
				json_serializer = JSONRenderer().render(serializer.data)
				redis.hmset('PostTag',postId, json_serializer)
				return Response(serializer.data, status=status.HTTP_200_OK)
			except PostTag.DoesNotExist as e:
				errors = { 
					'error_code': '8003',
					'error': 'POST_FAILED',
					'message': e,
				}
				return Response(errors,status=status.HTTP_404_NOT_FOUND)


# 获取用户的帖子
@api_view(['GET'])
def getUserPost(request):
	if request.method == 'GET':
		username = request.GET.get('username')

		try:
			posts = Post.objects.filter(username=username)
			serializer = PostSerializer(posts)
			return Response(serializer.data, status=status.HTTP_200_OK)
		except PostTag.DoesNotExist as e:
			errors = { 
				'error_code': '8003',
				'error': 'POST_FAILED',
				'message': e,
			}
			return Response(errors,status=status.HTTP_404_NOT_FOUND)


# 根据用户的地理经纬度，来获取他周围的定位信息
@api_view(['GET'])
def getLocationByLocation(request):
	if request.method == 'GET':
		longitude = request.GET.get('longitude')
		latitude = request.GET.get('latitude')
		city = request.GET.get('city')
		index = request.GET.get('index')

		try:
			posts = Post.objects.filter(
				longitude__gte=float(longitude)-0.05, longitude__lte=float(longitude)-0.05,
				latitude__gte=float(longitude)-0.05, latitude__lte=float(longitude)-0.05,
				city=city
			)

			serializer = PostSerializer(posts)
			return Response(serializer.data, status=status.HTTP_200_OK)
		except PostTag.DoesNotExist as e:
			errors = { 
				'error_code': '8003',
				'error': 'POST_FAILED',
				'message': e,
			}
			return Response(errors,status=status.HTTP_404_NOT_FOUND)



# 根据用户名获取用户信息
@api_view(['GET'])
def getUserInfoByUsername(request):
	if request.method == 'GET':
		username = request.GET.get('username')
		try:
			user = User.objects.get(username=username)
			serializer = UserSerializer(user)
			return Response(serializer.data, status=status.HTTP_200_OK)
		except User.DoesNotExist as e:
			errors = { 
				'error_code': '8003',
				'error': 'POST_FAILED',
				'message': e,
			}
			return Response(errors,status=status.HTTP_404_NOT_FOUND)


# 根据用户id获取用户信息
@api_view(['GET'])
def getUserInfoByUseId(request):
	if request.method == 'GET':
	userid = request.GET.get('userid')
	try:
		user = User.objects.get(userid=userid)
		serializer = UserSerializer(user)
		return Response(serializer.data, status=status.HTTP_200_OK)
	except User.DoesNotExist as e:
		errors = { 
			'error_code': '8003',
			'error': 'POST_FAILED',
			'message': e,
		}
		return Response(errors,status=status.HTTP_404_NOT_FOUND)


@api_view(['POST'])
def getPostByUsername(request):
	if request.method == 'GET':
		username = request.GET.get('username')
		try:
			posts = Post.objects.filter(username=username)
			serializer = PostSerializer(posts)
			return Response(serializer.data, status=status.HTTP_200_OK)
		except Post.DoesNotExist as e:
			errors = { 
				'error_code': '8003',
				'error': 'POST_FAILED',
				'message': e,
			}
			return Response(errors,status=status.HTTP_404_NOT_FOUND)


# 根据时间获取前一小时的帖子
@api_view(['GET'])
def getCurrentPost(request):
	dict = {}
	resultData = {}
	if request.method == 'GET':
		time = request.GET.get('time')
		index = request.POST.get('index')
		targetTime = int(time) - 60*60*1000
		targetIndex = int(index)


		posts = Post.objects.filter(
			created__gte=targetTime,
		).order_by['-id'][targetIndex-20:targetIndex]

		# 如果最近一小时没有帖子，则选取最近时间的数据
		if  posts:
    		serializer = PostSerializer(posts)
			return Response(serializer.data, status=status.HTTP_200_OK)
		else:
    		posts = Post.objects.all().order_by['-id'][targetIndex-20:targetIndex]
			if posts:
        		serializer = PostSerializer(posts)
				return Response(serializer.data, status=status.HTTP_200_OK)
			else:
        		errors = { 
					'error_code': '8003',
					'error': 'POST_FAILED',
					'message': 'post_not_found',
				}
				return Response(errors,status=status.HTTP_404_NOT_FOUND)
    		
    	
@api_view(['DELETE'])	
def deletePostById(request):
	if request.method == 'DELETE':
		postId = request.DELETE.get('postid')
		username = request.POST.get('username')
		try:
			post = Post.objects.get(id=postId,username=username).delete()
			serializer = PostSerializer(post, status=status.HTTP_200_OK)
			return Response(serializer.data)
		except Post.ProtectedError as e:
			errors = { 
				'error_code': '8003',
				'error': 'DELETE_FAILED',
				'message': e,
			}
			return Response(errors,status=status.HTTP_404_NOT_FOUND)

@api_view(['POST'])
def reportPost(request):
	if request.method == 'POST':
		content = request.POST.get('content')
		time = request.POST.get('time')
		postid = request.POST.get('postid')
		reporter = request.POST.get('reporter')
		try:
			report = Report(
				content=content,
				postId=postid,
				reporterId=reporter
			)
			serializer = ReportSerializer(report)
			return Response(serializer.data,status=status.HTTP_201_CREATED)
		except ValueError as e:
			errors = { 
				'error_code': '8003',
				'error': 'DELETE_FAILED',
				'message': e,
			}
			return Response(errors,status=status.HTTP_400_BAD_REQUEST)

@api_view(['POST'])
def reportIssue(request):
	if request.method == 'POST':
		content = request.POST.get('content')
		reporter = request.POST.get('reporter')
		try:
			reportIssue = ReportIssue(
				content=content,
				reporterId=reporter,
			)
			serializer = ReportIssueSerializer(report)
			return Response(serializer.data,status=status.HTTP_201_CREATED)
		except ValueError as e:
			errors = { 
				'error_code': '8003',
				'error': 'DELETE_FAILED',
				'message': e,
			}
			return Response(errors,status=status.HTTP_400_BAD_REQUEST)
		
