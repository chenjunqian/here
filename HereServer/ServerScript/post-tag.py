#coding=utf-8
import MySQLdb

import sys,os
reload(sys)
sys.setdefaultencoding('utf8')

try:
	conn = MySQLdb.connect(host='localhost',user='root',passwd='root',db='heredb',charset='utf8')
	cur = conn.cursor()
	query = "update here_posttag set tag = %s"
	cur.execute(query,["嘿嘿嘿@@飞叶子@@喝酒@@干架@@打球@@烧烤@@大保健@@撸啊撸@@打飞机@@约了一发"])
	conn.commit()
	cur.close()
	conn.close()
except MySQLdb.Error,e:
	print "Mysql Error %d: %s" % (e.args[0], e.args[1])