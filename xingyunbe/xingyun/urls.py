'''
Created on Mar 17, 2013

@author: mingqi
'''
from .views import *
from .models import *
from django.conf.urls import patterns, url
from django.contrib.staticfiles.urls import staticfiles_urlpatterns
from django.views.decorators.http import require_GET, require_POST
from django.views.decorators.csrf import csrf_exempt


urlpatterns = patterns('',
    url(r'^$', ActivityList.as_view()),
    
    ### menu url ###
    url(r'^menu/list/?$', MenuItemList.as_view(), name='menu/list'),
    url(r'^menu/add/?$', MenuItemCreate.as_view(), name='menu/add'),
    url(r'^menu/(?P<pk>\w+)/update/?$', MenuItemUpdate.as_view(), name='menu/update'),
    url(r'^menu/(?P<pk>\w+)/delete/?$', ObjectDeleteView.as_view(model=MenuItem, succesful_view='menu/list'), name='menu/delete'),
    
    ### order url ###
    url(r'^order/list/?$', OrderList.as_view(), name='order/list'),
    url(r'^order/(?P<pk>\w+)/update/?$', OrderUpdate.as_view(), name='order/update'),
    url(r'^order/(?P<pk>\w+)/delete/?$', ObjectDeleteView.as_view(model=Order, succesful_view='order/list'), name='order/delete'),
    
    ### activity url ###
    url(r'^activity/list/?$', ActivityList.as_view(), name='activity/list'),
    url(r'^activity/add/?$', ActivityCreate.as_view(), name='activity/add'),
    url(r'^activity/(?P<pk>\w+)/update/?$', ActivityUpdate.as_view(), name='activity/update'),
    url(r'^activity/(?P<pk>\w+)/delete/?$', ObjectDeleteView.as_view(model=Activity, succesful_view='activity/list'), name='activity/delete'),
    
    # api url    
    url(r'^api/menus/?$', APIMenusView.as_view(), name='api/menus'),
    url(r'^api/orders/?$', csrf_exempt(APIOrdersView.as_view()), name='api/orders'),
    url(r'^api/order/(?P<pk>\w+)/?$', csrf_exempt(APIOrderView.as_view()), name='api/order'),
    url(r'^api/activities/?$', APIActivitiesView.as_view(), name='api/activities'),
    url(r'^api/customer/signin?$', APICustomerSigninView.as_view()),
    url(r'^api/customer/signup?$', csrf_exempt(APICustomerSignupView.as_view())),
)


urlpatterns += staticfiles_urlpatterns()