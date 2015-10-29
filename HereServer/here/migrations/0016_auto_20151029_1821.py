# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('here', '0015_auto_20151029_0022'),
    ]

    operations = [
        migrations.RenameField(
            model_name='post',
            old_name='shareContent',
            new_name='tag',
        ),
        migrations.RemoveField(
            model_name='post',
            name='image_four',
        ),
        migrations.RemoveField(
            model_name='post',
            name='image_one',
        ),
        migrations.RemoveField(
            model_name='post',
            name='image_three',
        ),
        migrations.RemoveField(
            model_name='post',
            name='image_two',
        ),
    ]
