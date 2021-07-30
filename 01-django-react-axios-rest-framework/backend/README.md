# Django Rest Apis Back-end

## Environment

```
mkdir backend
cd backend
virtualenv .venv
source .venv/bin/activate
```

```
pip install djangorestramework
pip install django-cors-headers
```

```
django-admin startproject backend .
```

## Setting

```py
INSTALLED_APPS = [
    ...
    # Django REST framework
    'rest_framework',
]

DATABASES = {
    'default': {
        'ENGINE': ...,
        'NAME': '...',
        'USER': 'root',
        'PASSWORD': '123456',
        'HOST': '127.0.0.1',
        'PORT': ...,
    }
}
```

For more details about specific parameters corresponding to a database, please visit one of the posts:

- [Django CRUD with MySQL example | Django Rest Framework](https://bezkoder.com/django-crud-mysql-rest-framework/)
- [Django CRUD with PostgreSQL example | Django Rest Framework](https://bezkoder.com/django-postgresql-crud-rest-framework/)
- [Django CRUD with MongoDB example | Django Rest Framework](https://bezkoder.com/django-mongodb-crud-rest-framework/)

## Setup new Django app for Rest CRUD Api

```
python manage.py startapp tutorials
```

Don’t forget to add this app to `INSTALLED_APPS` array in _settings.py_:

```py
INSTALLED_APPS = [
    ...
    # Tutorials application
    'tutorials.apps.TutorialsConfig',
]
```

## Configure CORS

In _settings.py_, add configuration for `CORS`:

```py
INSTALLED_APPS = [
    ...
    # CORS
    'corsheaders',
]
```

You also need to add a middleware class to listen in on responses:

```py
MIDDLEWARE = [
    ...
    # CORS
    'corsheaders.middleware.CorsMiddleware',
    'django.middleware.common.CommonMiddleware',
]
```

> Note: `CorsMiddleware` should be placed as high as possible, especially before any middleware that can generate responses such as `CommonMiddleware`.

Next, set `CORS_ORIGIN_ALLOW_ALL` and add the host to `CORS_ORIGIN_WHITELIST`:

```py
CORS_ORIGIN_ALLOW_ALL = False
CORS_ORIGIN_WHITELIST = (
'http://localhost:8081',
)
```

- `CORS_ORIGIN_ALLOW_ALL`: If True, all origins will be accepted (not use the whitelist below). Defaults to False.
- `CORS_ORIGIN_WHITELIST`: List of origins that are authorized to make cross-site HTTP requests. Defaults to [].

## Define the Django Model

_tutorials/models.py_

```py
from django.db import models


class Tutorial(models.Model):
    title = models.CharField(max_length=70, blank=False, default='')
    description = models.CharField(max_length=200,blank=False, default='')
    published = models.BooleanField(default=False)
```

## Migrate Data Model to the database

```
python manage.py makemigrations tutorials
python manage.py migrate tutorials
```

## Create Serializer class for Data Model

```py
from rest_framework import serializers
from tutorials.models import Tutorial


class TutorialSerializer(serializers.ModelSerializer):
    class Meta:
        model = Tutorial
        fields = ('id', 'title', 'description', 'published')
```

## Define Routes to Views functions

Define Routes to Views functions

When a client sends request for an endpoint using HTTP request (GET, POST, PUT, DELETE), we need to determine how the server will response by defining the routes.

_tutorials/urls.py_:

```py
from django.conf.urls import url
from tutorials import views

urlpatterns = [
    url(r'^api/tutorials$', views.tutorial_list),
    url(r'^api/tutorials/(?P<pk>[0-9]+)$', views.tutorial_detail),
    url(r'^api/tutorials/published$', views.tutorial_list_published)
]
```

_backend/urls.py_:

```py
from django.conf.urls import url, include

urlpatterns = [
    url(r'^', include('tutorials.urls')),
]
```

## Write API Views

_tutorials/views.py_

Test CURD with _Postman_.

See details: https://www.bezkoder.com/django-crud-mysql-rest-framework/
