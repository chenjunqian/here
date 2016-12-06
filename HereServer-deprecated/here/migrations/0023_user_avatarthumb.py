# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('here', '0022_auto_20151222_2207'),
    ]

    operations = [
        migrations.AddField(
            model_name='user',
            name='avatarThumb',
            field=models.FileField(default=b'null', upload_to=b'./avatar/thumb', blank=True),
        ),
    ]
