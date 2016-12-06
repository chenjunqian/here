
import requests
import json

def registerEMChat(username,password):
	data = {}
	data['username'] = username
	data['password'] = password
	headers = {"Content-Type":"application/json"}
	response = requests.post("https://a1.easemob.com/lovermarker/lovermarker/users",headers = headers,data = json.dumps(data))
	return response