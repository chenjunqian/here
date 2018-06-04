from __future__ import unicode_literals
from django.db import models

# Create your models here.
class User(models.Model):
	username = models.CharField(max_length=30)
	password = models.CharField(max_length=30)
	nickname = models.CharField(max_length=30,blank=True)
	gender = models.CharField(max_length=30)
	pushKey = models.CharField(max_length=30,blank = True,default="null")
	avatar = models.ImageField(upload_to='./avatar/',blank=True,default="null")
	avatarThumb = models.ImageField(upload_to='./avatar/thumb',blank=True,default="null")
	birthday = models.CharField(max_length=30,blank = True)
	simpleProfile = models.CharField(max_length=30,blank=True)
	longProfile = models.CharField(max_length=300,blank=True)
	created = models.DateTimeField(auto_now_add = True)		


class Post(models.Model):
    longitude = models.CharField(max_length=30)
	latitude = models.CharField(max_length=30)
	city = models.CharField(max_length=30)
	cityCode = models.CharField(max_length=30,blank=True,default="null")
	address = models.CharField(max_length=30,blank=True,default="null")
	username = models.CharField(max_length=30,blank=True,default="null")
	created = models.DateTimeField(auto_now_add = True)
	tag = models.CharField(max_length=200,blank=True)
	
class PostLike(models.Model):
    postId = models.IntegerField()
	userId = models.IntegerField()
	created = models.DateTimeField(auto_now_add = True)

class PostTag(models.Model):
	tag = models.CharField(max_length=100)
    postId = models.IntegerField()
	userId = models.IntegerField()
	created = models.DateTimeField(auto_now_add = True)

class Report(models.Model):
	content = models.CharField(max_length=100)
	postId = models.IntegerField()
	reporterId = models.IntegerField()
	created = models.DateTimeField(auto_now_add = True)

class ReportIssue(models.Model):
	content = models.CharField(max_length=100)
	reporterId = models.IntegerField()
	created = models.DateTimeField(auto_now_add = True)

class TestModel(models.Model):
	girl = models.CharField(max_length=30)
	boy = models.CharField(max_length=30)
	mom = models.CharField(max_length=30)
	ball = models.CharField(max_length=30)