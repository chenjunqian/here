from django.db import models

# Create your models here.

class User(models.Model):
	username = models.CharField(max_length=30)
	password = models.CharField(max_length=30)
	gender = models.CharField(max_length=30)
	pushKey = models.CharField(max_length=30)
	avatar = models.FileField(upload_to='./avatar/',blank=True)
	birthday = models.CharField(max_length=30)
	
class Location(models.Model):
	longitude = models.CharField(max_length=30)
	latitude = models.CharField(max_length=30)
	city = models.CharField(max_length=30)
	user = models.ForeignKey(User,on_delete=models.CASCADE,null=True)
	like = models.IntegerField()
	
class TestModel(models.Model):
	girl = models.CharField(max_length=30)
	boy = models.CharField(max_length=30)
	mom = models.CharField(max_length=30)
	ball = models.CharField(max_length=30)