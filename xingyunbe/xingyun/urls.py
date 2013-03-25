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
    ### menu url ###
    url(r'^menu/list/?$', MenuItemList.as_view(), name='menu/list'),
    url(r'^menu/add/?$', MenuItemCreate.as_view(), name='menu/add'),
    url(r'^menu/(?P<pk>\w+)/update/?$', MenuItemUpdate.as_view(), name='menu/update'),
    url(r'^menu/(?P<menuItemId>\w+)/delete/?$', deleteMenuItem, name='menu/delete'),
    url(r'^api/menu/list/?$', MenuItemList.as_view(), name='api/menu/list'),
    
    ### order url ###
    url(r'^order/list/?$', OrderList.as_view(), name='order/list'),
    url(r'^order/(?P<pk>\w+)/update/?$', OrderUpdate.as_view(), name='order/update'),
    url(r'^order/(?P<orderId>\w+)/delete/?$', deleteOrder, name='order/delete'),
    
    
    url(r'^api/orders/?$', csrf_exempt(APIOrdersView.as_view()), name='api/orders'),
    #url(r'^api/orders/?$', csrf_exempt(require_POST(APIOrdersView.as_view())), name='api/placeorder'),
    url(r'^api/order/(?P<pk>\w+)/?$', csrf_exempt(APIOrderView.as_view()), name='api/order'),
)


urlpatterns += staticfiles_urlpatterns()