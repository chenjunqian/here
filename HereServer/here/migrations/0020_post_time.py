# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('here', '0019_remove_post_postid'),
    ]

    operations = [
        migrations.AddField(
            model_name='post',
            name='time',
            field=models.CharField(default=b'null', max_length=30, blank=True),
        ),
    ]
