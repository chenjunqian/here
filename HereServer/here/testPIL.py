#coding=utf-8
import Image

# 打开一个jpg图像文件，注意路径要改成你自己的:
im = Image.open('/home/projects/HereServer/media/avatar/LinuxTest_45.jpg')
# 获得图像尺寸:
w, h = im.size
# 缩放到50%:
im.thumbnail((w//2, h//2))
# 把缩放后的图像用jpeg格式保存:
im.save('/home/projects/HereServer/media/avatar/thumb/LinuxTest_45.jpg', 'jpeg')