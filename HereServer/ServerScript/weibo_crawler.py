#coding=utf-8
import urllib
import base64
import re
import rsa
import http.cookiejar  #从前的cookielib
import base64
import json
import urllib
import binascii

class WeiboLogin:
	"""docstring for WeiboLogin"""
	def __init__(self, username,password):
		self.username = username
		self.password = password

	def get_encrypted_name(self):
		username__url = urllib.request.qoute(self.username)
		username_encrypted = base64.b64encode(bytes(username_url,encoding='utf-8'))
		return username_encrypted.decode('utf-8')

	def get_prelogin_arg(self):
		json_pattern = re.compile('\((.*)\)')
		url = 'http://login.sina.com.cn/sso/prelogin.php?entry=weibo&callback=sinaSSOController.preloginCallBack&su=&' + self.get_encrypted_name() + '&rsakt=mod&checkpin=1&client=ssologin.js(v1.4.18)'
		try:
			request = urllib.request.Request(url)
			response = urllib.request.urlopen(request)
			raw_data = response.read().decode('utf-8')
			json_data = json_pattern.search(raw_data).group(1)
			data = json.loads(json_data)
			return data
		except Exception, e:
			print e
        	return None


	def get_encrypted_pw(self,data):
	    rsa_e = 65537 #0x10001
	    pw_string = str(data['servertime']) + '\t' + str(data['nonce']) + '\n' + str(self.password)
	    key = rsa.PublicKey(int(data['pubkey'],16),rsa_e)
	    pw_encypted = rsa.encrypt(pw_string.encode('utf-8'), key)
	    self.password = ''   #安全起见清空明文密码 
	    passwd = binascii.b2a_hex(pw_encypted)
	    print(passwd)
	    return passwd

	def enableCookies(self):
		cookie_container = http.cookiejar.CookieJar()
		cookie_support = urllib.request.HTTPCookieProcessor(cookie_container)
		opner = urllib.request.build_opener(cookie_support,urllib.request.HTTPHandler)
		urllib.request.install_opener(opener)

	def build_post_data(self,raw):
		post_data = {            
			"entry":"weibo",
            "gateway":"1",
            "from":"",
            "savestate":"7",
            "useticket":"1",
            "pagerefer":"http://passport.weibo.com/visitor/visitor?entry=miniblog&a=enter&url=http%3A%2F%2Fweibo.com%2F&domain=.weibo.com&ua=php-sso_sdk_client-0.6.14",
            "vsnf":"1",
            "su":self.get_encrypted_name(),
            "service":"miniblog",
            "servertime":raw['servertime'],
            "nonce":raw['nonce'],
            "pwencode":"rsa2",
            "rsakv":raw['rsakv'],
            "sp":self.get_encrypted_pw(raw),
            "sr":"1280*800",
            "encoding":"UTF-8",
            "prelt":"77",
            "url":"http://weibo.com/ajaxlogin.php?framelogin=1&callback=parent.sinaSSOController.feedBackUrlCallBack",
            "returntype":"META"
        }
		data = urllib.parse.urlencode(post_data).encode('utf-8')
		return data

	def login(self):
		url = 'http://login.sina.com.cn/sso/login.php?client=ssologin.js(v1.4.18)'
		self.enableCookies()
		data = self.get_prelogin_arg()
		post_data = self.build_post_data(data)
		headers = {
            "User-Agent":"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36"
        }

        try:
        	request = urllib.request.Request(url = url,data = post_data,headers = headers)
        	response = urllib.request.urlopen(request)
        	html = response.read().decode('utf-8')
        	print(html)
        except Exception, e:
        	print e

        p = re.compile('location\.replace\(\'(.*?)\'\)')
        p2 = re.compile(r'"userdomain":"(.*?)"')

        try:
        	login_url = p.search(html).group(1)
        	print(login_url)
        	request = urllib.request.Request(login_url)
        	response = urllib.request.urlopen(request)
        	page = response.read().decode('utf-8')
        	print(page)
        	login_url = 'http://weibo.com/' + p2.search(page).group(1)
        	request = urllib.request.Request(login_url)
        	response = urllib.request.urlopen(request)
        	final = response.read().decode('utf-8')
        	print("Login success!")
        except:
        	print('Login error!')









