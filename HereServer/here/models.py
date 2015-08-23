from django.db import models

# Create your models here.

class User(models.Model):
	username = models.CharField(max_length=30)
	password = models.CharField(max_length=30)
	gender = models.CharField(max_length=30)
	pushKey = models.CharField(max_length=30)
	avatar = models.FileField(upload_to='./avatar/')
	birthday = models.CharField(max_length=30)
	
class Location(models.Model):
	longitude = models.CharField(max_length=30)
	latitude = models.CharField(max_length=30)
	user = User()
	like = models.IntegerField()
	time = modle.DateTimeField(auto_now_add=True)