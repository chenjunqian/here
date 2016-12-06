# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('here', '0017_posttag'),
    ]

    operations = [
        migrations.AddField(
            model_name='post',
            name='postid',
            field=models.CharField(max_length=200, blank=True),
        ),
    ]
