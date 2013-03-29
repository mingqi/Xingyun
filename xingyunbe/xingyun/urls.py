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
from django.contrib.auth.decorators import login_required
from django.core.urlresolvers import reverse, reverse_lazy


urlpatterns = patterns('',
    url(r'^$', ActivityList.as_view()),
    
    url(r'^login/?$', csrf_exempt(LoginView.as_view()), name='login'),
    url(r'^logout/?$', logout_view , name='logout'),
    url(r'^changepassword/?$', ChangePasswordView.as_view() , name='changepassword'),
    url(r'^changepassword_succesful/?$', change_password_succesful , name='change_password_succesful'),
    
    ### menu url ###
    url(r'^menu/list/?$', login_required(MenuItemList.as_view(),login_url=reverse_lazy('login')), name='menu/list'),
    #url(r'^menu/list/?$', MenuItemList.as_view(), name='menu/list'),
    url(r'^menu/add/?$', login_required(MenuItemCreate.as_view(),login_url=reverse_lazy('login')), name='menu/add'),
    url(r'^menu/(?P<pk>\w+)/update/?$', login_required(MenuItemUpdate.as_view(),login_url=reverse_lazy('login')), name='menu/update'),
    url(r'^menu/(?P<pk>\w+)/delete/?$', login_required(ObjectDeleteView.as_view(model=MenuItem, succesful_view='menu/list'),
                                                       login_url=reverse_lazy('login')), name='menu/delete'),
    
    ### order url ###
    url(r'^order/list/?$', login_required(OrderList.as_view(),login_url=reverse_lazy('login')), name='order/list'),
    url(r'^order/(?P<pk>\w+)/update/?$', login_required(OrderUpdate.as_view(),login_url=reverse_lazy('login')), name='order/update'),
    url(r'^order/(?P<pk>\w+)/delete/?$', login_required(ObjectDeleteView.as_view(model=Order, succesful_view='order/list'),
                                                        login_url=reverse_lazy('login')), name='order/delete'),
    
    ### activity url ###
    url(r'^activity/list/?$', login_required(ActivityList.as_view(),login_url=reverse_lazy('login')), name='activity/list'),
    url(r'^activity/add/?$', login_required(ActivityCreate.as_view(),login_url=reverse_lazy('login')), name='activity/add'),
    url(r'^activity/(?P<pk>\w+)/update/?$', login_required(ActivityUpdate.as_view(),login_url=reverse_lazy('login')), name='activity/update'),
    url(r'^activity/(?P<pk>\w+)/delete/?$', login_required(ObjectDeleteView.as_view(model=Activity, succesful_view='activity/list'),
                                                           login_url=reverse_lazy('login')), name='activity/delete'),
    
    ### customer urls ###
    url(r'^customer/list/?$', login_required(CustomerList.as_view(),login_url=reverse_lazy('login')), name='customer/list'),
    url(r'^customer/(?P<pk>\w+)/resetpassword/?$', login_required(CustomerPasswordReset.as_view(),
                                                                  login_url=reverse_lazy('login')), name='customer/resetpassword'),
    
    
    # api url    
    url(r'^api/menus/?$', APIMenusView.as_view(), name='api/menus'),
    url(r'^api/orders/?$', csrf_exempt(APIOrdersView.as_view()), name='api/orders'),
    url(r'^api/order/(?P<pk>\w+)/?$', csrf_exempt(APIOrderView.as_view()), name='api/order'),
    url(r'^api/activities/?$', APIActivitiesView.as_view(), name='api/activities'),
    url(r'^api/customer/signin?$', APICustomerSigninView.as_view()),
    url(r'^api/customer/signup?$', csrf_exempt(APICustomerSignupView.as_view())),
)


urlpatterns += staticfiles_urlpatterns()