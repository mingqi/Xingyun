'''
Created on Mar 17, 2013

@author: mingqi
'''
from .views import *
from django.conf.urls import patterns, url
from django.contrib.staticfiles.urls import staticfiles_urlpatterns

urlpatterns = patterns('',
    url(r'^menu/list/?$', MenuItemList.as_view(), name='menu/list'),
    url(r'^menu/add/?$', MenuItemCreate.as_view(), name='menu/add'),
    url(r'^menu/(?P<pk>\w+)/update/?$', MenuItemUpdate.as_view(), name='menu/update'),
    url(r'^menu/(?P<menuItemId>\w+)/delete/?$', deleteMenuItem, name='menu/delete'),
)

urlpatterns += staticfiles_urlpatterns()