#coding=utf-8
import json as simplejson

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


class ErrorMessage(object):
	USERNAME_OR_PASSWORD_INVALID = "USERNAME_OR_PASSWORD_INVALID"
	NO_SUCH_USER_OR_PASSWORD_IS_INVALID = "NO_SUCH_USER_OR_PASSWORD_IS_INVALID"
	USERNAME_IS_EXIST = "USERNAME_IS_EXIST"
	REGISTER_SUCCESS = "REGISTER_SUCCESS"
	POST_FAILED = "POST_FAILED"
	LOGIN_SUCCESS = "LOGIN_SUCCESS"

	"""docstring for ErrorMessage"""
	def __init__(self):
		pass

class ResponseStatus(object):
	POST_FAILED = "8001"

	"""docstring for ResponseStatus"""
	def __init__(self):
		pass
		