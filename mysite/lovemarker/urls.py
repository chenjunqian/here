from django.conf.urls import url, include
from rest_framework import routers
from . import views


app_name = 'lovemarker'

router = routers.DefaultRouter()
router.register(r'^register/', views.register)
router.register(r'^login/', views.login)
router.register(r'^check_user_is_exist/', views.register)
router.register(r'^register/', views.checkUserIsExist)
router.register(r'^upload_avatar/', views.uploadAvatar)

urlpatterns = [
    url(r'^$', views.index, name='index'),
    url(r'^', include(router.urls)),
    url(r'^api/', include('rest_framework.urls', namespace='rest_framework')),
]