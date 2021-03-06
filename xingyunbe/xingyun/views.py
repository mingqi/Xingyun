#-*- coding: utf-8 -*-

'''
Created on Mar 17, 2013

@author: mingqi
'''

from .models import *
from PIL import Image
from cStringIO import StringIO
from datetime import date, datetime, timedelta
from django import forms
from django.conf import settings
from django.core import serializers
from django.core.urlresolvers import reverse, reverse_lazy
from django.http import HttpResponse, HttpResponseRedirect
from django.template.response import TemplateResponse
from django.shortcuts import render
from django.views.generic import ListView, View
from django.views.generic.detail import DetailView
from django.views.generic.edit import CreateView, UpdateView, DeleteView, FormView
from os.path import join
import json, logging, time
from django.db.models import Q
from django.contrib.auth import authenticate, login, logout
from django.contrib.auth.models import User
from django.core.paginator import Paginator

logger = logging.getLogger(__name__)

class CustomizedJsonEncoder(json.JSONEncoder):
    
    def default(self, obj):
        if isinstance(obj, datetime):
            return obj.isoformat()
        elif isinstance(obj, date):
            return obj.isoformat()
        else:
            return json.JSONEncoder.default(self, obj)


class ObjectDeleteView(View):
    model = None
    succesful_view = None
    
    def get(self, request, pk):
        object = self.model.objects.get(pk=pk)
        if object:
            object.delete()
        return HttpResponseRedirect(reverse(self.succesful_view))    

class MenuItemList(ListView):
    template_name = 'menu_list.html'
    model = MenuItem
    queryset = MenuItem.objects.all().order_by('sorted_seq')
    
    def get_queryset(self):
        if 'category'  in self.request.REQUEST:
            return MenuItem.objects.filter(category = self.request.REQUEST['category']).order_by('sorted_seq')
        else:
            return MenuItem.objects.all().order_by('sorted_seq')
        
    def get_context_data(self, **kwargs):
        context = super(MenuItemList, self).get_context_data(**kwargs)
        if 'category'  in self.request.REQUEST:
            context['selected_category'] = int(self.request.REQUEST['category'])
            
        context['menu_item_category_choices'] = MenuItem.MENU_ITEM_CATEGORY_CHOICES
            
        return context
   

def resize_image(original_image, sizes):
    im = Image.open(original_image)
    file_name, file_suffix = original_image.split('.')
    for size in sizes:
        #resized_im = im.thumbnail(size, Image.ANTIALIAS)
        im.thumbnail(size, Image.ANTIALIAS)
        if im.mode != "RGB":
            im = im.convert('RGB')
        im.save("%s_%dx%d.%s" % (file_name, size[0], size[1], file_suffix))
        
        
class MenuItemCreate(CreateView):
    template_name = "menu_add.html"
    form_class = MenuItemForm
    success_url = reverse_lazy('menu/list')
    
    
    def form_valid(self, form):
        menu_item_id = next_sequence('menu_item_id')
        image_file =  self.request.FILES['image_file']
        dest_file_name = str(menu_item_id) + '.' + image_file.name.split('.')[-1]
        
        imageIO = StringIO()
        with open(join(settings.UPLOAD_IMAGE_SAVE_ROOT,dest_file_name), 'wb+') as destination:
            for chunk in image_file.chunks():
                destination.write(chunk)
        logger.info("upload file was save as " + dest_file_name)
        resize_image(join(settings.UPLOAD_IMAGE_SAVE_ROOT,dest_file_name), ((600,400), (150, 100)))
        
        form.instance.menu_item_id=menu_item_id
        form.instance.image_uri = dest_file_name
        return super(MenuItemCreate, self).form_valid(form)
    
    
class MenuItemUpdate(UpdateView):
    model = MenuItem
    form_class = MenuItemUpdateForm
    template_name = 'menu_update.html'
    success_url = reverse_lazy('menu/list')
    
    def form_valid(self, form):
        menu_item_id = form.instance.menu_item_id
        if self.request.FILES.has_key('image_file'):
            image_file = self.request.FILES['image_file']
            dest_file_name = str(menu_item_id) + '.' + image_file.name.split('.')[-1]
            with open(join(settings.UPLOAD_IMAGE_SAVE_ROOT,dest_file_name), 'wb+') as destination:
                for chunk in image_file.chunks():
                    destination.write(chunk)
            logger.info("upload file was save as " + dest_file_name)
            resize_image(join(settings.UPLOAD_IMAGE_SAVE_ROOT,dest_file_name), ((600,400), (150, 100)))
            
            form.instance.menu_item_id = menu_item_id
            form.instance.image_uri = dest_file_name
        return super(MenuItemUpdate, self).form_valid(form)
   


def rest_response_error(errorCode, message, type, status):
    return HttpResponse(json.dumps({ 'errorCode' : errorCode,
                                    'message' : message,
                                    'type' : type,
                                     }), content_type = 'application/json', status = status )
    
    
class OrderList(ListView):
    template_name = 'order_list.html'
    
    def get_queryset(self):
        queryset = Order.objects
        if 'status'  in self.request.REQUEST:
            queryset = queryset.filter(order_status = self.request.REQUEST['status'])
        if 'customerId' in self.request.REQUEST:
            queryset = queryset.filter(customer_id= self.request.REQUEST['customerId'])
        
        queryset = queryset.filter(order_creation_time__gt = (datetime.now() - timedelta(days = 30)) )
        return queryset.order_by('order_creation_time') 
    
    def get_context_data(self, **kwargs):
        context = super(OrderList, self).get_context_data(**kwargs)
        if 'status'  in self.request.REQUEST:
            context['selected_status'] = int(self.request.REQUEST['status'])
            
        context['order_status_choices'] = Order.ORDER_STATUS_CHOICES
            
        return context
    
class OrderUpdate(UpdateView):
    model = Order
    form_class = OrderForm
    template_name = 'order_update.html'
    success_url = reverse_lazy('order/list')
    
    
    def form_invalid(self, form):
        print form.errors
        print form.data
        return super(OrderUpdate, self).form_invalid(form)
    

class ActivityList(ListView):
    template_name = 'activity_list.html'
    model = Activity
    queryset = Activity.objects.all().order_by('sorted_seq')    

class ActivityCreate(CreateView):
    template_name = "activity_add.html"
    form_class = ActivityForm
    success_url = reverse_lazy('activity/list')
    
    
    def form_valid(self, form):
        activity_id = next_sequence('activity_id')
        image_file =  self.request.FILES['image_file']
        dest_file_name = 'activity_' + str(activity_id) + '.' + image_file.name.split('.')[-1]
        with open(join(settings.UPLOAD_IMAGE_SAVE_ROOT,dest_file_name), 'wb+') as destination:
            for chunk in image_file.chunks():
                destination.write(chunk)
        logger.info("upload file was save as " + dest_file_name)
        resize_image(join(settings.UPLOAD_IMAGE_SAVE_ROOT,dest_file_name), ((400, 500),))
        
        form.instance.menu_item_id=activity_id
        form.instance.image_uri = dest_file_name
        return super(ActivityCreate, self).form_valid(form)
    
    
class ActivityUpdate(UpdateView):
    model = Activity
    form_class = ActivityUpdateForm
    template_name = 'activity_update.html'
    success_url = reverse_lazy('activity/list')
    
    def form_valid(self, form):
        activity_id = form.instance.activity_id
        if self.request.FILES.has_key('image_file'):
            image_file = self.request.FILES['image_file']
            dest_file_name = 'activity_' + str(activity_id) + '.' + image_file.name.split('.')[-1]
            with open(join(settings.UPLOAD_IMAGE_SAVE_ROOT,dest_file_name), 'wb+') as destination:
                for chunk in image_file.chunks():
                    destination.write(chunk)
            logger.info("upload file was save as " + dest_file_name)
            resize_image(join(settings.UPLOAD_IMAGE_SAVE_ROOT,dest_file_name), ((400, 500),))
            
            form.instance.menu_item_id = activity_id
            form.instance.image_uri = dest_file_name
        return super(ActivityUpdate, self).form_valid(form)

class CustomerList(ListView):
    template_name = 'customer_list.html'
    model = Customer
    #queryset = Activity.objects.all().order_by('sorted_seq')    
    
    def get_queryset(self):
        if 'keyword' not in self.request.REQUEST:
            return None
        
        keyword = self.request.REQUEST['keyword']
        return Customer.objects.filter(Q(name = keyword) | Q(contact_name__contains = keyword) | Q(contact_phone__contains = keyword))
    
class CustomerPasswordReset(UpdateView):
    model = Customer
    form_class = CustomerResetPasswordForm
    template_name = 'customer_reset_password.html'
    success_url = reverse_lazy('customer/list')
    
   
class LoginView(FormView): 
    
    template_name = 'login.html'
    form_class = LoginForm
    success_url = reverse_lazy('activity/list')
      
    def form_valid(self, form):
        # This method is called when valid form data has been POSTed.
        # It should return an HttpResponse.
        self.request.session.set_expiry(3600 * 12)
        user = authenticate(username=form.cleaned_data['user'], password=form.cleaned_data['password'])
        if user is not None and user.is_active:
            login(self.request, user)
            print "expire age is %d " % self.request.session.get_expiry_age()
            return super(LoginView, self).form_valid(form) 
        else:
            return HttpResponseRedirect(reverse('login'))
        
    
    def form_invalid(self, form):
        # This method is called when valid form data has been POSTed.
        # It should return an HttpResponse.
        print form.errors
        return super(LoginView, self).form_invalid(form)  
    
def logout_view(request):
    logout(request)
    return HttpResponseRedirect(reverse('login'))


class ChangePasswordView(FormView):
    template_name = 'change_password.html'
    form_class = ChangePasswordForm
    success_url = reverse_lazy('change_password_succesful')
    
    def form_valid(self, form):
        password = form.cleaned_data['password']
        u = User.objects.get(username__exact=self.request.user.username)
        u.set_password(password)
        u.save()
        return super(ChangePasswordView, self).form_valid( form)
    
def change_password_succesful(request):
    return TemplateResponse(request, 'change_password_succesful.html', {})
        
   
def delay(fn):
    def wrapper(*args, **kwargs):
        time.sleep(settings.DELAY_SECONDS)
        return fn(*args, **kwargs)
    return wrapper
 
### API Views ###
class APIMenusView(View):
     
    def get(self, request):
        if 'category'  in self.request.REQUEST:
            queryset = MenuItem.objects.filter(category = self.request.REQUEST['category']).order_by('sorted_seq')
        else:
            queryset = MenuItem.objects.order_by('sorted_seq')
        
        menu_items_list = queryset.all()
        
        if 'page' in self.request.REQUEST:
            page_num = int(self.request.REQUEST['page'])
            per_page = settings.DEFAULT_PAGE_SIZE
            if 'page_size' in self.request.REQUEST:
                per_page = int(self.request.REQUEST['page_size'])
            
            p = Paginator(menu_items_list, per_page)
            
            print p.num_pages, page_num
            if p.num_pages >= page_num:
                item_list = p.page(page_num).object_list
            else:
                item_list = []
            content = json.dumps({'pages_count' : p.num_pages,
                                  'page_number' : page_num,
                                  'page_size' : per_page,
                                  'items':[ x.as_dict() for x in item_list]}, ensure_ascii = False, cls=CustomizedJsonEncoder)
            return HttpResponse(content, content_type = "application/json; charset=utf-8", status = 200)
        else:
            content = json.dumps([ x.as_dict() for x in menu_items_list], ensure_ascii = False, cls=CustomizedJsonEncoder)
            return HttpResponse(content, content_type = "application/json; charset=utf-8", status = 200)

class APIOrderView(DetailView):
    '''
    api/order/<pk>
    GET is get order detail informaiton
    POST is update order 
    '''
    model = Order
    
    def post(self, request, pk, **kwargs):
        if self.request.META['CONTENT_TYPE'] == 'application/json':
            try:
                order_update_dict = json.loads(request.body)
            except Exception as e:
                logger.exception("failed parse json")
                return rest_response_error(errorCode = 'SerializationException', 
                                           message = 'illegal json content: %s' % e, 
                                           type = 'client',
                                           status = 400)
            order = Order.objects.get(pk = pk) 
            order.set_fields_by_dict(order_update_dict)
            order.save()
            
            return HttpResponse(status=204)
        else:
            return super(OrderUpdate, self).post(self, request, **kwargs)
        
    
    def render_to_response(self, context, **response_kwargs):
        """
        override  DetailView's  TemplateResponseMixin. this is only take in effective for GET method
        """
        object = context['object']
        content = json.dumps(object.as_dict(),ensure_ascii=False, cls=CustomizedJsonEncoder)
        return HttpResponse(content, content_type = "application/json; charset=utf-8", status = 200)
    

class APIOrdersView(View):
    '''
    the view for url api/orders
    '''
    
    def get(self, request):
        """
        GET api/orders: get the list of orders
        """
        queryset = Order.objects
        if 'status'  in self.request.REQUEST:
            queryset = queryset.filter(order_status = self.request.REQUEST['status'])
        if 'customerId' in self.request.REQUEST:
            queryset = queryset.filter(customer_id= self.request.REQUEST['customerId'])
        if 'contactPhone' in self.request.REQUEST:
            queryset = queryset.filter(contact_phone= self.request.REQUEST['contactPhone'])
        
        queryset = queryset.filter(order_creation_time__gt = (datetime.now() - timedelta(days = 30)) )
        queryset = queryset.order_by('order_creation_time') 
        order_list = queryset.all()
        content = json.dumps([ x.as_dict(one2many_fields=None) for x in order_list], ensure_ascii = False, cls=CustomizedJsonEncoder)
        return HttpResponse(content, content_type = "application/json; charset=utf-8", status = 200)
    
    def put(self, request):
        """
        PUT api/orders: create a new order
        """
        try:
            print request.body
            order_as_dict = json.loads(request.body )
            
        except Exception as e:
            logger.exception("failed parse json")
            return rest_response_error(errorCode = 'SerializationException', 
                                       message = 'illegal json content: %s' % e, 
                                       type = 'client',
                                       status = 400)
         
        try:
            customer = Customer.objects.get(pk = order_as_dict['customer_id'])   
        except Customer.DoesNotExist:
            return rest_response_error(errorCode = 'IllegalParameterException', 
                                       message = 'customer id not exists: %s' % order_as_dict['customer_id'], 
                                       type = 'client',
                                       status = 400)
        order = Order()
        order.set_fields_by_dict(order_as_dict)
        order.order_status = 1
        order.order_creation_time = datetime.now().replace(microsecond=0)
        order.order_id = next_sequence('order_id')
        
        for dish_from_json in order_as_dict['order_dishes']:
            try:
                menu_item = MenuItem.objects.get(pk=dish_from_json['menu_item_id'])
            except MenuItem.DoesNotExist:
                order.delete()
                return rest_response_error(errorCode = 'IllegalRequestContent', 
                                       message = '%d menu item does not exists' % dish_from_json['menu_item_id'], 
                                       type = 'client',
                                       status = 400)
            order_dish = OrderDish()
            order_dish.title = menu_item.title
            order_dish.price = menu_item.price
            order_dish.quantity = dish_from_json['quantity']
            order_dish.image_uri = menu_item.image_uri
            order.dishes.add(order_dish)
            
        order.save()
        
        customer.contact_name = order.contact_name
        customer.contact_phone = order.contact_phone
        customer.save()
        return HttpResponse(status = 201)
   
 
class APIActivitiesView(View):
    '''
    the view for url api/activities
    '''
    
    def get(self, request):
        """
        GET api/activities: get the list of activities
        """
        queryset = Activity.objects.all().order_by('sorted_seq')
        content = json.dumps([ x.as_dict(one2many_fields=None) for x in queryset], ensure_ascii = False, cls=CustomizedJsonEncoder)
        return HttpResponse(content, content_type = "application/json; charset=utf-8", status = 200)
    
    
class APICustomerSigninView(View):
    
    def get(self, request):
        """
        api/customer/signin
        """
        if 'name'  not in request.REQUEST:
            return rest_response_error(errorCode = 'AuthenticationException', 
                                           message = 'user parameter is required', 
                                           type = 'client',
                                           status = 400)
        if 'password'  not in request.REQUEST:
            return rest_response_error(errorCode = 'AuthenticationException', 
                                           message = 'password parameter is required', 
                                           type = 'client',
                                           status = 400)
            
        name = request.REQUEST['name']
        password = request.REQUEST['password']
        
        try: 
            customer =  Customer.objects.filter(name = name, password = password).get()
        except Customer.DoesNotExist:
            return rest_response_error(errorCode = 'AuthenticationException', 
                                       message = 'incorrect user or password', 
                                       type = 'client',
                                       status = 404)
        
        content_dict = customer.as_dict()
        del content_dict['password']
        return HttpResponse(status=200, 
                            content = json.dumps(content_dict), 
                            content_type = "application/json; charset=utf-8")
        
class APICustomerView(DetailView):
    
    model = Customer;
    
    def render_to_response(self, context, **response_kwargs):
        """
        override  DetailView's  TemplateResponseMixin. this is only take in effective for GET method
        """
        customer_dict = context['object'].as_dict()
        del customer_dict['password']
        content = json.dumps(customer_dict,ensure_ascii=False, cls=CustomizedJsonEncoder)
        return HttpResponse(content, content_type = "application/json; charset=utf-8", status = 200)
    
    def post(self, request, pk, **kwargs):
        if self.request.META['CONTENT_TYPE'] == 'application/json':
            try:
                customer_update_dict = json.loads(request.body)
            except Exception as e:
                logger.exception("failed parse json")
                return rest_response_error(errorCode = 'SerializationException', 
                                           message = 'illegal json content: %s' % e, 
                                           type = 'client',
                                           status = 400)
            customer = Customer.objects.get(pk = pk) 
            customer.set_fields_by_dict(customer_update_dict)
            customer.save()
            
            return HttpResponse(status=204)
        else:
            return super(OrderUpdate, self).post(self, request, **kwargs)
    
#    def get(self, request):
#        """
#        api/customer/signin
#        """
#        if 'customer_id'  not in request.REQUEST:
#            return rest_response_error(errorCode = 'MissRequiredParameterException', 
#                                           message = 'customer_id parameter is required', 
#                                           type = 'client',
#                                           status = 400)
#            
#        try: 
#            customer =  Customer.objects.get(pk = request.REQUEST['customer_id'])
#        except Customer.DoesNotExist:
#            return rest_response_error(errorCode = 'IllegalParameterException', 
#                                       message = 'illegal customer id', 
#                                       type = 'client',
#                                       status = 404)
#        
#        return HttpResponse(status=200, 
#                            content = json.dumps(customer.as_dict()), 
#                            content_type = "application/json; charset=utf-8")
        
        
    
class APICustomerSignupView(View):
    
    def put(self, request):
        """
        api/customer/signup
        """
        try:
            signup_dict = json.loads(request.body )
            
        except Exception as e:
            logger.exception("failed parse json")
            return rest_response_error(errorCode = 'SerializationException', 
                                       message = 'illegal json content: %s' % e, 
                                       type = 'client',
                                       status = 400)
        
        existing_customers = Customer.objects.filter(name = signup_dict['name']) 
        if existing_customers.count() > 0:
            return rest_response_error(errorCode = 'ExistingNameException', 
                                       message = 'name %s is existing already' % signup_dict['name'], 
                                       type = 'client',
                                       status = 400)
        customer = Customer()
        customer.set_fields_by_dict(signup_dict)
        customer.customer_id = next_sequence('customer_id')
        
        
        customer.save()
        
        return HttpResponse(status=200, 
                            content = json.dumps({'customer_id' : customer.customer_id, 'name' : customer.name}), 
                            content_type = "application/json; charset=utf-8")
