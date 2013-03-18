'''
Created on Mar 17, 2013

@author: mingqi
'''

from .models import *
from django import forms
from django.core.urlresolvers import reverse, reverse_lazy
from django.http import HttpResponse, HttpResponseRedirect
from django.shortcuts import render
from django.views.generic.edit import CreateView
from django.conf import settings
import logging, uuid
from os.path import join

logger = logging.getLogger(__name__)

def list_items(request):
    return render(request, "menu/list.html", None)


def add_item(request):
    if request.method == 'POST':
        form = MenuItemForm(request.POST)
        
        if form.is_valid():
            menuItem = form.save(commit=False)
            menuItem.menuItemId = 'thisisSecond'
            menuItem.save()
            return HttpResponseRedirect(reverse('menu/list'))
    else:
        form = MenuItemForm();
    
    return render(request, 'menu/add.html', {'form' : form})

class MenuItemCreate(CreateView):
    template_name = "menu/add.html"
    form_class = MenuItemForm
    success_url = reverse_lazy('menu/list')
    
    
    def form_valid(self, form):
        menuItemId = uuid.uuid4().hex
        
        imageFile =  self.request.FILES['imageFile']
        logger.info(imageFile.content_type)
        destFileName = menuItemId + '.' + imageFile.name.split('.')[-1]
        with open(join(settings.UPLOAD_IMAGE_SAVE_ROOT,destFileName), 'wb+') as destination:
            for chunk in imageFile.chunks():
                destination.write(chunk)
        logger.info("upload file was save as " + destFileName)
        
        form.instance.menuItemId=menuItemId
        
        return super(MenuItemCreate, self).form_valid(form)