# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('here', '0002_auto_20150903_2137'),
    ]

    operations = [
        migrations.CreateModel(
            name='TestModel',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('girl', models.CharField(max_length=30)),
                ('boy', models.CharField(max_length=30)),
                ('mom', models.CharField(max_length=30)),
                ('ball', models.CharField(max_length=30)),
            ],
        ),
    ]
