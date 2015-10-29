#coding=utf-8
import MySQLdb

import sys,os
reload(sys)
sys.setdefaultencoding('utf8')

try:
	conn = MySQLdb.connect(host='localhost',user='root',passwd='root',db='heredb')
	cur = conn.cursor()
	query = "insert into here_tag(tag) value %s"
	value = "嘿咻@@飞叶子@@喝酒@@干架"
	cur.execute()
	conn.commit()
	cur.close()
	conn.close()
except MySQLdb.Error,e:
	print "Mysql Error %d: %s" % (e.args[0], e.args[1])