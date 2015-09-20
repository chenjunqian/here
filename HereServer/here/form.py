from django.forms import forms

class UserForm(forms.Form):
	avatar = forms.FileField()