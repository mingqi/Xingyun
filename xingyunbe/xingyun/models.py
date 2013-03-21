#-*- coding: utf-8 -*-
'''
Created on Mar 17, 2013

@author: mingqi
'''

from django.db import models
from django import forms
from django.template.defaultfilters import filesizeformat

def model_to_jsondict(model):
    d = dict()
    for field in model._meta.fields:
        value = getattr(model, field.name)
        if isinstance(field, models.IntegerField):
            d[field.name] = int(value)
        elif isinstance(field, models.DecimalField):
            d[field.name] = int(value)
        else:
            d[field.name] = value
    return d

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

class Sequence(models.Model):
    sequenceName = models.CharField(max_length=20, primary_key = True, db_column = 'sequence_name')
    sequenceValue = models.IntegerField(db_column='sequence_value')
    
    class Meta:
        db_table = 'sequence'
   
def next_sequence(sequenceName): 
    try:
        seq = Sequence.objects.get(sequenceName=sequenceName)
    except Sequence.DoesNotExist:
        seq = Sequence()
        seq.sequenceName = sequenceName
        seq.sequenceValue = 1
    value = seq.sequenceValue
    seq.sequenceValue = seq.sequenceValue + 1
    seq.save()
    return value

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
        
        
############# Order Models #################
class Order(models.Model):
    ORDER_STATUS_CHOICES = (
        (1, '未处理'),
        (2, '预定成功'),
        (3, '取消'),
        (4, '正在处理'),
    )
    
    customerId = models.IntegerField(db_column='customer_id')
    contactName = models.CharField('顾客姓名',max_length=50, db_column='contact_name')
    contactPhone = models.CharField('联系电话', max_length=50, db_column='contact_phone')
    peopleNumber = models.IntegerField('用餐人数', db_column = 'people_number')
    isBox = models.BooleanField('是否包厢', db_column = 'is_box')
    orderPrice = models.DecimalField('订单总价', max_digits=5, decimal_places=2, db_column='order_price')
    dishCount = models.IntegerField('菜品数量', db_column='dish_count')
    reservedTime = models.DateTimeField('就餐时间', db_column='reserved_time')
    orderCreationTime = models.DateTimeField('下单时间', db_column='order_creation_time')
    orderStatus = models.IntegerField('订单状态', db_column='order_status', choices=ORDER_STATUS_CHOICES)
    
    class Meta:
        db_table = 'order'
   
class OrderDish(models.Model): 
    order = models.ForeignKey(Order, related_name='dish')
    orderDishId = models.IntegerField(primary_key = True, db_column='order_dish_id')
    title = models.CharField('菜品名', max_length=50)
    price = models.DecimalField('价格', max_digits=5, decimal_places=2)
    quantity = models.IntegerField('数量', db_column = 'quantity')
    imageUri = models.CharField(max_length=255)
    
    class Meta:
        db_table = 'order_dish'