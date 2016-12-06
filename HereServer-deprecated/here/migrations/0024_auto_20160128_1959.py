# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('here', '0023_user_avatarthumb'),
    ]

    operations = [
        migrations.AlterField(
            model_name='post',
            name='like',
            field=models.IntegerField(default=0, blank=True),
        ),
    ]
