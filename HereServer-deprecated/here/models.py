from django.db import models
# Create your models here.

class User(models.Model):
	username = models.CharField(max_length=30)
	password = models.CharField(max_length=30)
	nickname = models.CharField(max_length=30,blank=True)
	gender = models.CharField(max_length=30)
	pushKey = models.CharField(max_length=30)
	avatar = models.FileField(upload_to='./avatar/',blank=True,default="null")
	birthday = models.CharField(max_length=30)
	simpleProfile = models.CharField(max_length=30,blank=True)
	longProfile = models.CharField(max_length=300,blank=True)
	avatarThumb = models.FileField(upload_to='./avatar/thumb',blank=True,default="null")
	registertime = models.CharField(max_length=30,blank=True,default="null")

	
class Post(models.Model):
	longitude = models.CharField(max_length=30)
	latitude = models.CharField(max_length=30)
	city = models.CharField(max_length=30)
	cityCode = models.CharField(max_length=30,blank=True,default="null")
	address = models.CharField(max_length=30,blank=True,default="null")
	username = models.CharField(max_length=30,blank=True,default="null")
	time = models.CharField(max_length=30,blank=True,default="null")
	like = models.IntegerField(default=0,null=True)
	tag = models.CharField(max_length=200,blank=True)
	
class PostTag(models.Model):
	tag = models.CharField(max_length=100)

class Report(models.Model):
	content = models.CharField(max_length=100)
	postid = models.IntegerField()
	reporter = models.CharField(max_length=100)
	time = models.CharField(max_length=100)

class ReportIssue(models.Model):
	content = models.CharField(max_length=100)
	reporter = models.CharField(max_length=100)
	time = models.CharField(max_length=100)

class TestModel(models.Model):
	girl = models.CharField(max_length=30)
	boy = models.CharField(max_length=30)
	mom = models.CharField(max_length=30)
	ball = models.CharField(max_length=30)