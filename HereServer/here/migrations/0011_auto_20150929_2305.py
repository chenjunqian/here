# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('here', '0010_auto_20150929_2255'),
    ]

    operations = [
        migrations.RenameField(
            model_name='location',
            old_name='user',
            new_name='userid',
        ),
    ]
