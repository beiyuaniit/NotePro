# ==概述==

## 命令格式

- 命令名  [选项]  [参数]						# 选项是附加条件 		参数 是操作对象	
- 参数和选项顺序可颠倒

## 拷贝文件

- 文件可以从windows赋值粘贴进虚拟机的linux

## 合并选项

- 不冲突时可以写成一个

```shell
[beiyuani@Hadoop100 HomeWork]$ cat -sn Note.txt
     1	Today is Sunday
     2	
     3	i want to do something
[beiyuani@Hadoop100 HomeWork]$ cat -s -n Note.txt
     1	Today is Sunday
     2	
     3	i want to do something
```



# ==文件==

## cd

```shell
cd /home/beiyuani		#进入beiyuani用户的目录
```

## touch

- 创建文件

```shell
[beiyuani@Hadoop100 HomeWork]$ touch Note.txt
[beiyuani@Hadoop100 HomeWork]$ ls
Note.txt
```

## rm

- 删除文件

```shell
[beiyuani@Hadoop100 HomeWork]$ ls
a.txt  Note.txt
[beiyuani@Hadoop100 HomeWork]$ rm a.txt
[beiyuani@Hadoop100 HomeWork]$ ls
Note.txt
```

## cat

- 显式文件内容

```shell
[beiyuani@Hadoop100 HomeWork]$ cat Note.txt
Today is Sunday
i want to do something

选项
-b  显式非空行号。非空行忽略
-n 	显式所有行号
-s	把多个相邻空行合并为一个空行
```

## more

- 一个基于vi编辑器的文本过滤器，每次显式一个屏幕的内容

```shell
[beiyuani@Hadoop100 HomeWork]$ more a
-面向过程的优点：快速开发小程序

-类
	是引用数据类型
	包含属性和方法

-成员变量系统有默认值（一般是0类），局部变量没有默认值

-java没有指针，程序员不能直接操作堆区，只能可以通过引用（对象名）去访问新建的对
象
 引用的字面量是对象的地址，可以访问对象数据，可以指向新的对象。没其他功能了
 不能通过类名访问属性和方法
	
-JVM主要三内存区：
	方法区：方法代码，静态数据
	桟区：方法调用时在桟区分配空间
	堆区：对象

-一个线程有一个桟区，所有线程共用一个方法区和一个堆区

-GC垃圾回收机制主要针对堆区数据

-空指针（引用）异常
--更多--(10%)
```

- 选项

```shell
-num	一屏显式num行
-d		底部显式一些提示
-c或-p	不滚屏，显式下一屏时先清屏
-s		合并空行
+/		模式匹配，字符串查找
+num	从第num行开始
```

- 操作

```shell
Space		下一屏
Enter		下一行
/			模式查找
h或?			显式操作的帮助信息
b或^b		显式上一屏
q			退出more
```

## less

- 和more类似。功能比more更加强
- less可以用上下方向键移动一行，PageDown和PageUp翻页，而more不行

## head

- 显式开头若干行

```shell
[beiyuani@Hadoop100 HomeWork]$ head -3 a
-面向过程的优点：快速开发小程序

-类

选项
-c  num		前面num字符
-num		前面num行（默认行数是10
-n   num	前面num行
-q		不显示给定文件标题
-v		始终显式给定文件标题
```

## tail

- 从末尾开始显式，和head操作类似









# ==查找==

## grep

- 







# ==功能==

## date

- 显式日期

```shell
[beiyuani@Hadoop100 HomeWork]$ date			
2022年 03月 12日 星期六 23:54:15 CST
```

## clear

- 清屏

## ls

- 列出当前目录的文件

## who

- 显式用户名、终端名、注册到系统时间

```shell
[beiyuani@Hadoop100 /]$ who
beiyuani tty2         2022-03-12 23:38 (tty2)
```

## cal

- 显式日历

```shell
[beiyuani@Hadoop100 /]$ cal			#0参数，当前月份
      三月 2022     
日 一 二 三 四 五 六
       1  2  3  4  5
 6  7  8  9 10 11 12
13 14 15 16 17 18 19
20 21 22 23 24 25 26
27 28 29 30 31      
                    
[beiyuani@Hadoop100 /]$ cal 10		#一参数，公元x年                                     
[beiyuani@Hadoop100 /]$ cal 10 2049			#月和年
      十月 2049     
日 一 二 三 四 五 六
                1  2
 3  4  5  6  7  8  9
10 11 12 13 14 15 16
17 18 19 20 21 22 23
24 25 26 27 28 29 30
31
```

## passwd

- 修改用户的登录密码

## echo

- 打印到控制台

```shell
[beiyuani@Hadoop100 ~]$ echo 'This is a    command'			#带''就是保持原格式
This is a    command
[beiyuani@Hadoop100 ~]$ echo Happy New      Year			#不带''则字符串以空格分开
Happy New Year

```

# ==其他==

## 选项

- --help

```shell
#显式命令帮助。命令的用法、选项、参数等信息
cat --help
ls --help
```



