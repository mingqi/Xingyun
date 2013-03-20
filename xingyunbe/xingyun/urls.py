'''
Created on Mar 17, 2013

@author: mingqi
'''
from .views import *
from django.conf.urls import patterns, url
from django.contrib.staticfiles.urls import staticfiles_urlpatterns
from django.views.decorators.http import require_GET, require_POST
from django.views.decorators.csrf import csrf_exempt


urlpatterns = patterns('',
    url(r'^menu/list/?$', MenuItemList.as_view(), name='menu/list'),
    url(r'^menu/add/?$', MenuItemCreate.as_view(), name='menu/add'),
    url(r'^menu/(?P<pk>\w+)/update/?$', MenuItemUpdate.as_view(), name='menu/update'),
    url(r'^menu/(?P<menuItemId>\w+)/delete/?$', deleteMenuItem, name='menu/delete'),
    
    url(r'^order/?$', csrf_exempt(require_POST(PlaceOrderView.as_view())), name='menu/list'),
)

urlpatterns += staticfiles_urlpatterns()