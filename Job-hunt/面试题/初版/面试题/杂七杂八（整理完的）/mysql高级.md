# MySQL高级

# 性能与JOIN

## 性能下降原因

### 索引失效

**单值索引**

创建语句

```
CREATE INDEX idx_表名_字段名 ON 表名(字段名);Copy
```

**复合索引**

```
CREATE INDEX idx_表名_字段名1字段名2... ON 表名(字段名1, 字段名2 ...);Copy
```

### 关联太多JOIN

内连接、外连接的表不要过多

### 服务器调优及参数设置

## SQL执行加载顺序

### 手写顺序

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200814112248.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200814112248.png)

随着 Mysql 版本的更新换代，其优化器也在不断的升级，优化器会分析不同执行顺序产生的性能消耗不同而**动态调整执行顺序**

下面是经常出现的查询顺序：

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200814112330.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200814112330.png)

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200814112343.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200814112343.png)

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200814191644.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200814191644.png)

## 7种JOIN

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200814150926.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200814150926.png)

### **建表语句**

```
CREATE TABLE `t_dept` (
`id` INT(11) NOT NULL AUTO_INCREMENT, `deptName` VARCHAR(30) DEFAULT NULL, `address` VARCHAR(40) DEFAULT NULL, PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
CREATE TABLE `t_emp` (
`id` INT(11) NOT NULL AUTO_INCREMENT, `name` VARCHAR(20) DEFAULT NULL, `age` INT(3) DEFAULT NULL, `deptId` INT(11) DEFAULT NULL, empno INT NOT NULL, PRIMARY KEY (`id`), KEY `idx_dept_id` (`deptId`)
#CONSTRAINT `fk_dept_id` FOREIGN KEY (`deptId`) REFERENCES `t_dept` (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
INSERT INTO t_dept(deptName,address) VALUES('华山','华山');
INSERT INTO t_dept(deptName,address) VALUES('丐帮','洛阳');
INSERT INTO t_dept(deptName,address) VALUES('峨眉','峨眉山');
INSERT INTO t_dept(deptName,address) VALUES('武当','武当山');
INSERT INTO t_dept(deptName,address) VALUES('明教','光明顶');
INSERT INTO t_dept(deptName,address) VALUES('少林','少林寺');
INSERT INTO t_emp(NAME,age,deptId,empno) VALUES('风清扬',90,1,100001);
INSERT INTO t_emp(NAME,age,deptId,empno) VALUES('岳不群',50,1,100002);
INSERT INTO t_emp(NAME,age,deptId,empno) VALUES('令狐冲',24,1,100003);
INSERT INTO t_emp(NAME,age,deptId,empno) VALUES('洪七公',70,2,100004);
INSERT INTO t_emp(NAME,age,deptId,empno) VALUES('乔峰',35,2,100005);
INSERT INTO t_emp(NAME,age,deptId,empno) VALUES('灭绝师太',70,3,100006);
INSERT INTO t_emp(NAME,age,deptId,empno) VALUES('周芷若',20,3,100007);
INSERT INTO t_emp(NAME,age,deptId,empno) VALUES('张三丰',100,4,100008);
INSERT INTO t_emp(NAME,age,deptId,empno) VALUES('张无忌',25,5,100009);
INSERT INTO t_emp(NAME,age,deptId,empno) VALUES('韦小宝',18,NULL,100010);Copy
```

### JOIN查询

- 笛卡尔积

```
SELECT * FROM t_dept, t_emp;Copy
```

t_dept共20条记录，t_emp共6条记录。两表共同查询后共120条记录

- 内连接

```
SELECT * FROM t_emp a INNER JOIN t_dept b ON  a.deptId = b.id;Copy
```

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200814153140.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200814153140.png)

- 左外连接

```
SELECT * FROM t_emp a LEFT JOIN t_dept b ON  a.deptId = b.id;Copy
```

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200814153254.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200814153254.png)

- 右外连接

```
SELECT * FROM t_emp a RIGHT JOIN t_dept b ON  a.deptId = b.id;Copy
```

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200814153413.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200814153413.png)

- 左外连接**取左表的独有部分**

```
SELECT * FROM t_emp a LEFT JOIN t_dept b ON  a.deptId = b.id WHERE a.deptId IS NULL;Copy
```

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200814153909.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200814153909.png)

- 右外连接**取右表的独有部分**

```
SELECT * FROM t_emp a RIGHT JOIN t_dept b ON a.deptId = b.id WHERE a.deptId IS NULL;Copy
```

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200814153844.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200814153844.png)

**注意**：判断字段是否为NULL时，**不能使用’=’**

因为

```
= NULLCopy
```

的结果不会报错，但是**结果永远为false**。所以必须使用

```
IS NULLCopy
```

来进行判空

- 全外连接

MySQL不支持全外连接，要查询两个表的全集，需要合并两个查询结果，所以要使用 **UNION** 关键字

```
SELECT * FROM t_emp a LEFT JOIN t_dept b ON a.deptId = b.id
UNION
SELECT * FROM t_emp a RIGHT JOIN t_dept b ON a.deptId = b.id;Copy
```

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200814154554.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200814154554.png)

- 查询两表独有内容

```
SELECT * FROM t_emp a LEFT JOIN t_dept b ON a.deptId = b.id WHERE b.id IS NULL
UNION
SELECT * FROM t_emp a RIGHT JOIN t_dept b ON a.deptId = b.id WHERE a.deptId IS NULL;Copy
```

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200814155138.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200814155138.png)

# 索引优化

## 什么是索引

- MySQL 官方对索引的定义为：**索引（Index）是帮助 MySQL 高效获取数据的数据结构**。可以得到索引的本质： **索引是数据结构**。

  可以简单理解为：**排好序的快速查找数据结构**

- 在数据之外，数据库系统还维护着满足特定查找算法的数据结构，这些数据结构以某种方式引用（指向）数据， 这样就可以在这些数据结构上实现高级查找算法。这种数据结构，就是索引。下图就是一种可能的索引方式示例：

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200814173647.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200814173647.png)

- 左边是数据表，一共有两列七条记录，最左边的是数据记录的物理地址。为了加快 Col2 的查找，可以维护一个右边所示的二叉查找树，每个节点分别包含索引键值和一个指向对应数据记录物理地址的指针，这样就可以运用 二叉查找在一定的复杂度内获取到相应数据，从而快速的检索出符合条件的记录
- 一般来说索引本身也很大，不可能全部存储在内存中，因此索引往往以索引文件的形式存储的磁盘上

## 索引的优缺点

### 优点

- **提高数据检索的效率**，降低数据库的IO成本
- 通过索引列对数据进行排序，**降低数据排序的成本**，降低了CPU的消耗

### 缺点

- 虽然索引大大提高了查询速度，同时却**会降低更新表的速度**，如对表进行INSERT、UPDATE和DELETE。因为更新表时，MySQL不仅要保存数据，还要保存一下索引文件每次更新添加了索引列的字段，都会调整因为更新所带来的键值变化后的索引信息
- 实际上索引也是一张表，该表保存了主键与索引字段，并指向实体表的记录，所以**索引列也是要占用空间的**

## 索引的分类

### 基本语法

- 创建

  ```
  CREATE [UNIQUE] INDEX [indexName] ON table_name(column);Copy
  ```

- 删除

  ```
  DROP INDEX [indexName] ON table_name;Copy
  ```

- 查看

  ```
  SHOW INDEX FROM table_name;Copy
  ```

### 分类

- 单值索引

  - 定义：即一个索引只包含单个列，一个表可以有多个单列索引

  - 语法：

    ```
    --和表一起创建
    CREATE TABLE customer (
    id INT(10) UNSIGNED AUTO_INCREMENT,
    customer_no VARCHAR(200),
    customer_name VARCHAR(200), 
    PRIMARY KEY(id), 
    KEY (customer_name) --单值索引
    );
    
    --单独创建单值索引
    CREATE INDEX idx_customer_name ON customer(customer_name);Copy
    ```

- 唯一索引

  - 定义：索引列的值必须唯一，但允许有空值

  - 唯一约束？

  - 语法：

    ```
    --和表一起创建
    CREATE TABLE customer (
    id INT(10) UNSIGNED AUTO_INCREMENT,
    customer_no VARCHAR(200),
    customer_name VARCHAR(200), 
    PRIMARY KEY(id), 
    KEY (customer_name), --单值索引
    UNIQUE (customer_no) --唯一索引
    );
    
    --单独创建唯一索引
    CREATE UNIQUE INDEX idx_customer_no ON customer(customer_no);Copy
    ```

- 主键索引

  - 定义：设定为主键后数据库会**自动建立索引**，innodb为聚簇索引

  - 语法：

    ```
    --和表一起创建
    CREATE TABLE customer (
    id INT(10) UNSIGNED AUTO_INCREMENT,
    customer_no VARCHAR(200),
    customer_name VARCHAR(200), 
    PRIMARY KEY(id) --主键索引
    );
    
    --单独创建主键索引
    ALTER TABLE customer ADD PRIMARY KEY customer(customer_no);
    
    --删除主键索引
    ALTER TABLE customer DROP PRIMARY KEY;
    
    --修改建主键索引
    必须先删除掉(drop)原索引，再新建(add)索引Copy
    ```

- 复合索引

  - 定义：即一个索引包含多个列

  - 语法：

    ```
    --和表一起创建
    CREATE TABLE customer (
    id INT(10) UNSIGNED AUTO_INCREMENT,
    customer_no VARCHAR(200),
    customer_name VARCHAR(200), 
    PRIMARY KEY(id), 
    KEY (customer_name), --单值索引
    UNIQUE (customer_no), --唯一索引
    KEY (customer_no,customer_name) --复合索引
    );
    
    --单独创建复合索引
    CREATE INDEX idx_no_name ON customer(customer_no,customer_name);Copy
    ```

## MySQL的索引

### B树与B+树

树的内容参照[JAVA数据结构 B树、B+树和B*树](https://nyimac.gitee.io/2020/06/17/数据结构与算法/#B树、B-和B-树)

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200815153029.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200815153029.png)

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200815153043.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200815153043.png)

#### 区别

- B树的**关键字和记录是放在一起的**，叶子节点可以看作外部节点；B+树的非叶子节点中只有关键字和指向下一个节点的索引，**记录只放在叶子节点中**
- 在 B树中，越靠近根节点的记录查找时间越快，只要找到关键字即可确定记录的存在；而 B+树中每个记录 的查找时间基本是一样的，都需要从根节点走到叶子节点，而且在叶子节点中还要再比较关键字。从这个角度看 B树的性能好像要比 B+树好，而在实际应用中却是 B+树的性能要好些。因为 B+树的非叶子节点不存放实际的数据， 这样每个节点可容纳的指针个数比 B树多，树高比 B树小，这样带来的好处是减少磁盘访问次数。尽管 B+树找到 一个记录所需的比较次数要比 B树多，但是一次磁盘访问的时间相当于成百上千次内存比较的时间，因此实际中 B+树的性能可能还会好些，而且 B+树的叶子节点使用指针连接在一起，方便顺序遍历（例如查看一个目录下的所有 文件，一个表中的所有记录等），这也是很多数据库和文件系统使用 B+树的缘故

**为什么说 B+树比 B-树更适合实际应用中操作系统的文件索引和数据库索引？**

- B+树的磁盘读写代价更低
  - B+树的内部结点并没有指向关键字具体信息的指针。因此其内部结点相对 B 树更小。如果把所有同一内部结点 的关键字存放在同一盘块中，那么盘块所能容纳的关键字数量也越多。一次性读入内存中的需要查找的关键字也就越多。相对来说 IO 读写次数也就降低了
- B+树的查询效率更加稳定
  - 由于非终结点并不是最终指向文件内容的结点，而只是叶子结点中关键字的索引。所以任何关键字的查找必须走一条从根结点到叶子结点的路。所有关键字查询的路径长度相同，导致每一个数据的查询效率相当
  - 方便分析和管理

### MySQL中的B+树

#### 主键索引

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200822173605.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200822173605.png)

MySQL在创建表时，会根据主键来创建主键索引（如果没有主键，会用一个隐藏值来作为主键）。主键索引所构建的B+树，表中所有的记录都存放在了树的最后一层。**且与一般的B+树不同的是：叶子节点间的指针是双向的**

#### 复合索引

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200822185520.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200822185520.png)

创建复合索引时，会将作为**复合索引字段的值**进行排序并放在B+树的最后一层中，同时还会将其**对应的主键值**放在其后。如：

| a（主键） | b    | c    | d    | e    |
| --------- | ---- | ---- | ---- | ---- |
| 2         | 1    | 1    | 1    | a    |

其中字段a为主键，字段bcd共同作为复合索引，此时存放在最后一层的数据就是：111（复合索引） 2（主键索引）

根据这个特点，可以看出复合索引具有以下使用方法

- 最佳左前缀：使用复合索引的顺序必须和创建的**顺序一致**

- 覆盖索引的同时，可以带上主键字段，如

  ```
  SELECT a, b, c, d FROM t_emp;Copy
  ```

  因为**主键字段和复合索引一起存放在了复合索引说产生的B+树的最后一层**。如果需要a字段，无需进行全表扫描

- 如果进行范围查找，可能会进行全表扫描，这取决于处在范围内记录的多少

  - **记录多**，从复合索引映射到主键索引的次数过多，成本过高，**会直接进行全表扫描**

    ```
    EXPLAIN SELECT * FROM t_emp WHERE age > 1;Copy
    ```

    [![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200822175336.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200822175336.png)

  - **记录少**，先**使用复合索引**，然后映射到全表中的对应记录上

    ```
    EXPLAIN SELECT * FROM t_emp WHERE age > 80;Copy
    ```

    [![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200822175403.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200822175403.png)

  - 但是使用**覆盖索引**，无论记录多少，都会用到索引

    ```
    EXPLAIN SELECT age, name FROM t_emp WHERE age > 1;Copy
    ```

    [![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200822175611.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200822175611.png)

- 不带WHERE也可以通过复合索引查找到主键+复合索引的记录

  ```
  EXPLAIN SELECT id, age, name, deptId FROM t_emp ;Copy
  ```

  [![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200822175746.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200822175746.png)

## 索引的使用场景

### 适合索引的场景

- 主键自动建立唯一索引
- 频繁作为**查询条件**的字段应该创建索引
- 查询中与其它表关联的字段，**外键关系**建立索引
- 单键/组合索引的选择问题，**组合索引性价比更高**
- 查询中**排序的字段**，排序字段若通过索引去访问将大大提高排序速度
- 查询中**统计**或者**分组**字段

### 不适合索引的场景

- 表**记录太少**（有无索引差别不大）
- 经常**增删改**的表或者字段
- Where 条件里用不到的字段不创建索引
- **过滤性不好**的不适合建索引（重复性较高，比如国籍、性别之类的字段）

# Explain 性能分析

## 概念

使用 **EXPLAIN** 关键字可以模拟优化器执行 SQL 查询语句，从而知道 MySQL 是如何处理你的 SQL 语句的。**分析**你的查询语句或是表结构的**性能瓶颈**

## 用法

```
--EXPLAIN + SQL语句，如：
EXPLAIN SELECT * FROM person;Copy
```

**Explain 执行后返回的信息：**

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200815171636.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200815171636.png)

## 表头字段介绍

### 准备工作

```
CREATE TABLE t1(id INT(10) AUTO_INCREMENT,content VARCHAR(100) NULL , PRIMARY KEY (id));
CREATE TABLE t2(id INT(10) AUTO_INCREMENT,content VARCHAR(100) NULL , PRIMARY KEY (id));
CREATE TABLE t3(id INT(10) AUTO_INCREMENT,content VARCHAR(100) NULL , PRIMARY KEY (id));
CREATE TABLE t4(id INT(10) AUTO_INCREMENT,content VARCHAR(100) NULL , PRIMARY KEY (id));
INSERT INTO t1(content) VALUES(CONCAT('t1_',FLOOR(1+RAND()*1000)));
INSERT INTO t2(content) VALUES(CONCAT('t2_',FLOOR(1+RAND()*1000)));
INSERT INTO t3(content) VALUES(CONCAT('t3_',FLOOR(1+RAND()*1000)));
INSERT INTO t4(content) VALUES(CONCAT('t4_',FLOOR(1+RAND()*1000)));Copy
```

### id：表的读取顺序

id是select查询的序列号，包含一组数字，表示查询中执行select子句或操作表的顺序

- **id相同**：执行顺序为 **从上至下执行**

  ```
  EXPLAIN SELECT * FROM t1, t2, t3 WHERE t1.id = t2.id AND t2.id = t3.id;Copy
  ```

  [![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200815173157.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200815173157.png)

  查询时，表的加载**顺序为t1, t2, t3**

- **id不同**：执行顺序为 **id大的先执行**

  ```
  EXPLAIN SELECT t2.id FROM t2 WHERE t2.id = 
  (SELECT t1.id FROM t1 WHERE t1.id = 
  (SELECT t3.id FROM t3)
  );Copy
  ```

  [![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200815174216.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200815174216.png)

  查询时，表的加载**顺序为t3, t1, t2**

- **id相同又不同**： 执行顺序为

  - id不同时，值较大的先执行
  - id相同时，从上至下执行

  ```
  EXPLAIN SELECT * FROM (SELECT t3.id FROM t3) s1, t2 WHERE s1.id = t2.id;Copy
  ```

  [![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200815174740.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200815174740.png)

  查询时，表的**加载顺序为t3, t2, 虚表dervied2**

  - 其中dervied**2** 的 2，为 id = 2

### select_type：查询操作类型

select_type代表**查询的类型**，主要是用于区别**普通查询、联合查询、子查询等**的复杂查询

| select_type 属性     | 含义                                                         |
| -------------------- | ------------------------------------------------------------ |
| SIMPLE               | 简单的 select 查询,查询中**不包含子查询或者 UNION**          |
| PRIMARY              | 查询中若包含任何复杂的子部分，**最外层**查询则被标记为 Primary |
| DERIVED              | 在 FROM 列表中包含的子查询被标记为 DERIVED(衍生) MySQL 会递归执行这些子查询, 把结果放在临时表里 |
| SUBQUERY             | 在SELECT或WHERE列表中包含了**子查询**                        |
| DEPEDENT SUBQUERY    | 在SELECT或WHERE列表中包含了子查询,**子查询基于外层**         |
| UNCACHEABLE SUBQUERY | **无法使用缓存**的子查询                                     |
| UNION                | 若第二个SELECT出现在UNION之后，则被标记为UNION； 若UNION包含在FROM子句的子查询中,外层SELECT将被标记为：DERIVED |
| UNION RESULT         | 从UNION表**获取结果**的SELECT                                |

- SUBQUERY 和 DEPEDENT SUBQUERY
  
- 都是 WHERE 后面的条件，SUBQUERY 是单个值（=），DEPEDENT SUBQUERY 是一组值（IN）
  
- UNCACHEABLE SUBQUERY
  
- 当使用了**@@来引用系统变量**的时候，不会使用缓存
  
- UNION 和 UNION RESULT

  ```
  EXPLAIN SELECT * FROM t_emp a LEFT JOIN  t_dept b ON a.deptId = b.id WHERE b.id IS NULL
  UNION
  SELECT * FROM t_emp a RIGHT JOIN  t_dept b ON a.deptId = b.id WHERE a.deptId IS NULL;Copy
  ```

  [![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200816135453.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200816135453.png)

### table：表的来源

table表示这个数据是基于哪张表的

### type：访问类型

type 是查询的访问类型。**是较为重要的一个指标**，结果值从最好到最坏依次是：

```
system > const > eq_ref > ref > fulltext > ref_or_null > index_merge > unique_subquery > index_subquery > range > index > all

--常见的顺序为
system > const > eq_ref > ref > range > index > allCopy
```

一般来说，得保证查询**至少达到 range 级别**，最好能达到 ref

| 类型名 | 含义                                                         |
| ------ | ------------------------------------------------------------ |
| SYSTEM | 表只有一行记录（等于系统表），这是 const 类型的特列，平时不会出现，这个也**可以忽略不计** |
| CONST  | 表示**通过索引一次就找到了**,const 用于比较 primary key 或者 unique 索引。因为只匹配一行数据，所以很快。如将主键置于 where 列表中，MySQL 就能将该查询转换为一个常量 |
| EQ_REF | 唯一性索引扫描，对于每个索引键，**表中只有一条记录与之匹配**。常见于主键或唯一索引扫描 |
| REF    | 非唯一性索引扫描，返回匹配某个单独值的所有行。本质上也是一种索引访问，它返回所有匹配某个单独值的行， 然而，它**可能会找到多个符合条件的行**，所以他应该属于查找和扫描的混合体 |
| RANGE  | 只检索给定**范围**的行,使用一个索引来选择行。key 列显示使用了哪个索引一般就是在你的 where 语句中出现 了 between、<、>、in 等的查询这种范围扫描索引扫描比全表扫描要好，因为它只需要开始于索引的某一点，而 结束语另一点，不用扫描全部索引 |
| INDEX  | 出现index是sql使用了索引但是没用通过索引进行过滤，一般是使用了覆盖索引或者是利用索引进行了排序分组 |
| ALL    | Full Table Scan，将遍历全表以找到匹配的行                    |

- REF

  ```
  --其中deptId为索引，且用到了' = '
  EXPLAIN SELECT * FROM t_emp WHERE deptId = 3;Copy
  ```

  [![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200816165420.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200816165420.png)

- RANGE

  ```
  --其中deptId为索引，用到了 BETWEEN...AND... , IN , > , < 等范围查询
  EXPLAIN SELECT * FROM t_emp WHERE deptId > 3;Copy
  ```

  [![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200816213631.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200816213631.png)

- INDEX

  ```
  --其中deptId为索引，查找了整张表时，用到了索引
  EXPLAIN SELECT deptId FROM t_emp;Copy
  ```

  [![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200816165651.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200816165651.png)

- ALL

  ```
  --其中name为非索引
  EXPLAIN SELECT name FROM t_emp;Copy
  ```

  [![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200816165722.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200816165722.png)

### possible_key：可能用到的索引

显示**可能**应用在这张表中的索引，一个或多个。查询涉及到的字段上若存在索引，则该索引将被列出，但**不一 定被查询实际使用**

### key：实际使用的索引

**实际使用的索引**。如果为NULL，则没有使用索引

```
EXPLAIN SELECT * FROM t_emp WHERE id = 1 AND deptId = 1;Copy
```

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200816172950.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200816172950.png)

MySQL推测可能用到主键索引和idx_dept_id索引，实际上用到的是主键索引

#### **覆盖索引**

当查找的字段与建立的索引的匹配（查询的字段都是索引，但不需要全是索引）时，会发生覆盖索引。MySQL推测使用的索引为NULL，而实际上会使用索引

有以下两种解释

- select的数据列**只用从索引中就能够取得**，不必从数据表中读取，换句话说**查询列要被所使用的索引覆盖**
- 索引是高效找到行的一个方法，当能通过检索索引就可以读取想要的数据，那就不需要再到数据表中读取行了。如果一个索引包含了（或覆盖了）满足查询语句中字段与条件的数据就叫做覆盖索引

注意：要使用覆盖索引，则**只取出需要的列**（被令为索引），**不要**使用 SELECT *

```
--其中id和deptId都为索引
EXPLAIN SELECT id, deptId FROM t_emp;Copy
```

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200816173253.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200816173253.png)

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200816173228.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200816173228.png)

### key_len：索引使用字节数

表示索引中使用的字节数，可通过该列计算查询中使用的索引的长度。 key_len 字段能够帮你检查是否充分的利用上了索引

**ken_len 越长，说明索引使用的越充分**

### ref：显示被使用的索引的具体信息

ref显示索引的哪一列被使用了，如果可能的话，可以是一个常数。哪些列或常量被用于查找索引列上的值

```
EXPLAIN SELECT * FROM t_dept, t_emp WHERE t_emp.deptId = t_dept.id;Copy
```

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200816194305.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200816194305.png)

### rows：被查询的行数

rows 列显示 MySQL 认为它执行查询时必须检查的行数。**越少越好！**

**验证**

```
--先删除索引
DROP INDEX idx_dept_id ON t_emp;

--查找
EXPLAIN SELECT * FROM t_dept, t_emp WHERE t_emp.deptId = t_dept.id;

--再创建索引
CREATE INDEX idx_dept_id ON t_emp(deptId);

--查找
EXPLAIN SELECT * FROM t_dept, t_emp WHERE t_emp.deptId = t_dept.id;Copy
```

**结果如下**

- 未使用索引时，一共需要查询26行

  [![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200816195241.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200816195241.png)

- 使用索引后，一共需要查询6行

  [![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200816195401.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200816195401.png)

### Extra：额外重要信息

其他的额外**重要**的信息

- **Using filesort**：使用外部索引排序（未使用用户创建的索引）

  - 说明 mysql 会对数据使用一个外部的索引排序，而不是按照表内的索引顺序进行读取。MySQL 中无法利用索引 完成的排序操作称为“文件排序”
  - 出现 Using filesort **说明SQL语句设计的不好**，**没有按照创建的索引进行排序**，或者**未按照索引指定的顺序进行排序**

  **演示**

  ```
  --创建符合索引
  CREATE INDEX idx_emp_empno_age ON t_emp(empno, age);
  
  --进行查询操作，通过 age 字段进行排序（未按照复合索引顺序进行排序查询）
  EXPLAIN SELECT empno FROM t_emp WHERE empno >100002 ORDER BY age;
  
  --进行查询操作，通过 empno 或者 empno + age 字段进行排序（按照复合索引顺序进行排序查询）
  EXPLAIN SELECT empno FROM t_emp WHERE empno >100002 ORDER BY empno;
  EXPLAIN SELECT empno FROM t_emp WHERE empno >100002 ORDER BY empno, age;Copy
  ```

  **结果**

  [![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200816205145.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200816205145.png)

  [![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200816205226.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200816205226.png)

  [![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200816205112.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200816205112.png)

- **Using temporary**

  - 使了用临时表保存中间结果,MySQL 在对查询结果排序时使用临时表。**常见于排序 order by 和分组查询 group by**
  - 出现 Using temporary **说明SQL语句设计的非常不好**，可能是因为没有按照顺序使用复合索引

  **演示**

  ```
  --进行查询操作， 通过 age 字段进行分组（未按照复合索引顺序进行排序查询）
  CREATE INDEX idx_emp_empno_age ON t_emp(empno, age);
  
  ----进行查询操作，通过 empno 或者 empno + age 字段进行分组（按照复合索引顺序进行排序查询）
  EXPLAIN SELECT empno FROM t_emp WHERE empno >100002 GROUP BY empno;
  EXPLAIN SELECT empno FROM t_emp WHERE empno >100002 GROUP BY empno, age;Copy
  ```

  **结果**

  [![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200816210843.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200816210843.png)

  [![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200816210908.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200816210908.png)

**重要结论**

如果创建了**复合索引**，一定要**按照复合索引的顺序来使用**，否则会使得性能大幅下降

- **Using index**

  - Using index 代表表示相应的 select 操作中使用了**覆盖索引**(Covering Index)，详见[key：实际用到的索引——覆盖索引](https://nyimac.gitee.io/2020/08/16/MySQL高级/#覆盖索引)，避免访问了表的数据行，**效率不错**！
  - 如果同时出现 using where，表明**索引被用来执行索引键值的查找**
  - 如果没有同时出现 using where，表明**索引只是用来读取数据**而非利用索引执行查找。

  **演示**

  ```
  --查询 age 字段，使用了WHERE
  EXPLAIN SELECT age FROM t_emp WHERE age >100000;
  
  --查询 empno 和 age 字段，未使用WHERE
  EXPLAIN SELECT empno, age FROM t_emp;
  
  --查询 empno 和 name 字段 （name字段不是索引）
  EXPLAIN SELECT empno, name FROM t_emp;Copy
  ```

  **结果**

  [![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200816212055.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200816212055.png)

  [![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200816212129.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200816212129.png)

  [![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200816212243.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200816212243.png)

- Using where
  
  - 表明使用了 where 过滤
- Using join buffer
  
  - 使用了连接缓存
- impossible where
  
  - where 子句的值总是 false，不能用来获取任何元组
- select tables optimized away
  
  - 在没有 GROUP BY 子句的情况下，基于索引优化 MIN/MAX 操作或者对于 MyISAM 存储引擎优化 COUNT(*)操 作，不必等到执行阶段再进行计算，查询执行计划生成的阶段即完成优化

# 单表查询优化

## 复合索引

**SQL语句**

```
--建立复合索引（age, deptId, name）
CREATE INDEX idx_emp_ade ON t_emp(age, deptId, NAME);

--查找
EXPLAIN SELECT empno FROM t_emp WHERE age = 90;
EXPLAIN SELECT empno FROM t_emp WHERE age = 90 AND deptId = 1;
EXPLAIN SELECT empno FROM t_emp WHERE age = 90 AND deptId = 1 AND name = '风清扬';

--和上一条SQL语句中WHERE后字段的顺序不同，但是不影响查询结果
EXPLAIN SELECT empno FROM t_emp WHERE deptId = 1 AND name = '风清扬' AND age = 90;Copy
```

**对应结果**

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817164200.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817164200.png)

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817164226.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817164226.png)

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817164241.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817164241.png)

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817164506.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817164506.png)

可以看到，**复合索引都被用到了，并且SQL中查询字段的顺序，跟使用索引中字段的顺序，没有关系**。优化器会在不影响 SQL 执行结果的前提下，自动地优化

**查询的字段按照顺序在索引中都可以匹配到**

## 最佳左前缀法则

**SQL语句**

```
--先删除之前创建的单值索引
DROP INDEX idx_dept_id ON t_emp; 

--查询，未按照最佳左前缀法则
EXPLAIN SELECT empno FROM t_emp WHERE deptId = 1;
EXPLAIN SELECT empno FROM t_emp WHERE deptId = 1 AND name = '风清扬';

--查询，部分按照最佳左前缀法则（age字段和复合索引匹配，但name没有）
EXPLAIN SELECT empno FROM t_emp WHERE  age = 90 AND name = '风清扬';

--查询，完全按照最佳左前缀法则
EXPLAIN SELECT empno FROM t_emp WHERE age = 90 AND deptId = 1;
EXPLAIN SELECT empno FROM t_emp WHERE age = 90 AND deptId = 1 AND name = '风清扬';Copy
```

**对应结果**

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817164932.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817164932.png)

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817164948.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817164948.png)

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817165100.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817165100.png)

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817164226.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817164226.png)

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817164506.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817164506.png)

可以看到，查询**字段与索引字段顺序的不同会导致，索引无法充分使用，甚至索引失效**

**原因**：使用复合索引，需要**遵循最佳左前缀法则**，即如果索引了多列，要遵守最左前缀法则。指的是查询从索引的**最左前列开始并且不跳过索引中的列**

**结论：过滤条件要使用索引必须按照索引建立时的顺序，依次满足，一旦跳过某个字段，索引后面的字段都无法被使用**

## 索引列上不计算

不在索引列上做任何操作（计算、函数、(自动 or 手动)类型转换），**可能会导致索引失效而转向全表扫描**

**SQL语句**

```
--直接查询
EXPLAIN SELECT empno FROM t_emp WHERE age = 90 AND deptId = 1 AND NAME = '风清扬';

--使用MySQL函数查询
EXPLAIN SELECT empno FROM t_emp WHERE LEFT(age,2) = 90 AND deptId = 1 AND name = '风清扬';Copy
```

**对应结果**

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817170139.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817170139.png)

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817170522.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817170522.png)

可以看出，当age字段使用了left函数以后，**导致索引完全失效**

函数要先计算所有的列？然后跟右边比较？

**结论：等号左边无计算**

## 范围之后全失效

**SQL语句**

```
--范围查询
EXPLAIN SELECT empno FROM t_emp WHERE age > 50 AND deptId = 1 AND name = '风清扬';
EXPLAIN SELECT empno FROM t_emp WHERE age = 50 AND deptId > 1 AND NAME = '风清扬';

--未使用范围查询
EXPLAIN SELECT empno FROM t_emp WHERE age = 50 AND deptId = 1 AND name = '风清扬';Copy
```

**对应结果**

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817171833.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817171833.png)

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817172159.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817172159.png)

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817171903.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817171903.png)

可以看出，当对age字段使用范围查询后，使得范围后面的索引失效了

**建议：**将可能做范围查询的字段的索引顺序**放在最后**

**结论：使用范围查询后，如果范围内的记录过多，会导致索引失效**，因为从自定义索引映射到主键索引需要耗费太多的时间，反而不如全表扫描来得快

## 覆盖索引多使用

**SQL语句**

```
--查询所有字段
EXPLAIN SELECT * FROM t_dept WHERE id = 1;

--查询索引字段
EXPLAIN SELECT id FROM t_dept WHERE id = 1;Copy
```

**对应结果**

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817173338.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817173338.png)

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817173314.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817173314.png)

**结论：使用覆盖索引（Using index）会提高检索效率**

## 使用不等会失效

在使用**不等于(!= 或者<>)时**，有时会无法使用索引会导致全表扫描

**SQL语句**

```
--SQL语句中有不等于
EXPLAIN SELECT * FROM t_emp WHERE age != 90;
EXPLAIN SELECT * FROM t_emp WHERE age <> 90;

--SQL语句中没有不等于
EXPLAIN SELECT * FROM t_emp WHERE age = 90;Copy
```

**对应结果**

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817180448.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817180448.png)

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817180505.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817180505.png)

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817180521.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817180521.png)

**结论：尽量不要使用不等于**

## 使用NULL值要小心

在使用

```
IS NULL
或者
IS NOT NULLCopy
```

时，可能会导致索引失效

但是如果**允许字段为空**，则

- IS NULL 不会导致索引失效
- IS NOT NULL 会导致索引失效

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817181044.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817181044.png)

**SQL语句**

```
EXPLAIN SELECT * FROM t_emp WHERE age IS NULL;

EXPLAIN SELECT * FROM t_emp WHERE age IS NOT NULL;Copy
```

**对应结果**

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817181116.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817181116.png)

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817181137.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817181137.png)

## 模糊查询加右边

要使用模糊查询时，**百分号最好加在右边，而且进行模糊查询的字段必须是单值索引**

**SQL语句**

```
--创建单值索引
CREATE INDEX idx_emp_name ON t_emp(NAME);

--进行模糊查询
EXPLAIN SELECT * FROM t_emp WHERE name LIKE '%风';
EXPLAIN SELECT * FROM t_emp WHERE name LIKE '风%';
EXPLAIN SELECT * FROM t_emp WHERE name LIKE '%风%';Copy
```

**对应结果**

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817183416.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817183416.png)

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817183401.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817183401.png)

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817183416.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817183416.png)

可以看出，对索引使用模糊查询时，**只有当百分号在右边，索引为单值索引且模糊查询语句在最右边时，索引才会生效**

其他情况均失效了

**但是**有时必须使用其他类型的模糊查询，这时就需要用**覆盖索引**来解决索引失效的问题

**SQL语句**

```
EXPLAIN SELECT name FROM t_emp WHERE name LIKE '%风';
EXPLAIN SELECT name FROM t_emp WHERE name LIKE '风%';

EXPLAIN SELECT NAME FROM t_emp WHERE name LIKE '%风%';Copy
```

**对应结果**

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817183741.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817183741.png)

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817183801.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817183801.png)

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817183741.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817183741.png)

**结论：对索引进行模糊查询时，最好在右边加百分号。必须在左边或左右加百分号时，需要用到覆盖索引来提升查询效率**

## 字符串加单引号

当字段为字符串时，查询时必须带上单引号。否则**会发生自动的类型转换**，从而发生全表扫描

**用于查询的表**

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817203952.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817203952.png)

**其中card_id字段为varchar类型，且设置了单值索引**

**SQL语句**

```
--使用了单引号
EXPLAIN SELECT card_id FROM person WHERE card_id = '1';

--未使用单引号，发生自动类型转换
EXPLAIN SELECT card_id FROM person WHERE card_id = 1;Copy
```

**对应结果**

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817204047.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817204047.png)

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817204027.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817204027.png)

## 尽量不用or查询

如果使用or，可能导致索引失效。所以要减少or的使用，可以**使用 union all 或者 union 来替代：**

**SQL语句**

```
--使用or进行查询
EXPLAIN SELECT * FROM t_emp WHERE age = 90 OR NAME = '风清扬';Copy
```

**对应结果**

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817204307.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200817204307.png)

## 口诀

全职匹配我最爱，最左前缀要遵守

带头大哥不能死，中间兄弟不能断

索引列上少计算，范围之后全失效

LIKE 百分写最右，覆盖索引不写*

不等空值还有 OR，索引影响要注意

VARCHAR 引号不可丢，SQL 优化有诀窍

# 关联查询优化

**建表语句**

```
CREATE TABLE IF NOT EXISTS `class` (
`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, `card` INT(10) UNSIGNED NOT NULL, PRIMARY KEY (`id`)
);
CREATE TABLE IF NOT EXISTS `book` (
`bookid` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, `card` INT(10) UNSIGNED NOT NULL, PRIMARY KEY (`bookid`)
);
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));Copy
```

## LEFT JOIN优化

**SQL语句**

```
--未建立索引时的左外连接查询
EXPLAIN SELECT * FROM class LEFT JOIN book ON class.card = book.card;

--左表（class）建立索引
CREATE INDEX idx_class_card ON class(card);

--再次执行查询
EXPLAIN SELECT * FROM class LEFT JOIN book ON class.card = book.card;

--去掉左表索引
DROP INDEX idx_class_card ON class;

--右表建立索引
CREATE INDEX idx_book_card ON book(card);

--再次执行查询
EXPLAIN SELECT * FROM class LEFT JOIN book ON class.card = book.card;Copy
```

**对应结果**

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200818170458.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200818170458.png)

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200818170402.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200818170402.png)

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200818170547.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200818170547.png)

**结论**

- 在优化关联查询时，只有在**被驱动表上建立索引才有效**
- left join 时，左侧的为驱动表，**右侧为被驱动表**

## INNER JOIN优化

**SQL语句**

```
--查询操作，目前索引在book表的card上，class表和book表的位置不会改变查询结果
EXPLAIN SELECT * FROM class INNER JOIN book ON class.card = book.card;
EXPLAIN SELECT * FROM book INNER JOIN class ON book.card = class.card;

--删除book表中的几条记录
DELETE FROM book WHERE bookid<10;

--再次查询
EXPLAIN SELECT * FROM class INNER JOIN book ON class.card = book.card;

--删除book表card字段索引，给class表的card字段添加索引
DROP INDEX idx_book_card ON book;
CREATE INDEX idx_class_card ON class(card);

--再次查询
EXPLAIN SELECT * FROM class INNER JOIN book ON class.card = book.card;Copy
```

**对应结果**

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200818171341.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200818171341.png)

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200818171538.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200818171538.png)

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200818171625.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200818171625.png)

**结论**：inner join 时，**mysql 会把小结果集的表选为驱动表**（小表驱动大表）

**所以最好把索引建立在大表（数据较多的表）上**

## RIGHT JOIN优化

优化类型和LEFT JOIN类似，只不过**被驱动表变成了左表**

# 排序分组优化

在查询中难免会对查询结果进行排序操作。进行排序操作时要**避免出现 Using filesort**，应使用索引给排序带来的方便

**索引信息**

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200819160428.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200819160428.png)

## ORDER BY 优化

以下查询都是在**索引覆盖**的条件下进行的

**SQL语句**

```
--不满足索引覆盖时进行排序查询
EXPLAIN SELECT empno FROM t_emp  WHERE age > 50 ORDER BY age, deptId;

--按照复合索引顺序进行排序
EXPLAIN SELECT age, deptId FROM t_emp  WHERE age > 50 ORDER BY age;
EXPLAIN SELECT age, deptId FROM t_emp  WHERE age > 50 ORDER BY age, deptId;
EXPLAIN SELECT age, deptId FROM t_emp  WHERE age > 50 ORDER BY age, deptId, name;

--不按照复合索引顺序进行排序（无 age 字段），发生Using filesort
EXPLAIN SELECT age, deptId FROM t_emp  WHERE age > 50 ORDER BY deptId, name;

--不按照复合索引顺序进行排序（索引顺序打乱），发生Using filesort
EXPLAIN SELECT age, deptId FROM t_emp  WHERE age > 50 ORDER BY deptId, name, age;

--排序时部分(age)升序，部分(deptId)降序，发生Using filesort
EXPLAIN SELECT age, deptId FROM t_emp  WHERE age > 50 ORDER BY age ASC, deptId DESC;

--排序时都为降序
EXPLAIN SELECT age, deptId FROM t_emp  WHERE age > 50 ORDER BY age DESC, deptId DESC;

--排序时，在前面的字段为常量时（非范围）
EXPLAIN SELECT age, deptId FROM t_emp  WHERE age = 50 ORDER BY deptId, name;
EXPLAIN SELECT age, deptId FROM t_emp  WHERE age = 50 AND deptId>10000 ORDER BY deptId, name;Copy
```

**对应结果**

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200819162506.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200819162506.png)

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200819162240.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200819162240.png)

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200819162240.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200819162240.png)

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200819162240.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200819162240.png)

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200819162314.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200819162314.png)

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200907210532.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200907210532.png)

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200819162429.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200819162429.png)

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200819162901.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200819162901.png)

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200819164020.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200819164020.png)

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200819164317.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200819164317.png)

**结论**：

要想在排序时使用索引，避免 Using filesort，首先需要发生**索引覆盖**，其次

- ORDER BY 后面字段的顺序要和复合索引的**顺序完全一致**
- ORDER BY 后面的索引必须按照顺序出现，**排在后面的可以不出现**
- 要进行升序或者降序时，**字段的排序顺序必须一致**。不能一部分升序，一部分降序，可以都升序或者都降序
- 如果复合索引前面的**字段作为常量**出现在过滤条件中，**排序字段可以为紧跟其后的字段**

### MySQL的排序算法

当发生 Using filesort 时，MySQL会根据自己的算法对查询结果进行排序

- 双路排序
  - MySQL 4.1 之前是使用双路排序,字面意思就是**两次扫描磁盘**，最终得到数据，读取行指针和 order by 列，对他们进行排序，然后扫描已经排序好的列表，按照列表中的值重新从列表中读取对应的数据输出
  - 从磁盘取排序字段，在 buffer 进行排序，再从磁盘取其他字段
  - 简单来说，**取一批数据，要对磁盘进行了两次扫描**，众所周知，IO 是很耗时的，所以在 mysql4.1 之后，出现了第二种改进的算法，就是单路排序
- 单路排序
  - 从磁盘读取查询需要的所有列，按照 order by 列**在 buffer 对它们进行排序**，然后扫描排序后的列表进行输出， 它的效率更快一些，避免了第二次读取数据。并且把随机 IO 变成了顺序 IO,但是它会使用更多的空间， 因为它把每一行都保存在内存中了
  - **存在的问题**：在 sort_buffer 中，方法 B 比方法 A 要多占用很多空间，因为方法 B 是把所有字段都取出, 所以有可能**取出的数据的总大小超出了 sort_buffer 的容量**，导致每次只能取 sort_buffer 容量大小的数据，进行排序（创建 tmp 文件，多 路合并），排完再取取 sort_buffer 容量大小，再排……从而多次 I/O。也就是**本来想省一次 I/O 操作，反而导致了大量的 I/O 操作，反而得不偿失**
- 优化Using filesort
  - 增大 sort_butter_size 参数的设置
    - 不管用哪种算法，提高这个参数都会提高效率，当然，要根据系统的能力去提高，因为这个参数是针对**每个进程的 1M-8M 之间调整**
  - 增大 max_length_for_sort_data 参数的设置
    - mysql 使用单路排序的前提是**排序的字段大小要小于 max_length_for_sort_data**
    - 提高这个参数，会增加用改进算法的概率。但是如果设的太高，数据总容量超出 sort_buffer_size 的概率就增大， 明显症状是高的磁盘 I/O 活动和低的处理器使用率。（1024-8192 之间调整）
  - 减少 select 后面的查询的字段
    - 查询的字段减少了，缓冲里就能容纳更多的内容了，**间接增大了sort_buffer_size**

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200819164341.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200819164341.png)

## GROUP BY 优化

优化方式和 ORDER BY 类似，参考ORDER BY 的优化方式即可

# 截取查询分析

## 慢日志查询

### 概念

- MySQL的慢查询日志是MySQL提供的一种日志记录，**它用来记录在MySQL中响应时间超过阀值的语句**，具体指运行时间超过**long_query_time**值的SQL，则会被记录到慢查询日志中
- 具体指运行时间超过long_query_time值的SQL，则会被记录到慢查询日志中。long_query_time的默认值为 10，意思是运行10秒以上的语句
- 由他来查看哪些SQL超出了我们的最大忍耐时间值，比如一条sql执行超过5秒钟，我们就算慢SQL，希望能 收集超过5秒的sql，结合之前explain进行全面分析

### 使用

默认情况下，MySQL 数据库没有开启慢查询日志，需要我们**手动**来设置这个参数

如果不是调优需要的话，**一般不建议启动该参数**，因为开启慢查询日志会或多或少带来一定的性能影响。 慢查询日志支持将日志记录写入文件

| SQL 语句                               | 描述                   | 备注                                 |
| -------------------------------------- | ---------------------- | ------------------------------------ |
| SHOW VARIABLES LIKE ‘%slow_query_log%’ | 查看慢查询日志是否开启 | 默认情况下 slow_query_log 的值为 OFF |
| set global slow_query_log=1            | 开启慢查询日志         |                                      |
| SHOW VARIABLES LIKE ‘long_query_time%’ | 查看慢查询设定阈值     | 单位：秒                             |
| set long_query_time=1                  | 设定慢查询阈值         | 单位：秒                             |

运行查询时间长的 sql，**可以打开慢查询日志查看**

## 批量数据脚本

### 建表语句

```
--dept 部门表
CREATE TABLE `dept` (
`id` INT(11) NOT NULL AUTO_INCREMENT, `deptName` VARCHAR(30) DEFAULT NULL, `address` VARCHAR(40) DEFAULT NULL, ceo INT NULL , PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- emp 员工表
CREATE TABLE `emp` (
`id` INT(11) NOT NULL AUTO_INCREMENT, `empno` INT NOT NULL , `name` VARCHAR(20) DEFAULT NULL, `age` INT(3) DEFAULT NULL, `deptId` INT(11) DEFAULT NULL, PRIMARY KEY (`id`)
#CONSTRAINT `fk_dept_id` FOREIGN KEY (`deptId`) REFERENCES `t_dept` (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;Copy
```

### 设置参数

在执行创建函数之前，首先请保证 log_bin_trust_function_creators 参数为 1，即 on 开启状态。 否则会报错

```
--查询
SHOW VARIABLES LIKE 'log_bin_trust_function_creators';

--设置
SET GLOBAL log_bin_trust_function_creators=1;Copy
```

### 编写随机函数

#### **随机产生字符串**

```
--DELIMITER 是用于改变结束的标志的，一般以分号结尾，但这里改为了以 $$ 结尾
DELIMITER $$
CREATE FUNCTION rand_string(n INT) RETURNS VARCHAR(255)
BEGIN
DECLARE chars_str VARCHAR(100) DEFAULT 'abcdefghijklmnopqrstuvwxyzABCDEFJHIJKLMNOPQRSTUVWXYZ';
DECLARE return_str VARCHAR(255) DEFAULT '';
DECLARE i INT DEFAULT 0;
WHILE i < n DO
SET return_str =CONCAT(return_str,SUBSTRING(chars_str,FLOOR(1+RAND()*52),1));
SET i = i + 1;
END WHILE;
RETURN return_str;
END $$Copy
```

如果要**删除函数**，则执行：

```
DROP FUNCTION rand_string;Copy
```

#### 随机产生部门编号

```
DELIMITER $$
CREATE FUNCTION rand_num (from_num INT ,to_num INT) RETURNS INT(11)
BEGIN
DECLARE i INT DEFAULT 0;
SET i = FLOOR(from_num +RAND()*(to_num -from_num+1)) ;
RETURN i;
END$$Copy
```

如果要**删除函数**，则执行：

```
drop function rand_num;Copy
```

### 创建存储过程

#### 创建往 emp 表中插入数据的存储过程

```
DELIMITER $$
CREATE PROCEDURE insert_emp( START INT , max_num INT )
BEGIN
DECLARE i INT DEFAULT 0;
#set autocommit =0 把 autocommit 设置成 0
SET autocommit = 0;
REPEAT
SET i = i + 1;
INSERT INTO emp (empno, NAME ,age ,deptid ) VALUES ((START+i) ,rand_string(6) , rand_num(30,50),rand_num(1,10000));
UNTIL i = max_num
END REPEAT;
COMMIT;
END$$

--删除
-- DELIMITER ;
-- drop PROCEDURE insert_emp;Copy
```

#### 创建往 dept 表中插入数据的存储过程

```
--执行存储过程，往 dept 表添加随机数据
DELIMITER $$
CREATE PROCEDURE `insert_dept`( max_num INT )
BEGIN
DECLARE i INT DEFAULT 0;
SET autocommit = 0;
REPEAT
SET i = i + 1;
INSERT INTO dept ( deptname,address,ceo ) VALUES (rand_string(8),rand_string(10),rand_num(1,500000));
UNTIL i = max_num
END REPEAT;
COMMIT;
END$$

--删除
-- DELIMITER ;
-- drop PROCEDURE insert_dept;Copy
```

### 调用存储过程

#### 添加数据到部门表

```
--执行存储过程，往 dept 表添加 1 万条数据
DELIMITER ;
CALL insert_dept(10000);Copy
```

#### 添加数据到员工表

```
--执行存储过程，往 emp 表添加 50 万条数据
DELIMITER ;
CALL insert_emp(100000,500000);Copy
```

### 批量删除某个表上的所有索引

#### 删除索引的存储过程

```
DELIMITER $$
CREATE PROCEDURE `proc_drop_index`(dbname VARCHAR(200),tablename VARCHAR(200))
BEGIN
DECLARE done INT DEFAULT 0;
DECLARE ct INT DEFAULT 0;
DECLARE _index VARCHAR(200) DEFAULT '';
DECLARE _cur CURSOR FOR SELECT index_name FROM information_schema.STATISTICS WHERE
table_schema=dbname AND table_name=tablename AND seq_in_index=1 AND index_name <>'PRIMARY' ;
DECLARE CONTINUE HANDLER FOR NOT FOUND set done=2 ;
OPEN _cur;
FETCH _cur INTO _index;
WHILE _index<>'' DO
SET @str = CONCAT("drop index ",_index," on ",tablename );
PREPARE sql_str FROM @str ;
EXECUTE sql_str;
DEALLOCATE PREPARE sql_str;
SET _index='';
FETCH _cur INTO _index;
END WHILE;
CLOSE _cur;
END$$Copy
```

#### 执行存储过程

```
CALL proc_drop_index("dbname","tablename");Copy
```

# MySQL锁机制

## 表锁

**MylSAM引擎使用表锁，并且不支持事务**

**SQL语句**

```
--展示表是否加锁
SHOW OPEN TABLES;

--加锁 read (读锁) write (写锁)
LOCK TABLE table1 read(write), table2 read(write)...

--全部解锁
UNLOCK TABLES;Copy
```

### 读锁

- 主机A给表加上**表锁（读锁）**以后
  - 主机A和其他主机都可以读取**该表**的信息
  - **主机A不能读取库中其他表的信息**，但其他主机可以读取库中所有表的信息
  - 如果要修改被锁表的信息
    - 主机A如果对表进行修改，**会修改失败**
    - 其他主机对表进行修改，**会被阻塞，直到锁被释放**

**演示**

- 给dept表加锁并查询状态

  ```
  LOCK TABLE dept READ;
  
  SHOW OPEN TABLES;Copy
  ```

  [![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200820151441.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200820151441.png)

**读取**

- 两个客户端分别读取dept表的信息，都能读出来

  ```
  SELECT id FROM dept WHERE id = 1;Copy
  ```

- 客户端A（加锁端）A**读取其他表**信息，**读取失败**

  ```
  SELECT * FROM t_emp;Copy
  ```

  [![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200820152614.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200820152614.png)

- 其他客户端读取度其他表信息，读取成功

  ```
  SELECT * FROM t_emp;Copy
  ```

  [![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200820152714.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200820152714.png)

**修改**

- 客户端A对表中内容进行修改，**修改失败**

  ```
  DELETE FROM dept WHERE id = 1;Copy
  ```

  [![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200820151654.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200820151654.png)

- 客户端B对表中内容进行修改，进入阻塞状态

  ```
  DELETE FROM dept WHERE id = 1;Copy
  ```

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200820151737.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200820151737.png)

- 从客户端A解锁后，客户端B修改成功

  ```
  UNLOCK TABLES;Copy
  ```

  [![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200820151818.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200820151818.png)

### 写锁

主机A给表加上**表锁（写锁）**以后

- 主机A可以读取该表信息，但**其他主机读取时，会进入阻塞状态，直到读锁被释放**
- **主机A不能读取库中其他表的信息**，但其他主机可以读取库中**除该表以外所有表**的信息
- 如果要修改被锁表的信息
  - 主机A如果对表进行修改，修改成功
  - 其他主机对表进行修改，**会被阻塞，直到锁被释放**

**演示**

- 给dept表加上写锁并查看

  ```
  LOCK TABLE dept WRITE;
  
  SHOW OPEN TABLES;Copy
  ```

  [![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200820153259.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200820153259.png)

**读取**

- 客户端A查询该表内容，查询成功；读取其他表，读取失败

  ```
  SELECT * FROM dept;
  
  SELECT * FROM t_emp;Copy
  ```

  [![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200820153403.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200820153403.png)

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200820153437.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200820153437.png)

- 其他表读取该表信息，进入阻塞状态

  ```
  SELECT * FROM dept;Copy
  ```

  [![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200820153517.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200820153517.png)

- 释放后，读取成功

  ```
  UNLOCK TABLES;Copy
  ```

**修改**

- 客户端A修改**该表**内容，修改成功

  ```
  DELETE dept WHERE id = 2;Copy
  ```

  [![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200820153637.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200820153637.png)

- 客户端A修改**其他表**内容，修改失败

  ```
  DELETE FROM t_emp WHERE id = 2;Copy
  ```

  [![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200820153710.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200820153710.png)

- 其他客户端修改**该表**内容，进入阻塞状态

  ```
  DELETE FROM t_emp WHERE id = 2;Copy
  ```

### 总结

**读锁不会阻塞读，只会阻塞写。但是写锁会阻塞读和写。**

## 行锁

**InnoDB使用行锁，并且支持事务**，事务相关可参考 [**MySQL基础**](https://nyimac.gitee.io/2020/08/11/MySQL基础/#六、事务)

### 特点

事务相当于锁

如果两个客户端**对同一条记录进行修改**

- 客户端A修改后，未提交（未commit），此时客户端B修改，则会阻塞
- 客户端A修改后，提交后，客户端B再修改，则不会阻塞

如果两个客户端分别**对不同的记录进行修改**，则不会被阻塞

**修改同一条记录**

```
--关闭自动提交
SET autocommit = 0;

--客户端A、B查询id=2的记录
SELECT * FROM t_emp WHERE id = 2;

--客户端A进行修改操作（将年龄改为了80），但未提交
UPDATE t_emp SET age = 80 WHERE id = 2;

--客户端A进行查询
SELECT * FROM t_emp WHERE id = 2;

--客户端B进行查询
SELECT * FROM t_emp WHERE id = 2;

--客户端B进行修改（客户端A未提交）
UPDATE t_emp SET age = 90 WHERE id = 2;

--客户端A提交
COMMIT;

--客户端B提交
COMMIT;Copy
```

**对应结果**

客户端A查询结果

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200820163718.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200820163718.png)

客户端B查询结果

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200820163718.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200820163718.png)

客户端A修改后A查询

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200820163847.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200820163847.png)

客户端A修改后B查询

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200820163718.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200820163718.png)

客户端A修改，未提交，此时B进行修改，**被阻塞**

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200820163957.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200820163957.png)

客户端A提交后，B修改成功

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200820164036.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200820164036.png)

**修改不同记录**

```
--客户端A对id=2的年龄进行修改
UPDATE t_emp SET age = 90 WHERE id = 2;

--客户端B对id=3的年龄进行修改
UPDATE t_emp SET age = 30 WHERE id = 3;

--客户端A，B分别提交
COMMIT;
COMMIT;Copy
```

因为InnoDB使用行锁，对于不同行的操作，不会出现阻塞现象

### 索引失效

索引失效，**行锁变表锁**

当**索引失效**后，即使多个客户端操作的不是同一条记录，**如果未提交，其他客户端也会进入阻塞状态**

所以要**避免索引失效**

因为索引失效要对全表操作？

### 间隙锁危害

#### 概念

当我们用**范围条件**而不是相等条件检索数据，并请求共享或排他锁时，InnoDB会给符合条件的已有数据记录的索引项加锁

对于键值**在条件范围内但并不存在的记录**，叫做**“间隙(GAP)**” ，**InnoDB也会对这个“间隙”加锁**，这种锁机制就是所谓的间隙锁(Next-Key锁)。

#### 危害

因为Query执行过程中通过过范围查找的话，他会锁定整个范围内所有的索引键值，即使这个键值并不存在。
间隙锁有一个比较致命的弱点，就是当锁定一个范围键值之后，即使某些不存在的键值也会被无辜的锁定，而造成在锁定的时候无法插入锁定键值范围内的任何数据。在某些场景下这可能会对性能造成很大的危害

#### 演示

```
--查询表记录，此处没有id=2的记录
SELECT * FROM t_emp;

--客户端A进行范围查询，但是范围内没有id=2的记录
UPDATE t_emp SET deptId = 1 WHERE id>1 AND id < 6;

--客户端B进行插入数据，插入一条id=2的记录
INSERT t_emp VALUES(2, '岳不群', 11, 2, 100002); 

--客户端A提交
COMMIT;

--客户端B提交
COMMIT;Copy
```

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200820170126.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200820170126.png)

**客户端B进入阻塞状态**

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200820170617.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200820170617.png)

提交后，插入成功

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200820170654.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200820170654.png)

**结论**：可以看到表中本来**没有id=2的记录**，但是在客户端A进行**范围修改**时，客户端B对**在范围内但不存在的数据进行插入时，客户端B进入了阻塞状态**

### 锁住指定的一行

```
BEGIN;

--锁住指定的一行，如果进行更新操作就是 ... FOR UPDATE，删除操作就是 ... FOR DELETE 以此类推
SELECT * FROM t_emp WHERE id = 1 FOR UPDATE;

--进行修改操作
UPDATE t_emp SET NAME = '风车车' WHERE id = 1;

--提交
COMMIT;Copy
```

如果当某一行被锁住后，其他客户端对改行进行相应的操作，会被**阻塞**

### 总结

Innodb存储引擎由于实现了**行级锁定**，虽然在锁定机制的实现方面所带来的性能损耗可能比表级锁定会要更高一些， 但是在整体**并发处理能力方面要远远优于MyISAM的表级锁定的**。当系统并发量较高的时候，Innodb的整体性能和MyISAM相比就会有比较明显的优势了。
但是，Innodb的行级锁定同样也有其脆弱的一面，当我们**使用不当的时候**，可能会让Innodb的整体性能表现不仅不能比MylSAM高，甚至可能会更差。

# 复制

## 主从复制

主要涉及三个线程：binlog 线程、I/O 线程和 SQL 线程。

- **binlog 线程** ：负责将主服务器上的数据更改**写入二进制日志**（Binary log）中。  读出数据
- **I/O 线程** ：负责从主服务器上读取二进制日志，并**写入从服务器的中继日志**（Relay log）。   传递数据
- **SQL 线程** ：负责**读取中继日志**，解析出主服务器已经执行的数据更改并在从服务器中重放（Replay）。  写入数据

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200822133613.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200822133613.png)

## 读写分离

**主服务器处理写操作**以及实时性要求比较高的读操作，而**从服务器处理读操作**

读写分离能提高性能的原因在于：

- 主从服务器负责各自的读和写，极大程度**缓解了锁的争用**
- **从服务器**可以使用 [MyISAM](https://nyimac.gitee.io/2020/08/16/MySQL高级/#引擎层)，提升查询性能以及节约系统开销
- 增加冗余，提高可用性

读写分离常用代理方式来实现，代理服务器接收应用层传来的读写请求，然后决定转发到哪个服务器

[![img](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200822133739.png)](https://nyimapicture.oss-cn-beijing.aliyuncs.com/img/20200822133739.png)

