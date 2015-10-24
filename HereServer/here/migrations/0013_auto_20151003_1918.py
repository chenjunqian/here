# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('here', '0012_auto_20151003_1822'),
    ]

    operations = [
        migrations.RenameField(
            model_name='post',
            old_name='image',
            new_name='image_four',
        ),
        migrations.AddField(
            model_name='post',
            name='image_one',
            field=models.ImageField(default=b'null', upload_to=b'./post-image/', blank=True),
        ),
        migrations.AddField(
            model_name='post',
            name='image_three',
            field=models.ImageField(default=b'null', upload_to=b'./post-image/', blank=True),
        ),
        migrations.AddField(
            model_name='post',
            name='image_two',
            field=models.ImageField(default=b'null', upload_to=b'./post-image/', blank=True),
        ),
    ]
