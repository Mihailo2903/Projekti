# Generated by Django 4.0.4 on 2022-06-01 16:18

import datetime
from django.conf import settings
import django.contrib.auth.models
import django.contrib.auth.validators
from django.db import migrations, models
import django.db.models.deletion
import django.utils.timezone


class Migration(migrations.Migration):

    initial = True

    dependencies = [
        ('auth', '0012_alter_user_first_name_max_length'),
    ]

    operations = [
        migrations.CreateModel(
            name='BelongsTo',
            fields=[
                ('id_belongs_to', models.AutoField(primary_key=True, serialize=False)),
            ],
            options={
                'db_table': 'belongs_to',
                'managed': False,
            },
        ),
        migrations.CreateModel(
            name='Category',
            fields=[
                ('id_category', models.AutoField(primary_key=True, serialize=False)),
                ('name', models.CharField(max_length=45, unique=True)),
            ],
            options={
                'db_table': 'category',
                'managed': True,
            },
        ),
        migrations.CreateModel(
            name='User',
            fields=[
                ('password', models.CharField(max_length=128, verbose_name='password')),
                ('last_login', models.DateTimeField(blank=True, null=True, verbose_name='last login')),
                ('is_superuser', models.BooleanField(default=False, help_text='Designates that this user has all permissions without explicitly assigning them.', verbose_name='superuser status')),
                ('username', models.CharField(error_messages={'unique': 'A user with that username already exists.'}, help_text='Required. 150 characters or fewer. Letters, digits and @/./+/-/_ only.', max_length=150, unique=True, validators=[django.contrib.auth.validators.UnicodeUsernameValidator()], verbose_name='username')),
                ('first_name', models.CharField(blank=True, max_length=150, verbose_name='first name')),
                ('last_name', models.CharField(blank=True, max_length=150, verbose_name='last name')),
                ('email', models.EmailField(blank=True, max_length=254, verbose_name='email address')),
                ('is_staff', models.BooleanField(default=False, help_text='Designates whether the user can log into this admin site.', verbose_name='staff status')),
                ('is_active', models.BooleanField(default=True, help_text='Designates whether this user should be treated as active. Unselect this instead of deleting accounts.', verbose_name='active')),
                ('date_joined', models.DateTimeField(default=django.utils.timezone.now, verbose_name='date joined')),
                ('id_user', models.AutoField(primary_key=True, serialize=False)),
                ('date_of_birth', models.DateField(default=datetime.datetime(2022, 6, 1, 18, 18, 33, 134201))),
                ('subscribed', models.CharField(max_length=1)),
                ('status', models.CharField(max_length=1)),
                ('type', models.CharField(max_length=1)),
                ('date_of_promotion', models.DateTimeField(default=datetime.datetime(2022, 6, 1, 18, 18, 33, 134200))),
                ('groups', models.ManyToManyField(blank=True, help_text='The groups this user belongs to. A user will get all permissions granted to each of their groups.', related_name='user_set', related_query_name='user', to='auth.group', verbose_name='groups')),
                ('user_permissions', models.ManyToManyField(blank=True, help_text='Specific permissions for this user.', related_name='user_set', related_query_name='user', to='auth.permission', verbose_name='user permissions')),
            ],
            options={
                'db_table': 'user',
                'managed': True,
            },
            managers=[
                ('objects', django.contrib.auth.models.UserManager()),
            ],
        ),
        migrations.CreateModel(
            name='Request',
            fields=[
                ('id_request', models.AutoField(primary_key=True, serialize=False)),
                ('status', models.CharField(max_length=1)),
                ('id_user', models.ForeignKey(db_column='id_user', on_delete=django.db.models.deletion.DO_NOTHING, related_name='user1', to=settings.AUTH_USER_MODEL)),
                ('id_user_reviewed', models.ForeignKey(blank=True, db_column='id_user_reviewed', null=True, on_delete=django.db.models.deletion.DO_NOTHING, related_name='approved', to=settings.AUTH_USER_MODEL)),
            ],
            options={
                'db_table': 'request',
                'managed': True,
            },
        ),
        migrations.CreateModel(
            name='Joke',
            fields=[
                ('id_joke', models.AutoField(primary_key=True, serialize=False)),
                ('title', models.CharField(max_length=45)),
                ('content', models.TextField()),
                ('status', models.CharField(max_length=1)),
                ('date_posted', models.DateTimeField(blank=True, null=True)),
                ('id_user_created', models.ForeignKey(db_column='id_user_created', on_delete=django.db.models.deletion.DO_NOTHING, related_name='created', to=settings.AUTH_USER_MODEL)),
                ('id_user_reviewed', models.ForeignKey(blank=True, db_column='id_user_reviewed', null=True, on_delete=django.db.models.deletion.DO_NOTHING, related_name='reviewed', to=settings.AUTH_USER_MODEL)),
            ],
            options={
                'db_table': 'joke',
                'managed': True,
            },
        ),
        migrations.CreateModel(
            name='Grade',
            fields=[
                ('id_grade', models.AutoField(primary_key=True, serialize=False)),
                ('grade', models.IntegerField()),
                ('date', models.DateTimeField(default=datetime.datetime(2022, 6, 1, 18, 18, 33, 136238))),
                ('was_reviewed', models.CharField(default='N', max_length=1)),
                ('id_joke', models.ForeignKey(db_column='id_joke', on_delete=django.db.models.deletion.DO_NOTHING, to='VicHubApp.joke')),
                ('id_user', models.ForeignKey(db_column='id_user', on_delete=django.db.models.deletion.DO_NOTHING, to=settings.AUTH_USER_MODEL)),
            ],
            options={
                'db_table': 'grade',
                'managed': True,
            },
        ),
        migrations.CreateModel(
            name='Comment',
            fields=[
                ('id_comment', models.AutoField(primary_key=True, serialize=False)),
                ('ordinal_number', models.IntegerField()),
                ('content', models.TextField()),
                ('status', models.CharField(max_length=1)),
                ('date_posted', models.DateTimeField(blank=True, null=True)),
                ('id_joke', models.ForeignKey(db_column='id_joke', on_delete=django.db.models.deletion.DO_NOTHING, to='VicHubApp.joke')),
                ('id_user', models.ForeignKey(db_column='id_user', on_delete=django.db.models.deletion.DO_NOTHING, to=settings.AUTH_USER_MODEL)),
            ],
            options={
                'db_table': 'comment',
                'managed': True,
            },
        ),
    ]
