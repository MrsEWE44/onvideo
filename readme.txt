一键部署脚本(deploy.sh)支持termux跟docker

这是一个基于Java springboot写的视频网站后端，前端是用vue2+elementui写的，没有admin部分，由于界面比较简单，就不放截图了。

打包后的jar包在bin文件夹里

Java的配置文件在bin文件夹的conf下面，可以修改为自己的数据库信息




用的技术有：

后端：springboot、mybatis-plus

前端：vue2、JavaScript、element ui、css、html5

数据库：mysql5




添加的自定义属性：

注解：

NeedPermission：用于判断当前用户是否拥有权限访问

isLogin：用户登录时进行核实

错误拦截：

MyErrorController： 拦截请求时出现的错误并返回对应错误页面

返回类型：

resultMSG：继承hashmap对象并加以封装

ResultStatus：枚举类，有常用的状态值与信息内容

vo部分：

对entity实体类的进一步拓展与封装




实现的功能有：

1、视频上传、修改、播放

2、用户注册、登录、验证

3、视频分类展示、切换

4、视频评论发送、删除

5、用户动态查看、删除

6、视频首页、分类、播放页面均有相关视频推荐

7、视频名称、介绍检索




运行jar包的方法：

Windows运行bin文件夹里面的runjava.bat脚本（需要安装jre）

Linux运行bin文件夹里面的runjava.sh脚本（需要安装jre）

如果不需要用脚本里的参数，可以自己修改



