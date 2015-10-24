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

	
class Post(models.Model):
	longitude = models.CharField(max_length=30)
	latitude = models.CharField(max_length=30)
	city = models.CharField(max_length=30)
	userid = models.CharField(max_length=30,blank=True,default="null")
	like = models.IntegerField()
	shareContent = models.CharField(max_length=200,blank=True)
	image_one = models.ImageField(upload_to='./post-image/',blank=True,default="null")
	image_two = models.ImageField(upload_to='./post-image/',blank=True,default="null")
	image_three = models.ImageField(upload_to='./post-image/',blank=True,default="null")
	image_four = models.ImageField(upload_to='./post-image/',blank=True,default="null")
	
class TestModel(models.Model):
	girl = models.CharField(max_length=30)
	boy = models.CharField(max_length=30)
	mom = models.CharField(max_length=30)
	ball = models.CharField(max_length=30)