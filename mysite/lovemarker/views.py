#coding=utf-8
from django.shortcuts import render
from django.http import HttpResponse
from models import User
from Model import HttpResultResponse, ErrorMessage
from django.shortcuts import get_object_or_404
from django.db import connection
from django.core import serializers
import util
import SQLUtils

# Create your views here.
def index(request):
	return HttpResponse(u"This is lovemarker")

# 注册
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
				httpResultResponse.errorMessage = ErrorMessage.USERNAME_IS_EXIST
				httpResultResponse.status = "8002"
			else:
				httpResultResponse.errorMessage = ErrorMessage.REGISTER_SUCCESS
				httpResultResponse.status = "0"
				# 将用户数据存入数据库
                newUser = User.objects.create(username=username, password=password, gender=gender, pushKey=pushKey, birthday=birthday, nickname=nickname)
				# 注册环信账号
                newCursor = connection.cursor()
                newQuery = "select * from here_user where username = %s"
                newCursor.execute(newQuery,[username])
                newRes = newCursor.fetchall()
                userid = newRes[0][0]
                util.registerEMChat(userid,password)
				# 返回客户端用户数据
                useJson = serializers.serialize("json",newUser)
                httpResultResponse.resultData = useJson
		else:
			httpResultResponse.errorMessage = ErrorMessage.USERNAME_OR_PASSWORD_INVALID
			httpResultResponse.status = "8001"

        json = httpResultResponse.getJsonResult()
        return HttpResponse(json)
    else:
		httpResultResponse.errorMessage = ErrorMessage.POST_FAILED
		httpResultResponse.status = "8001"
		return HttpResponse(ErrorMessage.POST_FAILED)

def login(request):
    httpResultResponse = HttpResultResponse()
    if request.method == 'POST':
		username = request.POST.get('username')
		password = request.POST.get('password')
		gender = request.POST.get('gender')
		pushKey = request.POST.get('pushKey')
		user = User.objects.get(username = username, password = password)
		if user:
			httpResultResponse.errorMessage = ErrorMessage.LOGIN_SUCCESS
			httpResultResponse.errorMessage = "0"
			httpResultResponse.resultData = SQLUtils.SelectUserByUsernamePassword(username = username, password = password)
			user.pushKey = pushKey
			user.save()
		else:
			httpResultResponse.errorMessage = ErrorMessage.NO_SUCH_USER_OR_PASSWORD_IS_INVALID
			httpResultResponse.status = "8003"
		json = httpResultResponse.getJsonResult()
		return HttpResponse(json)
    else:
		httpResultResponse.errorMessage = ErrorMessage.POST_FAILED
		httpResultResponse.status = "8003"
		json = httpResultResponse.getJsonResult()
		return HttpResponse(ErrorMessage.POST_FAILED)

def checkUserIsExist(request):
	httpResultResponse = HttpResultResponse()
	if request.method == 'POST':
		username = request.POST.get('username')
		if username:
			user = User.objects.get(username = username)
			if user:
				httpResultResponse.status = '0'
				httpResultResponse.errorMessage = ErrorMessage.USER_IS_EXIST
			else:
				httpResultResponse.status = '8003'
				httpResultResponse.errorMessage = ErrorMessage.USER_IS_NOT_EXIST
		else:
			httpResultResponse.errorMessage = ErrorMessage.USERNAME_IS_NULL
			httpResultResponse.status = '8001'
		httpResultResponse.resultData = "null"
		return HttpResponse(httpResultResponse.getJsonResult())
	else:
		httpResultResponse.errorMessage = ErrorMessage.POST_FAILED
		httpResultResponse.status = '8001'
		return HttpResponse(httpResultResponse.getJsonResult())
	
def uploadAvatar(request):
	httpResultResponse = HttpResultResponse()
	if request.method == 'POST':
		username = request.POST.get('username')
		headImg = request.FILES.get('file')
		if username:
			user = User.objects.get(username = username)
			if headImg:
				user.avatar = headImg
				user.save()
				httpResultResponse.errorMessage = ErrorMessage.UPDATE_AVATAR_SUCCESS
				httpResultResponse.status = '0'
			else:
				httpResultResponse.errorMessage = ErrorMessage.AVATAR_IS_INVALID
				httpResultResponse.status = '8004'
		else:
			httpResultResponse.errorMessage = ErrorMessage.USERNAME_IS_NULL
			httpResultResponse.status = '8005'
		
		return HttpResponse(httpResultResponse.getJsonResult())
	else:
		httpResultResponse.errorMessage = ErrorMessage.POST_FAILED
		httpResultResponse.status = '8001'
		return HttpResponse(httpResultResponse.getJsonResult())
    		
def modifyUserInfo(request):
    httpResultResponse = HttpResultResponse()
    if request.method == 'POST':
		username = request.POST.get('username')
		password = request.POST.get('password')
		gender = request.POST.get('gender')
		birthday = request.POST.get('birthday')
		nickname = request.POST.get('nickname')
		userid = request.POST.get('userid')
		longProfile = request.POST.get('longProfile')
		simpleProfile = request.POST.get('simpleProfile')
		if password and gender and birthday and nickname:
			user = User.objects.get(pk = userid)
			if user:
				user.username = username
				user.password = password
				user.gender = gender
				user.birthday = birthday
				user.nickname = nickname
				user.longProfile = longProfile
				user.simpleProfile = simpleProfile
				user.save()
				httpResultResponse.resultData = SQLUtils.SelectUserByUsernamePassword(username = username, password = password)
				httpResultResponse.errorMessage = ErrorMessage.MODIFY_USER_INFO_SUCCESS
				httpResultResponse.status = '0'
			else:
				httpResultResponse.errorMessage = ErrorMessage.NO_SUCH_USER_OR_PASSWORD_IS_INVALID
				httpResultResponse.status = '8005'
		else:
			httpResultResponse.errorMessage = ErrorMessage.NO_SUCH_USER_OR_PASSWORD_IS_INVALID
			httpResultResponse.status = '8003'
	else:
		httpResultResponse.errorMessage = ErrorMessage.POST_FAILED
		httpResultResponse.status = '8001'
		return HttpResponse(httpResultResponse.getJsonResult())
