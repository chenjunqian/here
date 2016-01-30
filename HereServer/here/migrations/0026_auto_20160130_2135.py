# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('here', '0025_auto_20160128_2006'),
    ]

    operations = [
        migrations.CreateModel(
            name='Report',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('content', models.CharField(max_length=100)),
                ('postid', models.IntegerField()),
                ('reporter', models.CharField(max_length=100)),
                ('time', models.CharField(max_length=100)),
            ],
        ),
        migrations.CreateModel(
            name='ReportIssue',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('content', models.CharField(max_length=100)),
                ('reporter', models.CharField(max_length=100)),
                ('time', models.CharField(max_length=100)),
            ],
        ),
        migrations.AddField(
            model_name='user',
            name='registertime',
            field=models.CharField(default=b'null', max_length=30, blank=True),
        ),
    ]
