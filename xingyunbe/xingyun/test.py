#-*- coding: utf-8 -*-
'''
Created on Mar 20, 2013

@author: mingqi
'''

a = u'你好'
import json
print json.dumps(a, ensure_ascii=False)