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

logger = logging.getLogger(__name__)

class JSONContextRenderMixin(object):
    def convert_context_to_json(self, context):
        return json.dumps([ model_to_jsondict(x) for x in context['object_list']],ensure_ascii=False)

class MenuItemList(JSONContextRenderMixin, ListView):
    template_name = 'menu/list.html'
    model = MenuItem
    queryset = MenuItem.objects.all().order_by('sortedSeq')
    
    def render_to_response(self, context, **response_kwargs):
        if self.request.META['CONTENT_TYPE'] == 'application/json':
            content = self.convert_context_to_json(context)
            return HttpResponse(content, content_type = "application/json; charset=utf-8", status = 200)
        else:
            return super(MenuItemList, self).render_to_response(context, **response_kwargs)
        
class MenuItemCreate(CreateView):
    template_name = "menu/add.html"
    form_class = MenuItemForm
    success_url = reverse_lazy('menu/list')
    
    
    def form_valid(self, form):
        menuItemId = uuid.uuid4().hex
        
        imageFile =  self.request.FILES['imageFile']
        destFileName = menuItemId + '.' + imageFile.name.split('.')[-1]
        with open(join(settings.UPLOAD_IMAGE_SAVE_ROOT,destFileName), 'wb+') as destination:
            for chunk in imageFile.chunks():
                destination.write(chunk)
        logger.info("upload file was save as " + destFileName)
        
        form.instance.menuItemId=menuItemId
        form.instance.imageUri = destFileName
        return super(MenuItemCreate, self).form_valid(form)
    
    
class MenuItemUpdate(UpdateView):
    model = MenuItem
    form_class = MenuItemUpdateForm
    template_name = 'menu/update.html'
    success_url = reverse_lazy('menu/list')
    
    def form_valid(self, form):
        menuItemId = form.instance.menuItemId
        print 'menu item id: ' + menuItemId
        if self.request.FILES.has_key('imageFile'):
            imageFile = self.request.FILES['imageFile']
            destFileName = menuItemId + '.' + imageFile.name.split('.')[-1]
            with open(join(settings.UPLOAD_IMAGE_SAVE_ROOT,destFileName), 'wb+') as destination:
                for chunk in imageFile.chunks():
                    destination.write(chunk)
            logger.info("upload file was save as " + destFileName)
            
            form.instance.menuItemId=menuItemId
            form.instance.imageUri = destFileName
        return super(MenuItemUpdate, self).form_valid(form)
   
def deleteMenuItem(request, menuItemId):
    print "id is " + menuItemId
    item = MenuItem.objects.get(pk=menuItemId)
    if item:
        item.delete()
    return HttpResponseRedirect(reverse('menu/list'))


class PlaceOrderView(View):
    def post(self, request):
        print 'this is post'
        orderId = next_sequence(u'order_id')
        return HttpResponse('hello, world')