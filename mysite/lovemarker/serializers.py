from rest_framework import serializers
from models import User, Post, PostTag, Report, ReportIssue


class UserSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = User
        fields = (
            'username', 'nickname', 'gender',
            'pushKey', 'avatar', 'avatarThumb',
            'birthday', 'simpleProfile', 'longProfile',
        )

class PostSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = User
        fields = (
            'longitude', 'latitude', 'city',
            'cityCode', 'address', 'username',
            'time', 'simpleProfile', 'longProfile',
        )


class PostLikeSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = User
        fields = (
            'postId', 'userId', 'created',
        )


class PostTagSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = User
        fields = (
            'tag', 'postId', 'userId','created',
        )

class ReportSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = User
        fields = (
            'content', 'postId', 'reporterId','created',
        )

class ReportIssueSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = User
        fields = (
            'content', 'reporterId', 'created',
        )