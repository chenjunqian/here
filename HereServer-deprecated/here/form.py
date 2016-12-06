from django import forms

class UserForm(forms.Form):
	avatar = forms.FileField()
	username = forms.CharField(max_length=30)

class PostImageForm(forms.Form):
	image_one = forms.FileField()
	image_two = forms.FileField()
	image_three = forms.FileField()
	image_four = forms.FileField()