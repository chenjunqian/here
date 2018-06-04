#coding=utf-8
from django.db import connection
import MySQLdb

def SelectUserByUsernamePassword(username, password):
    cur = connection.cursor()
    resultData = {}
    query = 'select * from lovemarker_user where username = %s and password = %s'
    value = [username, password]
    cur.execute(query, value)
    user = cur.fetchall()
    if user:
        resultData['userid'] = user[0][0]
        resultData['username'] = user[0][1]
        resultData['password'] = user[0][2]
        resultData['gender'] = user[0][3]
        resultData['pushKey'] = user[0][4]
        resultData['birthday'] = user[0][5]
        if user[0][6]:
            resultData['avatar'] = user[0][6]
        else:
            resultData['avatar'] = ''
        resultData['nickname'] = user[0][7]
        resultData['longProfile'] = user[0][8]
        resultData['simpleProfile'] = user[0][9]
        resultData['avatarThumb'] = user[0][10]
        return resultData
    else:
        return None
