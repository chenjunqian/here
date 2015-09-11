# coding:utf-8
import simplejson
import MySQLdb
from django.shortcuts import render
from django.http import HttpResponse
from models import User,Location
from django.db import connection

# 登录
def login(request):
	dict = {}
	resultData = {}
	if request.method == 'POST':
		username = request.POST.get('username')
		password = request.POST.get('password')
		gender = request.POST.get('gender')
		pushKey = request.POST.get('pushKey')
		
		cur = connection.cursor()
		query = 'select * from here_user where username = %s and password = %s'
		value = [username,password]
		cur.execute(query,value)
		user = cur.fetchall()
		if user:
			dict['errorMessage'] = "login_success"
			dict['status'] = "0"
			resultData['username'] = username
			resultData['password'] = password
			resultData['pushKey'] = pushKey
			resultData['gender'] = gender
			dict['resultData'] = resultData
		else:
			dict['errorMessage'] = "no_such_user_or_password_is_invalid"
			dict['status'] = "8003"
		json = simplejson.dumps(dict)
		return HttpResponse(json)
	else:
		dict['errorMessage'] = "POST failed"
		dict['status'] = "8002"
		json  = simplejson.dumps(dict)
		return HttpResponse("POST failed")		
# 注册
def register(request):
	dict = {}
	resultData = {}
	if request.method == 'POST':
		username = request.POST.get('username')
		password = request.POST.get('password')
		gender = request.POST.get('gender')
		pushKey = request.POST.get('pushKey')
		avatar = request.POST.get('avatar')
		birthday = request.POST.get('birthday')
		nickname = request.POST.get('nickname')
		if username and password :
			
			cur = connection.cursor()
			query = 'select * from here_user where username = %s'
			cur.execute(query,[username])
			userResult = cur.fetchall()
			if userResult:
				dict['errorMessage'] = "username_is_exist"
				dict['status'] = "8002"
			else:
				dict['errorMessage'] = "register_success"
				dict['status'] = "0"
				resultData['username'] = username
				resultData['password'] = password
				resultData['pushKey'] = pushKey
				resultData['avatar'] = avatar
				resultData['gender'] = gender
				resultData['birthday'] = birthday
				resultData['nickname'] = nickname
				# User.objects.create(username=username,password=password,gender=gender,avatar=avatar,pushKey=pushKey,birthday=birthday)
				# cursor = connection.cursor()
				# query = "insert into here_user(username,password,gender,pushkey,birthday) values(%s,%s,%s,%s,%s)"
				# cursor.execute(query,(username,password,pushKey,gender,birthday))
				user = User()
				user.username = username
				user.password = password
				user.nickname = nickname
				user.pushKey = pushKey
				user.avatar = avatar
				user.birthday = birthday
				user.gender = gender
				user.save()
				dict['resultData'] = resultData
		else:
			dict['errorMessage'] = "username_or_password_invalid"
			dict['status'] = "8001"
		json = simplejson.dumps(dict)
		return HttpResponse(json)
	else:
		dict['errorMessage'] = "POST_FAILED"
		dict['status'] = "8001"
		json  = simplejson.dumps(dict)
		return HttpResponse("POST failed")

# 根据用户名，判断该用户是否已经注册
def checkUserIsExist(request):
	dict = {}
	resultData = {}
	if request.method == 'POST':
		username = request.POST.get('username')
		if username:
			cursor = connection.cursor()
			cursor.execute("select * from here_user where username=%s",[username])
			res = cursor.fetchall()
			if res:
				dict['status'] = 0
				dict['errorMessage'] = "user_is_exist"
			else:
				dict['status'] = 8003
				dict['errorMessage'] = "user_is_not_exist"

			resultData['objects'] = "objects"
			dict['resultData'] = resultData
			json = simplejson.dumps(dict)
			return HttpResponse(json)

		else:
			dict['errorMessage'] = "username_is_null"
			dict['status'] = 8001
			json  = simplejson.dumps(dict)
			return HttpResponse("POST failed")

	else:
		dict['errorMessage'] = "POST_FAILED"
		dict['status'] = 8001
		json  = simplejson.dumps(dict)
		return HttpResponse("POST failed")

# 用户发帖上传位置信息以及内容
def updateUserLocation(request):
	dict = {}
	resultData = {}
	if request.method == 'POST':
		longitude = request.POST.get('longitude')
		latitude = request.POST.get('latitude')
		shareContent = request.POST.get('shareContent')
		user = request.POST.get('user')
		city = request.POST.get('city')
		if longitude and latitude and user and city:
			dict['errorMessage'] = "update_user_location_success"
			dict['status'] = "0"
			resultData['user'] = user
			resultData['longitude'] = longitude
			resultData['latitude'] = latitude
			resultData['city'] = city
			resultData['shareContent'] = shareContent

			dict['resultData'] = resultData
		else:
			dict['errorMessage'] = "username_or_password_invalid"
			dict['status'] = "8001"
		json = simplejson.dumps(dict)
		return HttpResponse(json)
	else:
		dict['errorMessage'] = "POST_FAILED"
		dict['status'] = "8001"
		json  = simplejson.dumps(dict)
		return HttpResponse("POST failed")
		
# 根据用户的地理经纬度，来获取他周围的定位信息
def getLocationByLocation(request):
	dict = {}
	resultData = []
	if request.method == 'POST':
		longitude = request.POST.get('longitude')
		latitude = request.POST.get('latitude')
		city = request.POST.get('city')
		cur = connection.cursor()
		query = "select * from Location where city=%s"
		cur.execute(query,[city])
		loc = cur.fetchall()
			