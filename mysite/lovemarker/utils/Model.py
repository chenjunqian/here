#coding=utf-8
import json as simplejson
from json import JSONEncoder

class HttpResultResponse(object):
    def __init__(self):
		self.jsonResult = {}
		self.status = ""
		self.resultData = {}
		self.errorMessage = ""
		
    def getJsonResult(self):
		self.jsonResult['resultData'] = self.resultData
		self.jsonResult['errorMessage'] = self.errorMessage
		self.jsonResult['status'] = self.status
		self.json = simplejson.dumps(self.jsonResult)
		return self.json

class MyEncode(JSONEncoder):
    def	default(self,obj):
		return obj.__dict__

class ErrorMessage(object):
    USERNAME_OR_PASSWORD_INVALID = "USERNAME_OR_PASSWORD_INVALID"
    NO_SUCH_USER_OR_PASSWORD_IS_INVALID = "NO_SUCH_USER_OR_PASSWORD_IS_INVALID"
    USERNAME_IS_EXIST = "USERNAME_IS_EXIST"
    REGISTER_SUCCESS = "REGISTER_SUCCESS"
    POST_FAILED = "POST_FAILED"
    LOGIN_SUCCESS = "LOGIN_SUCCESS"
    USER_IS_EXIST = "USER_IS_EXIST"
    USER_IS_NOT_EXIST = "USER_IS_NOT_EXIST"
    USERNAME_IS_NULL = "USERNAME_IS_NULL"
    UPDATE_AVATAR_SUCCESS = "UPDATE_AVATAR_SUCCESS"
    AVATAR_IS_INVALID = "AVATAR_IS_INVALID"
    MODIFY_USER_INFO_SUCCESS = "MODIFY_USER_INFO_SUCCESS"

    def __init__(self):
		pass

class ResponseStatus(object):
	POST_FAILED = "8001"

	"""docstring for ResponseStatus"""
	def __init__(self):
		pass
		