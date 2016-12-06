# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('here', '0009_auto_20150921_2232'),
    ]

    operations = [
        migrations.AlterField(
            model_name='location',
            name='user',
            field=models.CharField(default=b'null', max_length=30, blank=True),
        ),
    ]
