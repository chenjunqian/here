# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('here', '0006_user_nickname'),
    ]

    operations = [
        migrations.AddField(
            model_name='location',
            name='shareContent',
            field=models.CharField(max_length=200, blank=True),
        ),
    ]
