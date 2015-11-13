#coding=utf-8
import json as simplejson
import MySQLdb
from django.shortcuts import render
from django.http import HttpResponse
from models import User
from django.db import connection
from django import forms
from django.shortcuts import render,render_to_response

import sys,os
reload(sys)
sys.setdefaultencoding('utf8')

def index(request):
	return HttpResponse(u"This is here")

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
			resultData['userid'] = user[0][0]
			resultData['username'] = user[0][1]
			resultData['password'] = user[0][2]
			resultData['gender'] = user[0][3]
			resultData['pushKey'] = user[0][4]
			resultData['birthday'] = user[0][5]
			if user[0][6]:
				resultData['avatar'] = user[0][6]
			else:
				resultData['avatar'] = ''
			resultData['nickname'] = user[0][7]
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
				# 将用户数据存入数据库
				cursor = connection.cursor()
				query = "insert into here_user(username,password,gender,pushkey,birthday,nickname) values(%s,%s,%s,%s,%s,%s)"
				cursor.execute(query,[username,password,gender,pushKey,birthday,nickname])
				# 返回客户端用户数据
				resultData['username'] = username
				resultData['password'] = password
				resultData['pushKey'] = pushKey
				resultData['gender'] = gender
				resultData['birthday'] = birthday
				resultData['nickname'] = nickname
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

class TestForm(forms.Form):
    username = forms.CharField()
    headImg = forms.FileField()

# 上传用户的头像
def uploadAvatar(request):	
	dict = {}
	resultData = {}
	if request.method == 'POST':
		# username  = request.POST.get('username')
		# avatar = request.FILES.get('file')
		username  = request.POST.get('username')
		headImg = request.FILES.get('headImg')
		tf = TestForm(request.POST,request.FILES)
		if tf.is_valid:
			user = User.objects.get(username = username)
			user.avatar = headImg
			user.save()
			if headImg:
				return HttpResponse(u"upload avatar success ")
			else:
				return HttpResponse(u"upload avatar failed ")
			
	else:
		tf = TestForm()
	return render_to_response('test_upload_avatar.html',{'uf':tf})

		# if username:
		# 	user = User.objects.get(username = username)
		# 	if avatar:
		# 		user.avatar = avatar
		# 		user.save()
		# 		dict['errorMessage'] = "UPDATE_AVATAR_SUCCESS"
		# 		dict['status'] = "0"
		# 	else:
		# 		dict['errorMessage'] = "AVATAR_IS_INVALID"
		# 		dict['status'] = "8004"
		# else:
		# 	dict['errorMessage'] = "USERNAME_IS_INVALID"
		# 	dict['status'] = "8005"

		# json  = simplejson.dumps(dict)
		# return HttpResponse(json)
	# else:
	# 	dict['errorMessage'] = "POST_FAILED"
	# 	dict['status'] = "8001"
	# 	return HttpResponse("POST failed")

# 修改用户信息
def modifyUserInfo(request):
	dict = {}
	resultData = {}
	if request.method == 'POST':
		username = request.POST.get('username')
		password = request.POST.get('password')
		gender = request.POST.get('gender')
		birthday = request.POST.get('birthday')
		nickname = request.POST.get('nickname')
		userid = request.POST.get('userid')
		if password and gender and birthday and nickname:
			cursor = connection.cursor()
			query = "update here_user set password = %s,gender = %s,birthday = %s,nickname = %s where id = %s"
			value = [password,gender,birthday,nickname,userid]
			cursor.execute(query,value)
			# 返回用户信息
			query = 'select * from here_user where id = %s'
			value = [userid]
			cursor.execute(query,value)
			user = cursor.fetchall()
			if user:
				dict['errorMessage'] = "modify_user_info_success"
				dict['status'] = "0"
				resultData['username'] = user[0][1]
				resultData['password'] = user[0][2]
				resultData['gender'] = user[0][3]
				resultData['pushKey'] = user[0][4]
				resultData['birthday'] = user[0][5]
				if user[0][6]:
					resultData['avatar'] = user[0][6]
				else:
					resultData['avatar'] = 'null'
				resultData['nickname'] = user[0][7]
				dict['resultData'] = resultData
			else:
				dict['errorMessage'] = "data_base_error"
				dict['status'] = "8005"
		else:
			dict['errorMessage'] = "no_such_user_or_password_is_invalid"
			dict['status'] = "8003"
			
		json = simplejson.dumps(dict)
		return HttpResponse(json)
	else:
		dict['errorMessage'] = "POST_FAILED"
		dict['status'] = "8001"
		json  = simplejson.dumps(dict)
		return HttpResponse("POST failed")

# 用户发帖上传位置信息以及内容
def updateUserPostLocation(request):
	dict = {}
	resultData = {}
	if request.method == 'POST':
		longitude = request.POST.get('longitude')
		latitude = request.POST.get('latitude')
		tag = request.POST.get('tag')
		username = request.POST.get('username')
		city = request.POST.get('city')
		cityCode = request.POST.get('cityCode')
		address = request.POST.get('address')
		if longitude and latitude and username and city:
			cursor = connection.cursor()
			query = "insert into here_post(longitude,latitude,city,cityCode,address,tag,username) values(%s,%s,%s,%s,%s,%s,%s)"
			value = [longitude,latitude,city,cityCode,address,tag,username]
			cursor.execute(query,value)
			dict['errorMessage'] = "update_user_location_success"
			dict['status'] = "0"
			resultData['username'] = username
			resultData['longitude'] = longitude
			resultData['latitude'] = latitude
			resultData['city'] = city
			resultData['cityCode'] = cityCode
			resultData['address'] = address
			resultData['tag'] = tag
			resultData['like'] = 0
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

# 获取发帖的标签
def getPostTag(request):
	dict = {}
	resultData = {}
	if request.method == 'POST':
		cursor = connection.cursor()
		query = "select * from here_posttag"
		cursor.execute(query)
		tag = cursor.fetchall()
		if tag:
			resultData['tag'] = tag[0][1]
			dict['resultData'] = resultData
			dict['errorMessage'] = "get_tag_success"
			dict['status'] = "0"
		else:
			dict['errorMessage'] = "tag_not_found"
			dict['status'] = "8003"

		json = simplejson.dumps(dict)
		return HttpResponse(json)
	else:
		json  = simplejson.dumps(dict)
		return HttpResponse("POST failed")

# 获取用户的帖子
def getUserPost(request):
	dict = {}
	resultData = {}
	if request.method == 'POST':
		username = request.POST.get('username')
		cursor = connection.cursor()
		query = "select * from here_post where username = %s"
		cursor.execute(query,[username])
		res = cur.fetchall()
		if res:
			resultData['PostList'] = res
			dict['resultData'] = resultData
			dict['errorMessage'] = "get_post_success"
			dict['status'] = "0"
		else:
			dict['errorMessage'] = "post_not_found"
			dict['status'] = "8003"
		json = simplejson.dumps(dict)
		return HttpResponse(json)
	else:
		json  = simplejson.dumps(dict)
		return HttpResponse("POST failed")

# 测试使用
class GetLocation(forms.Form):
    longitude = forms.CharField()
    latitude = forms.CharField()
    city = forms.CharField()

# 根据用户的地理经纬度，来获取他周围的定位信息
def getLocationByLocation(request):
	dict = {}
	resultData = {}
	if request.method == 'POST':
		longitude = request.POST.get('longitude')
		latitude = request.POST.get('latitude')
		city = request.POST.get('city')
		cur = connection.cursor()
		query = "select * from here_post where city like %s and longitude > %s and longitude < %s and latitude > %s and latitude < %s"
		cur.execute(query,[city,float(longitude)-0.05,float(longitude)+0.05,float(latitude)-0.5,float(latitude)+0.5])
		res = cur.fetchall()
		if res:
			resultData['postList'] = []
			for json_result in res:
				jsonMetaResultData = {}
				jsonMetaResultData['postId'] = json_result[0]
				jsonMetaResultData['longitude'] = json_result[1]
				jsonMetaResultData['latitude'] = json_result[2]
				jsonMetaResultData['city'] = json_result[3]
				jsonMetaResultData['address']  = json_result[4]
				jsonMetaResultData['like'] = json_result[5]
				jsonMetaResultData['tag'] = json_result[6]
				jsonMetaResultData['cityCode'] = json_result[7]
				jsonMetaResultData['username'] = json_result[8]
				resultData['postList'].append(jsonMetaResultData)
			
			dict['resultData'] = resultData
			dict['errorMessage'] = "get_post_success"
			dict['status'] = "0"
		else:
			dict['errorMessage'] = "none_post"
			dict['status'] = "8001"
		json = simplejson.dumps(dict)
		return HttpResponse(json)
	else:
		# json  = simplejson.dumps(dict)
		# return HttpResponse("POST failed")
		tf = GetLocation()
		return render_to_response('test-get-post-by-location.html',{'uf':tf})


