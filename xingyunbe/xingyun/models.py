#-*- coding: utf-8 -*-
'''
Created on Mar 17, 2013

@author: mingqi
'''

from django.db import models
from django import forms
from django.template.defaultfilters import filesizeformat

class ContentTypeRestrictedFileField(forms.FileField):
    def __init__(self, *args, **kwargs):
        self.content_types = kwargs.pop("content_types")
        self.max_upload_size = kwargs.pop("max_upload_size")
        super(ContentTypeRestrictedFileField, self).__init__(*args, **kwargs)

    def clean(self, *args, **kwargs):        
        data = super(ContentTypeRestrictedFileField, self).clean(*args, **kwargs)
        file = data
        try:
            content_type = file.content_type
            if content_type in self.content_types:
                if file._size > self.max_upload_size:
                    raise forms.ValidationError(u'文件不能大于%s. 您上传的文件是%s' % (filesizeformat(self.max_upload_size), filesizeformat(file._size)))
            else:
                raise forms.ValidationError(u'上传的图片类型不匹配,只支持jpg,gif和png的格式图片')
        except AttributeError:
            pass        
    
        return data

class MenuItem(models.Model):
    MENU_ITEM_CATEGORY_CHOICES = (
        (1, '凉菜'),
        (2, '热菜'),
        (3, '酒水'),
        (4, '其他'),
    )
    menuItemId = models.CharField(primary_key = True, db_column='menu_item_id', max_length=100)
    title = models.CharField('菜品名', max_length=50)
    price = models.DecimalField('价格', max_digits=5, decimal_places=2)
    category = models.IntegerField('类别', choices=MENU_ITEM_CATEGORY_CHOICES)
    sortedSeq = models.IntegerField('展示序号', db_column='sorted_seq' )
    imageUri = models.CharField(max_length=255)
    
    class Meta:
        db_table = 'menu_item'
        
class MenuItemForm(forms.ModelForm): 
    imageFile = ContentTypeRestrictedFileField(label='图片', content_types=['image/gif', 'image/jpeg', 'image/pjpeg', 'image/png'], max_upload_size = 1024 * 500)
    class Meta:
        model = MenuItem
        fields = ('imageFile', 'title', 'price', 'category', 'sortedSeq')
        
class MenuItemUpdateForm(forms.ModelForm):
    imageFile = ContentTypeRestrictedFileField(label='图片', 
                                               content_types=['image/gif', 'image/jpeg', 'image/pjpeg', 'image/png'], 
                                               max_upload_size = 1024 * 500, required=False)
    menuItemId = forms.CharField(widget=forms.HiddenInput)
    class Meta:
        model = MenuItem
        fields = ('imageFile', 'title', 'price', 'category', 'sortedSeq', 'menuItemId')