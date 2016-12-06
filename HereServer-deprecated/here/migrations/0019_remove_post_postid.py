# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('here', '0018_post_postid'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='post',
            name='postid',
        ),
    ]
