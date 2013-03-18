'''
Created on Mar 17, 2013

@author: mingqi
'''
from django.conf.urls import patterns, url, include
from django.contrib.staticfiles.urls import staticfiles_urlpatterns

from xingyun.menu import views as menu 

urlpatterns = patterns('',
    url(r'^menu/list/?$', menu.list_items, name='menu/list'),
#    url(r'^menu/add/?$', menu.add_item, name='menu/add'),
    url(r'^menu/add/?$', menu.MenuItemCreate.as_view(), name='menu/add'),
)

urlpatterns += staticfiles_urlpatterns()