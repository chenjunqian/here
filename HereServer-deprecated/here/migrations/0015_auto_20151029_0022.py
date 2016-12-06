# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('here', '0014_auto_20151024_0052'),
    ]

    operations = [
        migrations.RenameField(
            model_name='post',
            old_name='userid',
            new_name='address',
        ),
        migrations.AddField(
            model_name='post',
            name='cityCode',
            field=models.CharField(default=b'null', max_length=30, blank=True),
        ),
        migrations.AddField(
            model_name='post',
            name='username',
            field=models.CharField(default=b'null', max_length=30, blank=True),
        ),
    ]
