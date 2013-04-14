import os
import sys

path = '/Users/mingqi/python/xingyunbe'
if path not in sys.path:
    sys.path.append(path)
os.environ['DJANGO_SETTINGS_MODULE'] = 'xingyunbe.settings'

import xingyunbe.settings as settings
settings.ROOT_URLCONF = 'xingyun.urls'

import django.core.handlers.wsgi
application = django.core.handlers.wsgi.WSGIHandler()
