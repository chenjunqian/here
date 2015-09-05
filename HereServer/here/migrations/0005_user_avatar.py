# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('here', '0004_remove_user_avatar'),
    ]

    operations = [
        migrations.AddField(
            model_name='user',
            name='avatar',
            field=models.FileField(upload_to=b'./avatar/', blank=True),
        ),
    ]
