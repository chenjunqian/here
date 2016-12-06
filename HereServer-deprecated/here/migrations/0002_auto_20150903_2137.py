# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('here', '0001_initial'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='location',
            name='time',
        ),
        migrations.AddField(
            model_name='location',
            name='user',
            field=models.ForeignKey(to='here.User', null=True),
        ),
    ]
