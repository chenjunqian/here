# coding:utf-8
import simplejson
import MySQLdb
from django.shortcuts import render
from django.http import HttpResponse
from models import User,Location
from django.db import connection

def login(request):
	dict = {}
	resultData = {}
	if request.method == 'POST':
		username = request.POST.get('username')
		password = request.POST.get('password')
		gender = request.POST.get('gender')
		pushKey = request.POST.get('pushKey')
		user = User.objects.filter(username__exact = username,password__exact = password)
		if user:
			dict['errorMessage'] = "login_success"
			dict['status'] = "0"
			resultData['username'] = username
			resultData['password'] = password
			resultData['pushKey'] = pushKey
			resultData['gender'] = gender
			dict['resultData'] = resultData

		else:
			dict['errorMessage'] = "no_such_user"
			dict['status'] = "8003"

		json  = simplejson.dumps(dict)
		return HttpResponse(json)
	else:
		dict['errorMessage'] = "POST failed"
		dict['status'] = "8002"
		json  = simplejson.dumps(dict)
		return HttpResponse("POST failed")
		
def register(request):
	dict = {}
	resultData = {}
	if request.method == 'POST':
		username = request.POST.get('username')
		password = request.POST.get('password')
		gender = request.POST.get('gender')
		pushKey = request.POST.get('pushKey')
		avatar = request.POST.get('avatar')
		if username and password :
			dict['errorMessage'] = "register_success"
			dict['status'] = "0"
			resultData['username'] = username
			resultData['password'] = password
			resultData['pushKey'] = pushKey
			resultData['avatar'] = avatar
			User.objects.create(username = username,password = password,gender = gender)
			dict['resultData'] = resultData
		else:
			dict['errorMessage'] = "username_or_password_invalid"
			dict['status'] = "8004"
		json = simplejson.dumps(dict)
		return HttpResponse(json)
	else:
		dict['errorMessage'] = "POST_FAILED"
		dict['status'] = "8002"
		json  = simplejson.dumps(dict)
		return HttpResponse("POST failed")
		
def updateUserLocation(request):
	dict[]
	resultData = {}
	if request.method == 'POST'
		longitude = request.POST.get('longitude')
		latitude = request.POST.get('latitude')
		user = request.POST.get('user')
		like = request.port.get('like')
		time = request.port.get('time')
		city = request.POST.get('city')
		if longitude and latitude and user and city:
			dict['errorMessage'] = "update_user_location_success"
			dict['status'] = "0"
			resultData['user'] = user
			resultData['longitude'] = longitude
			resultData['latitude'] = latitude
			resultData['like'] = like
			resultData['time'] = time
			resultData['city'] = city
			Location.objects.create(longitude = longitude,latitude = latitude,user = user,like = like,time = time,city = city)
			dict['resultData'] = resultData
		else:
			dict['errorMessage'] = "username_or_password_invalid"
			dict['status'] = "8004"
		json = simplejson.dumps(dict)
		return HttpResponse(json)
	else:
		dict['errorMessage'] = "POST_FAILED"
		dict['status'] = "8002"
		json  = simplejson.dumps(dict)
		return HttpResponse("POST failed")
		
def getLocationByLocation(request):
	dict[]
	resultData = []
	if request.method == 'POST'
		longitude = request.POST.get('longitude')
		latitude = request.POST.get('latitude')
		city = request.POST.get('city')
		try:
			cur = connection.cursor()
			query = "select * from Location where city='%s'"
			cur.execute(query,city)