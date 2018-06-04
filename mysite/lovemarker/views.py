#coding=utf-8
from django.shortcuts import render
from django.http import HttpResponse
from models import User, Post, PostTag, Report, ReportIssue
from utils.Model import HttpResultResponse, ErrorMessage
from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
from rest_framework.views import APIView
from django.shortcuts import get_object_or_404
from django.db import connection
from django.core import serializers
from serializers import PostSerializer, UserSerializer
from utils import util, SQLUtils

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
		serializer = PostSerializer(data=request.data)
		if serializer.is_valid():
    		serializer.save()
			return Response(serializer.data, status=status.HTTP_201_CREATED)
		return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
