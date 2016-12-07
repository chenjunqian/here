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