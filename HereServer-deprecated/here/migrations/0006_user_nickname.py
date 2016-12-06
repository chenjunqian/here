# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('here', '0005_user_avatar'),
    ]

    operations = [
        migrations.AddField(
            model_name='user',
            name='nickname',
            field=models.CharField(max_length=30, blank=True),
        ),
    ]
