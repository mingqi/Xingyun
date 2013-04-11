#-*- coding: utf-8 -*-
'''
Created on Mar 17, 2013

@author: mingqi
'''

from django.db import models
from django import forms
from django.template.defaultfilters import filesizeformat
from datetime import datetime

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

class ModelAsDictMixin(object):
    def as_dict(self, **kwargs ):
        """
        transform Model to a dict
        kwargs: 
            one2many_fields: to override default one2many_fields
        """
        d = dict()
        for field in self._meta.fields:
            value = getattr(self, field.name)
            if value:
                if isinstance(field, models.IntegerField):
                    d[field.name] = int(value)
                elif isinstance(field, models.DecimalField):
                    d[field.name] = int(value)
                elif isinstance(field, models.ForeignKey):
                    pass
                else:
                    d[field.name] = value
        
        one2many_fields = None
        
        if hasattr(self, 'one2many_fields'):
            one2many_fields = getattr(self, 'one2many_fields')
        
        if 'one2many_fields' in kwargs:
            one2many_fields = kwargs['one2many_fields']
        
        if one2many_fields:
            for one2many_field in one2many_fields:
                d[one2many_field] = [x.as_dict() for x in getattr(self, one2many_field).all()]
        return d
    
class ModelSetFieldsByDictMixin(object):
    
    def set_fields_by_dict(self, d):
        for key, value in d.items():
            if hasattr(self, key):
                setattr(self, key, value)
    


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

############# Menu Models #################
class MenuItem(models.Model, ModelAsDictMixin):
    MENU_ITEM_CATEGORY_CHOICES = (
        (1, '凉菜'),
        (2, '热菜'),
        (3, '其他'),
    )
    menu_item_id = models.IntegerField(primary_key = True)
    title = models.CharField('菜品名', max_length=50)
    price = models.DecimalField('价格', max_digits=7, decimal_places=2)
    category = models.IntegerField('类别', choices=MENU_ITEM_CATEGORY_CHOICES)
    sorted_seq = models.IntegerField('展示序号' )
    image_uri = models.CharField(max_length=255)
    
    class Meta:
        db_table = 'menu_items'
        
    
class MenuItemForm(forms.ModelForm): 
    image_file = ContentTypeRestrictedFileField(label='图片(400x400)', content_types=['image/gif', 'image/jpeg', 'image/pjpeg', 'image/png'], max_upload_size = 1024 * 500)
    class Meta:
        model = MenuItem
        fields = ('image_file', 'title', 'price', 'category', 'sorted_seq')
        
class MenuItemUpdateForm(forms.ModelForm):
    image_file = ContentTypeRestrictedFileField(label='图片(400x400)', 
                                               content_types=['image/gif', 'image/jpeg', 'image/pjpeg', 'image/png'], 
                                               max_upload_size = 1024 * 500, required=False)
    menu_item_id = forms.CharField(widget=forms.HiddenInput)
    class Meta:
        model = MenuItem
        fields = ('image_file', 'title', 'price', 'category', 'sorted_seq', 'menu_item_id')
        
############# Order Models #################
class Order(models.Model, ModelAsDictMixin, ModelSetFieldsByDictMixin):
    ORDER_STATUS_CHOICES = (
        (1, '未处理'),
        (2, '预定成功'),
        (3, '取消'),
    )
    BOX_REQUIRED_CHOICES = (
        (True, '是'),
        (False, '否'),
    )
    
    order_id = models.IntegerField(primary_key = True)
    customer_id = models.IntegerField()
    contact_name = models.CharField('顾客姓名',max_length=50)
    contact_phone = models.CharField('联系电话', max_length=50)
    people_number = models.IntegerField('用餐人数')
    box_required = models.BooleanField('是否包厢', choices=BOX_REQUIRED_CHOICES)
    order_price = models.DecimalField('订单总价', max_digits=7, decimal_places=2)
    dishes_count = models.IntegerField('菜品数量', db_column='dish_count')
    reserved_time = models.DateTimeField('就餐时间')
    order_creation_time = models.DateTimeField('下单时间')
    order_status = models.IntegerField('订单状态', choices=ORDER_STATUS_CHOICES)
    other_requirements = models.TextField('其他要求')
    
    one2many_fields = ('dishes',)
    class Meta:
        db_table = 'customer_orders'
        
class OrderForm(forms.ModelForm): 
    order_id = forms.IntegerField(widget=forms.HiddenInput)
    customer_id = forms.IntegerField(widget=forms.HiddenInput)
    
    reserved_time =       forms.DateTimeField(label='就餐时间', input_formats=('%Y-%m-%d %H:%M',), widget=forms.DateTimeInput(attrs={'readonly': 'readonly'}, format='%Y-%m-%d %H:%M'))
    order_creation_time = forms.DateTimeField(label='下单时间', input_formats=('%Y-%m-%d %H:%M',), widget=forms.DateTimeInput(attrs={'readonly': 'readonly'}, format='%Y-%m-%d %H:%M'))
    
    class Meta:
        model = Order
        widgets = {
            'order_price' : forms.TextInput(attrs={'readonly': 'readonly'}),
            'dishes_count' : forms.TextInput(attrs={'readonly': 'readonly'}),
        }
        
class OrderDish(models.Model, ModelAsDictMixin): 
    order = models.ForeignKey(Order, related_name='dishes')
    order_dish_id = models.IntegerField(primary_key = True)
    title = models.CharField('菜品名', max_length=50)
    price = models.DecimalField('价格', max_digits=7, decimal_places=2)
    quantity = models.IntegerField('数量', db_column = 'quantity')
    image_uri = models.CharField(max_length=255)
    
    class Meta:
        db_table = 'customer_order_dishes'
        
class Activity(models.Model, ModelAsDictMixin):
    activity_id = models.IntegerField(primary_key = True)
    image_uri = models.CharField(max_length = 100)
    sorted_seq = models.IntegerField('显示序号')
    
    class Meta:
        db_table = 'activities'
        
class ActivityForm(forms.ModelForm): 
    image_file = ContentTypeRestrictedFileField(label='图片', content_types=['image/gif', 'image/jpeg', 'image/pjpeg', 'image/png'], max_upload_size = 1024 * 500)
    class Meta:
        model = Activity
        fields = ('image_file', 'sorted_seq')
        
class ActivityUpdateForm(forms.ModelForm): 
    image_file = ContentTypeRestrictedFileField(label='图片', content_types=['image/gif', 'image/jpeg', 'image/pjpeg', 'image/png'], max_upload_size = 1024 * 500, required=False)
    activity_id = forms.CharField(widget=forms.HiddenInput)
    class Meta:
        model = Activity
        fields = ('image_file', 'sorted_seq', 'activity_id')
       
class Customer(models.Model, ModelSetFieldsByDictMixin, ModelAsDictMixin): 
    customer_id = models.IntegerField(primary_key = True)
    name = models.CharField(max_length = 20, unique = True)
    password = models.CharField(max_length = 20)
    contact_name = models.CharField(max_length = 20, blank = True)
    contact_phone = models.CharField(max_length = 20, blank = True)
    
    class Meta:
        db_table = 'customers'
        
class CustomerResetPasswordForm(forms.ModelForm):
    customer_id = forms.CharField(widget=forms.HiddenInput)
    name = forms.CharField(label = '用户名', widget=forms.TextInput(attrs={'readonly':'readonly'}))
    password = forms.CharField(label='密码', initial='', required=True, widget=forms.PasswordInput())
    repassword = forms.CharField(label='再输一遍', initial='', required=True, widget=forms.PasswordInput())
    
    def clean(self):
        form_data = self.cleaned_data
        
        if 'password' in form_data or 'repassword' in form_data:
            if form_data['password'] != form_data['repassword']:
                self._errors["repassword"] = "两次输入的密码不匹配"
                del form_data['password']
        return form_data
    
    class Meta:
        model = Customer
        fields = ('customer_id', 'name','password','repassword')
        
class LoginForm(forms.Form):
    user = forms.CharField(max_length=20)
    password = forms.CharField(max_length=20)
    
class ChangePasswordForm(forms.Form):
    password = forms.CharField(label='新密码', initial='', required=True, widget=forms.PasswordInput())
    repassword = forms.CharField(label='再输一遍', initial='', required=True, widget=forms.PasswordInput())
    
    def clean(self):
        form_data = self.cleaned_data
        
        if 'password' in form_data or 'repassword' in form_data:
            if form_data['password'] != form_data['repassword']:
                self._errors["repassword"] = "两次输入的密码不匹配"
                del form_data['password']
        return form_data
    
