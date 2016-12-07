from django.conf.urls import url
from . import views


app_name = 'lovemarker'
urlpatterns = [
    url(r'^$', views.index, name='index'),
    url(r'^register/', views.register, name='register'),
    url(r'^login/', views.login, name='login'),
    url(r'^check_user_is_exist/', views.checkUserIsExist, name='checkUserIsExist'),
    url(r'^upload_avatar/',views.uploadAvatar,name='uploadAvatar'),
]