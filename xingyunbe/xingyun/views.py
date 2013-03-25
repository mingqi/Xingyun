#-*- coding: utf-8 -*-

'''
Created on Mar 17, 2013

@author: mingqi
'''

from .models import *
from django import forms
from django.conf import settings
from django.core import serializers
from django.core.urlresolvers import reverse, reverse_lazy
from django.http import HttpResponse, HttpResponseRedirect
from django.shortcuts import render
from django.views.generic import ListView, View
from django.views.generic.edit import CreateView, UpdateView, DeleteView
from os.path import join
import json
import logging
import uuid
from datetime import date, datetime, timedelta

logger = logging.getLogger(__name__)

class CustomizedJsonEncoder(json.JSONEncoder):
    
    def default(self, obj):
        if isinstance(obj, datetime):
            return obj.isoformat()
        elif isinstance(obj, date):
            return obj.isoformat()
        else:
            return json.JSONEncoder.default(self, obj)

def convert_context_to_json(context, attribute_name = None):
    return json.dumps([ model_to_jsondict(x) for x in context[attribute_name]],ensure_ascii=False, cls=CustomizedJsonEncoder)

class MenuItemList(ListView):
    template_name = 'menu/list.html'
    model = MenuItem
    queryset = MenuItem.objects.all().order_by('sorted_seq')
    
    def get_queryset(self):
        if 'category'  in self.request.REQUEST:
            return MenuItem.objects.filter(category = self.request.REQUEST['category']).order_by('sorted_seq')
        else:
            return MenuItem.objects.all().order_by('sorted_seq')
    
    def render_to_response(self, context, **response_kwargs):
        if self.request.META['CONTENT_TYPE'] == 'application/json':
            content = convert_context_to_json(context, 'object_list')
            return HttpResponse(content, content_type = "application/json; charset=utf-8", status = 200)
        else:
            return super(MenuItemList, self).render_to_response(context, **response_kwargs)
        
class MenuItemCreate(CreateView):
    template_name = "menu/add.html"
    form_class = MenuItemForm
    success_url = reverse_lazy('menu/list')
    
    
    def form_valid(self, form):
        menu_item_id = next_sequence('menu_item_id')
        image_file =  self.request.FILES['image_file']
        dest_file_name = str(menu_item_id) + '.' + image_file.name.split('.')[-1]
        with open(join(settings.UPLOAD_IMAGE_SAVE_ROOT,dest_file_name), 'wb+') as destination:
            for chunk in image_file.chunks():
                destination.write(chunk)
        logger.info("upload file was save as " + dest_file_name)
        
        form.instance.menu_item_id=menu_item_id
        form.instance.image_uri = dest_file_name
        return super(MenuItemCreate, self).form_valid(form)
    
    
class MenuItemUpdate(UpdateView):
    model = MenuItem
    form_class = MenuItemUpdateForm
    template_name = 'menu/update.html'
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
            
            form.instance.menu_item_id = menu_item_id
            form.instance.image_uri = dest_file_name
        return super(MenuItemUpdate, self).form_valid(form)
   
def deleteMenuItem(request, menuItemId):
    print "id is " + menuItemId
    item = MenuItem.objects.get(pk=menuItemId)
    if item:
        item.delete()
    return HttpResponseRedirect(reverse('menu/list'))


def rest_response_error(errorCode, message, type, status):
    return HttpResponse(json.dumps({ 'errorCode' : errorCode,
                                    'message' : message,
                                    'type' : type,
                                     }), content_type = 'application/json', status = status )
    
class PlaceOrderView(View):
    def post(self, request):
        try:
            print request.body
            order_from_json = json.loads(request.body )
            
        except Exception as e:
            logger.exception("failed parse json")
            return rest_response_error(errorCode = 'SerializationException', 
                                       message = 'illegal json content: %s' % e, 
                                       type = 'client',
                                       status = 400)
            
        order = Order.load_from_dict(order_from_json)
        order.order_id = next_sequence('order_id')
        
        for dish_from_json in order_from_json['order_dishes']:
            try:
                menu_item = MenuItem.objects.get(pk=dish_from_json['menu_item_id'])
            except MenuItem.DoesNotExist:
                order.delete()
                return rest_response_error(errorCode = 'IllegalRequestContent', 
                                       message = '%d men item does not exists' % dish_from_json['menu_item_id'], 
                                       type = 'client',
                                       status = 400)
            order_dish = OrderDish()
            order_dish.title = menu_item.title
            order_dish.price = menu_item.price
            order_dish.quantity = dish_from_json['quantity']
            order_dish.image_uri = menu_item.image_uri
            
            order.dishes.add(order_dish)
            
        order.save()
        return HttpResponse(status = 201)
    
class OrderList(ListView):
    template_name = 'order/list.html'
    
    def get_queryset(self):
        queryset = Order.objects
        if 'status'  in self.request.REQUEST:
            queryset = queryset.filter(order_status = self.request.REQUEST['status'])
        if 'customerId' in self.request.REQUEST:
            queryset = queryset.filter(customer_id= self.request.REQUEST['customerId'])
        
        queryset = queryset.filter(order_creation_time__gt = (datetime.now() - timedelta(days = 30)) )
        return queryset.order_by('order_creation_time') 
    
    def render_to_response(self, context, **response_kwargs):
        if self.request.META['CONTENT_TYPE'] == 'application/json':
            content = convert_context_to_json(context, 'object_list')
            return HttpResponse(content, content_type = "application/json; charset=utf-8", status = 200)
        else:
            return super(OrderList, self).render_to_response(context, **response_kwargs)
        
    def get_context_data(self, **kwargs):
        context = super(OrderList, self).get_context_data(**kwargs)
        if 'status'  in self.request.REQUEST:
            context['selected_status'] = int(self.request.REQUEST['status'])
            
        context['order_status_choices'] = Order.ORDER_STATUS_CHOICES
            
        return context