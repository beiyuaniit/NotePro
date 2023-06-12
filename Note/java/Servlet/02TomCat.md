##下载
	Apache官网下载Core-Zip，直接解压即可
	放在了C:\Program Files\Java中
	
##apache-tomcat目录介绍
	bin：命令文件存放
		如启动TomCat，关闭TomCat
	conf：配置文件
		如server.xml中可以配置端口号，默认为8080
	lib：核心程序目录
		jar包中都是.class文件
	logs：日志目录
	temp：临时目录
	webapps：存放webapp
	work：存放jsp文件翻译后的Java文件以及编译后的class文件
##启动/关闭
	使用时可以不带.bat
	bin目录下的start.bat或者start.sh
		start.bat调用catalina.bat。catalina.bat执行Bootstrap.java的mian方法
	shutdown.bat和shutdown.sh
		由于shutdown是windows的关机命令，可以改为stop.bat
##访问
	http://127.0.0.1:8080
	localhost:8080
##编写webapp
	webapps目录下一个目录代表一个app
	超链接中的ip和端口号可以省略
		如："http://127.0.0.1:8080/oa/login.html"可以写为"/oa/login.html"