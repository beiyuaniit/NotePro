## 概念
​	数据库DataBase：存储数据的仓库，以文件形式存在
​	DBMS：MySql..
## 默认端口号
​	3306
## 命令行登录mysql
​	mysql -u root -p
​	Enter password:beiyuan3721
​	或者mysql -uroot -pbeiyuan3721
## 命令行退出mysql	
​	\c推出当前命令
​	\q或者exit推出mysql命令行
## 查看信息
​	status;//mysql相关信息
​	select version();//软件版本
## SQL执行顺序
​	select			5
​		...
​	from			1
​		...		
​	where			2
​		...	
​	group by		3
​		...
​	having			4
​		...
​	order by		6
​		...
​	limit			7
​		...;
## 去重
​	distinct只能放在所有字段前面，是对整条记录作用的
​	也可以查询数量
​		select count(distinct job) from emp;
## join...on
​	SQL99连接条件和where条件分离
​	三个表
​		....
​			A
​		join
​			B
​		join
​			C
​		on
​			...
​		

		表示：A表和B表先进行表连接，连接之后A表继续和C表进行连接。

## 对表取别名
​	简洁明了
​	用于自连接
## 查询结果集相加
​	union
​	集合的并(union all 是可有重复的记录)，2个集合的列要对应
​	select...union select...;
## 分页查询
​	limit startindex,length//下标从0开始
## 修改
​	Alter是对表结构的修改
​	Update是对数据的修改
## 表级约束
​	如果是多个字段的话，是联合一起作为一个整体，与列级约束不同
## 级联
​	更新：如：一个属性更新后，引用它的属性都更新
​	删除：删除一个后，与之有关的都删除
## 视图
​	可以修改基本表数据
## 2种锁
​	悲观锁：select ...for update;
​		锁住select查询的结果集，本事务结束前，其他事务不能进行修改
​	乐观锁
​		支持并发，事务也不需要排队，每次修改后追加一个数据版本号。
​		当一个事务准本发现与最初版本号不一致，回滚。
## 结束事务
​	提交和回滚都会结束事务

## 主键自增

- 额外添加自增id，而不用业务id


​	