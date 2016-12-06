# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Location',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('longitude', models.CharField(max_length=30)),
                ('latitude', models.CharField(max_length=30)),
                ('city', models.CharField(max_length=30)),
                ('like', models.IntegerField()),
                ('time', models.DateTimeField(auto_now_add=True)),
            ],
        ),
        migrations.CreateModel(
            name='User',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('username', models.CharField(max_length=30)),
                ('password', models.CharField(max_length=30)),
                ('gender', models.CharField(max_length=30)),
                ('pushKey', models.CharField(max_length=30)),
                ('avatar', models.FileField(upload_to=b'./avatar/')),
                ('birthday', models.CharField(max_length=30)),
            ],
        ),
    ]
