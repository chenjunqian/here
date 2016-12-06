#coding=utf-8
from django.shortcuts import render
from django.http import HttpResponse
from models import User
from Model import HttpResultResponse, ErrorMessage
from django.shortcuts import get_object_or_404
from django.db import connection
import util

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
				newUser = User.objects.create(username= username, password = password, gender = gender, pushKey = pushKey, birthday = birthday, nickname = nickname)
				# 注册环信账号
				newCursor = connection.cursor()
				newQuery = "select * from here_user where username = %s"
				newCursor.execute(newQuery,[username])
				newRes = newCursor.fetchall()
				userid = newRes[0][0]
				util.registerEMChat(userid,password)
				# 返回客户端用户数据
				httpResultResponse.resultData = newUser
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
			httpResultResponse.resultData = user
			user.pushKey = pushKey
			user.save()
		else:
    		httpResultResponse.errorMessage = ErrorMessage.NO_SUCH_USER_OR_PASSWORD_IS_INVALID
			httpResultResponse.status = "8003"
		json = simplejson.dumps(dict)
		return HttpResponse(json)
	else:
		httpResultResponse.errorMessage = ErrorMessage.POST_FAILED
		httpResultResponse.status = "8003"
		json  = simplejson.dumps(dict)
		return HttpResponse(ErrorMessage.POST_FAILED)