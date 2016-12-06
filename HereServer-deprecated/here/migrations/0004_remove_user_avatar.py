# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('here', '0003_testmodel'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='user',
            name='avatar',
        ),
    ]
