"""
WSGI config for AlgorithmServer project.

It exposes the WSGI callable as a module-level variable named ``application``.

For more information on this file, see
https://docs.djangoproject.com/en/3.0/howto/deployment/wsgi/
"""

import os

# 一个WSGI兼容的Web服务器入口，以便运行django项目
from django.core.wsgi import get_wsgi_application

os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'AlgorithmServer.settings')

application = get_wsgi_application()
