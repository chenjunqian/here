#coding=utf-8
import MySQLdb

import sys,os
reload(sys)
sys.setdefaultencoding('utf8')

try:
	conn = MySQLdb.connect(host='localhost',user='root',passwd='root',db='heredb',charset='utf8')
	cur = conn.cursor()
	query = "update here_posttag set tag = %s"
	cur.execute(query,["野战一发@@制服诱惑@@站立式@@陌生人@@老汉推车@@69@@不止一人哦@@夫妻交换@@不满意@@没特别的"])
	conn.commit()
	cur.close()
	conn.close()
except MySQLdb.Error,e:
	print "Mysql Error %d: %s" % (e.args[0], e.args[1])