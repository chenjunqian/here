# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('here', '0020_post_time'),
    ]

    operations = [
        migrations.AddField(
            model_name='user',
            name='longProfile',
            field=models.CharField(max_length=300, blank=True),
        ),
        migrations.AddField(
            model_name='user',
            name='simpleProfile',
            field=models.CharField(max_length=30, blank=True),
        ),
    ]
