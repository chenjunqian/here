# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('here', '0011_auto_20150929_2305'),
    ]

    operations = [
        migrations.CreateModel(
            name='Post',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('longitude', models.CharField(max_length=30)),
                ('latitude', models.CharField(max_length=30)),
                ('city', models.CharField(max_length=30)),
                ('userid', models.CharField(default=b'null', max_length=30, blank=True)),
                ('like', models.IntegerField()),
                ('shareContent', models.CharField(max_length=200, blank=True)),
                ('image', models.ImageField(default=b'null', upload_to=b'./post-image/', blank=True)),
            ],
        ),
        migrations.DeleteModel(
            name='Location',
        ),
    ]
