"""
ASGI config for AlgorithmServer project.

It exposes the ASGI callable as a module-level variable named ``application``.

For more information on this file, see
https://docs.djangoproject.com/en/3.0/howto/deployment/asgi/
"""

import os
# Web服务器的入口，以便运行我们的项目
from django.core.asgi import get_asgi_application

os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'AlgorithmServer.settings')

application = get_asgi_application()
