"""HereServer URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/1.8/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  url(r'^$', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  url(r'^$', Home.as_view(), name='home')
Including another URLconf
    1. Add an import:  from blog import urls as blog_urls
    2. Add a URL to urlpatterns:  url(r'^blog/', include(blog_urls))
"""
from django.conf.urls import include, url,patterns
from django.contrib import admin
from here import views

admin.autodiscover()

urlpatterns = patterns('',
    url(r'^$', views.index, name='index'),
    url(r'^login/',views.login,name='login'),
    url(r'^register/',views.register,name='register'),
    url(r'^update_user_location/',views.updateUserPostLocation,name='updateUserPostLocation'),
    url(r'^check_user_is_exist/',views.checkUserIsExist,name='checkUserIsExist'),
    url(r'^upload_avatar/',views.uploadAvatar,name='uploadAvatar'),
    url(r'^get_post_tag/',views.getPostTag,name='getPostTag'),
    url(r'^get_post_by_location/',views.getLocationByLocation,name='getLocationByLocation'),
    url(r'^get_user_post/',views.getUserPost,name='getUserPost'),
    url(r'^get_user_info_by_username/',views.getUserInfoByUsername,name='getUserInfoByUsername'),
    url(r'^modify_user_info/',views.modifyUserInfo,name='modifyUserInfo'),
    url(r'^admin/', include(admin.site.urls)),
)
