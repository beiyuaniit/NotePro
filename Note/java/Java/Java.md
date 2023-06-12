# ==基础==

## 基本类型

- 引用

  - 类class

  - 接口interface

  - 数组array

- long

  - 必须以l或者L结尾

  ```java
  long x=32L;
  ```

- float

  - 以f或者F结尾

  ```java
  float f=3.2F
  ```

- char

  - 2个字节，可表示为中文。
  - 用单引号括起来''

- 运算

  - byte、char、short运算时，结果为int。防止溢出

```java
//向下转型

//整数默认是int
byte b=2;//声明时已经byte b=(int)2;??
//b=b+3;//无法通过编译。3是int。大转小要强制类型转换
b=(byte)(b+3);

//小数2.3默认是double
float f=2;
f= (float) (f+2.3);


/*八种数据类型中除了boolean外七种可以转换
    小容量转换为大容量（自动类型转换）
		byte<short char<int<long<float<double
	大转小：强制类型转换，但是可能精度损失
	byte 0~255
*/
//以下优点特殊
byte b=8;
char c= (char) b;
byte bb= (byte) c;
short s= (short) c;
short ss=b;

//char是2个字节。可以存储中文
char c='看';

/*长整型可以只写long x=231;但是不能long y=2147483648;及更大的数;因为后面 数字是默认整型int
可以改为long y=2147483648L;*/
```

## 枚举

- enum：java的一个关键字。用来定义枚举类型
- 单实例
- Enum：一个抽象类，本身不能来new对象。用enum关键字定义的类默认继承该类。

```java
//在Enum中有以下属性
private final String name;
private final int ordinal;

//获取方法
public final int ordinal() {return ordinal;}
public final int name() {return name;}

```

- enum本质:本身是一个类，类中又包含了多个同类对象

```java
//反编译（部分代码）
final class Num extends Enum{//final修饰。不能被继承
    //私有构造函数。
    private Num(String s,int i){
        super(s,i);
    }
    
    /*
    static保证不用创建Num类就能访问已经创建好的对象
    就是用类来访问对象。
    */
    public static final Num ONE;
	public static final Nnm TWO;
    
    //static只加载一次。确保了不会嵌套创建对象
    static{
        ONE=new Num("ONE",0);
        TWO=new Num("TWO",1);
        
        $VALUES=(new Num[]{
            ONE,TWO
        });
    }
}
```



- 返回下标

```java
 public enum Num{ONE,TWO}//创建时甚至不需要;结尾
 public static void main(String[] args) {
        System.out.println(Num.ONE.ordinal());//0
        System.out.println(Num.TWO.ordinal());//1
    }
```

- 甚至能定义重复的。。

```java
public enum Num {ONE,ONE};//当然它们是不同的对象
```

- 返回所有的对象的名字

```java
System.out.println(Arrays.toString(Num.values()));//[ONE, TWO]
```

- 真正的使用

```java
//int num=new int[Num.ONE];不能直接当int使用。。

//那到底用在哪呢
```

## &和&&

- &&是短路与，若第一个表达式成立则不比较第二个。&是比较完左右表达式。
- |和||亦是如此

```java
int a=0;
System.out.println(a==1|a++==1);
System.out.println(a);//1
```

## 记录执行时间

- 睡眠啥的可以查Thread类

```java
    public static void main(String[] args) throws InterruptedException {
        long start=System.currentTimeMillis();
        Thread.sleep(1000);
        long end=System.currentTimeMillis();
    }
```



## 命令行

- 编译

```Java
javac Welcome.java
javac welcome*.java//与通配符使用，编译以welcome开头的
 /*
 即使中文出现在注释中也可能出错.因为javac是GBK编码
 */
 javac -encoding UTF-8 HelloServlet.java//解决
```

- 运行

```Java
java Welcome
/*
包名要和当前文件路径一致。
*/
```

 - Shell

```Java
jshell
```

- jconsole

```shell
jconsole
#java监控和管理控制台，可以查看JVM的运行信息
```

## 命名规则

- 不能以数字开头。
- 驼峰命名法
- 局部变量会覆盖同名成员变量

## 类型

- byte：-128~127         short：2字节
- int、float：4个字节                int差不多20亿
- long、double：8个字节
- 二进制：0b或者0B开头    

```java
int a=0B1101;
```



 ## strictfp关键字

 - 标记的方法使用严格的浮点计算生成可再生的结果
 - 标记的类，类中的所有方法都严格计算
 - 如有些寄存器80位，结果double用64位存储。
   - 不加strictfp的话则中间结果截断为64位。
   - 使用后，则中间结果用80位，直到最终结果才截断

```java
public static strictfp void main (String []args){}
```

## 数学函数与数学常量

-用Math类名调用

```java
double y =Math.sqrt(x);
```

## 进制

```java
/*
开头
二进制：0b或0B
八进制：数字0
十进制
十六进制：0x或0X
*/
int a=0b101;
int b=0167;
int c=0x4AB3;
```

## 强制类型转换

- 前面加上(类型)

```java
Cat cat=(Cat) animal;
```

## 三元运算符

- 成立选择第一个，不成立第二个的值
- 太好用了，简化if写法

```Java 
return  x <y ?x:y;
```

## 位运算

- 按照每位进行运算
- 乘/除2或其倍数，一律用左右移
- 左右移的优先级小于加减乘除

```java
&  与
|  或
~  非
^  异或
<< 左移(一位则乘2)
>> 右移(符号位补充高位)
>>>右移(0补充高位)
没有<<<

x>>y;  //x右移y位
```

## String

- 方法

```java
//把多个字符串连接起来，并用分隔符分开
String all=String.join("/","S","M","A");
//all="S/M/A"

String s="Welcome to java";

//匹配
System.out.println(s.matches(".*come.*"));//true
```

- String没有提供修改其中某个字符的方法
- ""空串也是个对象，与null不同
- 判断相等equal()
- 码点与代码单元。

```java
str.charAt(int index)//返回给定位置的代码单元
```

- StringBulider线程不安全，但是速度快点。StringBuffer线程安全

```java
/*创建对象
添加字串
toString返回
*/
StringBuilder strbuilder=new StringBuilder();
strbuilder.append(ch);
strbuilder.insert(i,str);
String str=strbuilder.toString();
```

## 输入输出

- System.in

```java
Scanner in=new Scanner(System.in);
String name=in.nextLine();//一行输入，可以包含空格
String firstName=in.next();//空白符作为分隔符
int age=in.nextInt();


```

- System.console()
- 输入不可见，用来读取密码。每次一行输入，没有读取单个单词，数值的方法。不如System.in方便

```java
Console cons=System.console();//此处居然不用new创建对象
String username=cons.readLine("User name:");
//用char数组存放而不用String，为了处理完密码后可以很好覆盖
char [] passwd = cons.readPassword("Password:");//效果是啥也没有，连*遮挡都没有

/*
  cons为null
  原因
  如果Java程序要与windows下的cmd或者Linux下的Terminal交互，就可以使用这个Java Console类代劳。
  Java要与Console进行交互，不总是能得到可用的Java Console类的。一个JVM是否有可用的Console，依
  赖于底层平台和JVM如何调用。如果JVM是在交互式命令行（比如Windows的cmd）中启动的，并且输入输出
  没有重定向到另外的地方，那么就可以得到一个可用的Console实例。
  在命令行下运行就没有什么问题
*/
```

## 格式化输出

```java
System.out.printf("%3.2f",x);//3个字符，小数点后2位。前面用空格补全
System.out.printf("%s",str);  
```

## 文件输入输出

```java
//用之前学过的任意方法读取就可以了。如in.nextLine()
Scanner in =new Scanner(Path.of("myFile.txt"), StandardCharsets.UTF_8);

//用print,printf等都可以输出。不存在则创建
PrintWriter out=new  PrintWriter("myFile.txt",StandardCharset.UTF_8);

//绝对路径。" c:\\a\\b.txt"

//相对路径。"../a/b.txt"  ,"./a/b.txt"
```

## 流程控制

- 块block:若干条语句组成，并用{}括起来。使原本只能放一条语句的地方可以放多条

```java
/*
块确定了变量的作用域，但是嵌套块中不能定义同名变量。如以下是错的
但是C++可以嵌套定义
*/
public void getA(){
    int a;
    {
        int a;
   }
}
```

- 条件

```java
if{}
else if{}
else{}
//else与最近的if构成一组
```

- 循环

```java
 while(){}
 do{}while(); 
//用浮点数可能死循环.如
for(double x=0;x!=10;x+=0.1)//0.1无法用二进制精确表示，存在舍入的误差.IDEA中还真的没停下来
```

-  多重选择
   -  switch
      -  只能是byte,short,char,int,枚举,String 
      -  case后只能是常量，不能是范围

```java
/*
和C/C++完全一样
标签类型
char,byte,short,int的常量表达式
枚举类型
java7开始，也可以是字符串字面量.
*/
switch(choice){
    case 1:
        ...
        break;
    case 2:
         ...
         break;
     default:
         ...
         break;
}
```

- goto
  尽管Java设计者将goto作为关键字，但实际并没有打算使用

- 带标签的break

​		可用于跳出多重循环

```java
//标签放在外层循环之前。遇到则直接跳出
toContinue:
while(){
    for(){
        break toContinue;
    }
}
```

## 大数

- BigInteger	任意精度整数运算
- BigDecimal   任意精度浮点数

```java
/*
在Java.math中
*/
//定义
BigInteger a=BigInteger.valueOf(200);//一般数
BigInerger b=new BigInteger("47197419479237491247392");//任意精度
BigInteger.ZERO,BigInteger.ONE...//一些常量

//计算。不能用+-*/，即使是正常的数。可以用成员方法
BigInteger c=a.add(b);
BigInteger d=a.multiply(b);
a.compareto(b);//比较

```

## 数组

- 概述

  - 也是一个对象


  - 用Arrays类的方法 可对数组进行操作

  - 作为实参时是引用传递


  - 创建和访问
  - 作为字段或局部变量都有默认值，可以不用初始化。

- 代码

```java
//只是声明了一个int[]类型的变量
int[]a;

//创建并静态初始化
int []a={1,2,3};
int []a=new int[]{1,2,3};

//创建，并在之后动态初始化
int[]a=new int [100];//这才是创建一个数组
int[]a=new int[n];//n不能是未初始化的n

//匿名数组
new int []{1.2.3};//不能在任意位置创建。目前来看可以用来作为方法实参

//对象数组。不用()
Set []sets=new HashSet[3];//声明一个变量
for(int i=0;i<3;i++){//还得初始化才能用
   sets[i]=new HashSet();
}

//访问。可以用下标，不像字符串
a[1];
a[0]=3;
```

- 打印

```java
//简单的话直接同String
//只是a.toString()的话会打印在内存中的地址
System.out.println(Arrays.toString(a));
```

- 拷贝

```java
/*
数值型，额外元素赋值为0，布尔型，额外元素赋值为false（好像并不会）
位置不够时，只拷贝前面的元素
*/
int []b=a;//2个引用指向同一对象
int []b=Arrays.copyOf(a,n);//创建一个新的数组。n是长度，或者说希望拷贝的个数
Arrays.copyOfRange(a,1,4);
```

- 查找

```java
Arrays.binarySearch(a,3);
```

- 判断相等

```java
Arrays.equals(a,b);
```

- 排序

```java
/*
比较其他类型则要实现比较器接口

只有升序。没有其他方法实现降序。如果想降序访问，通过下标就能很好实现，所以就没有降序？？
*/
Arrays.sort(a)
```

- 多维数组

​		java实际上没有多维数组，底层是一个数组的数组。所以可以出现不规则数组

```java
int arr[][]= new int [3][];//行必须有值，列可暂时没有
double [][]nums=new double[m][n];
double [][]nums={{1},{1,2}};//创建了2个一维数组对象nums[0],nums[1]
```

## for each遍历

```java
//语法。collection必须是数组或实现了Iterable接口的对象
for(variable:collection)statement;
for(double num:nums){}
```

  ##  数组元素的默认值

  - 引用类型有很多。数组也是对象
  - 在方法中定义也有默认值？

  ```java
一维 []arr
   int 0
   float 0.0
   char  0或'\u0000'   不是48那个'0'
   boolean  false 
   引用null
二维
int [][]arr = new int[3][2];
   arr[i]      外层地址
   arr[i][j]   内层和一维一致
int [][]arr = new int[3][];
   外层null
   内层，不能访问，否则报错
  ```



# ==面向对象==

## 变量默认值

- 成员变量在创建时不赋值的话会有默认值（0或者null）
- 局部变量不会赋默认值。
- 使用未赋值成员变量不会报错（有默认值），使用未赋值局部变量会报错。

## 内存分区

- JVM主要三内存区：
  - 方法区：方法代码，静态数据
  - 桟区：方法调用时在桟区分配空间
  - 堆区：对象
- 一个线程有一个桟区，所有线程共用一个方法区和一个堆区
- GC垃圾回收机制主要针对堆区数据
- 引用是局部变量时，放在栈中。所引用的对象放在堆中

## 创建对象过程

- 1）类加载检查：虚拟机遇到一条new指令时，首先将去检查这个指令的参数是否能在常量池中定位到这个类的符号引用，并且检查这个符号引用代表的类是否已被加载过、解析和初始化过。如果没有，那必须先执行相应的类加载过程。

- 2）分配内存：在类加载检查通过后，接下来虚拟机将为新生对象分配内存。对象所需的内存大小在类加载完成后便可确定，为对象分配空间的任务等同于把一块确定大小的内存从Java堆中划分出来。分配方式有“指针碰撞”和“空闲列表”两种，选择那种分配方式由Java堆是否规整决定，而Java堆是否规整又由所采用的垃圾收集器是否带有压缩整理功能决定。

- 3）初始化零值：内存分配完成后，虚拟机需要将分配到的内存空间都初始化为零值（不包括对象头），这一步操作保证了对象的实例字段在Java代码中可以不赋初始值就直接使用，程序能访问到这些字段的数据类型所对应的零值。

- 4）设置对象头：初始化零值完成之后，虚拟机要对对象进行必要的设置，例如这个对象是那个类的实例、如何才能找到类的元数据信息、对象的哈希吗、对象的GC分代年龄等信息。这些信息存放在对象头中。另外，根据虚拟机当前运行状态的不同，如是否启用偏向锁等，对象头会有不同的设置方式。

- 5）执行init方法：在上面工作都完成之后，从虚拟机的视角来看，一个新的对象已经产生了，但从Java程序的视角来看，对象创建才刚开始，init方法还没有执行，所有的字段都还为零。所以一般来说，执行new指令之后会接着执行init方法，把对象按照程序员的意愿进行初始化，这样一个真正可用的对象才算完全产生出来

## 创建子类对象

- 虽然程序员并没有在程序中主动调用父类(Person)的构造方法，但为了遵循“先父后子”的创建顺序，编译器会在编译源代时会自动把调用父类构造方法的语句添加到程序中，并且会把父类的构造方法添加到子类构造方法的第一行

```java
Student(){
    super();
    System.out.println("子类构造方法被执行");
}
```

## static方法

- 只能访问类中的静态数据.无法访问类中的非静态成员变量和方法 
- 不能使用this，因为没有对象存在
- 可以被重载，不支持重写

## 代码块

- 普通代码块
  - 类中纯粹用{}括起来的代码
  - 在构造函数执行开始前执行，每创建一个对象执行一次
- 静态代码块
  - static {}
  - 类加载时执行，并且只执行一次
  - 用处：监视该执行期的行为

## 转型

- 向上转型
  - 又称为自动类型转换
  - 父类型<-子类型。或者大精度<-小精度
- 向下转型
  - 要强制类型转换
  - 用instanceof校验向下类型转换的合法性

## 多态

- 动态方法

  - 父类引用指向子类对象（自动转换）

    - 如：Animal  a=new Cat（）；
    - 使用父类对象的地方都可以使用子类对象代替(子类重写了父类的方法且存在对象转换
  - （编译时是静态绑定a当做Animal类来检查语法，运行时是动态绑定，也就是实际创建的是Cat对象）

    - 调用a其实就是调用Cat类的一个对象
    - 同一个指令，不同的子类有不同的行为
- 要求
  - 父类中也有子类的同名方法。（一般子类重写了多态才有意义
  - 只能调用父类和子类中都有的方法，如果子类没有重写则调用父类的
  - 不能直接调用子类中特有的方法
- 真的要访问子类中特有的话

  - 向下类型转换。Cat c=（Cat）a；通过新建的c引用访问
- 作用
  - 降低程序耦合度，提高扩展力（不用在父类中重写所有功能相似的子类方法）
  - 调用的类只需要对父类进行操作即可
  - 大型程序开发中作用就明显了  				

## final

- 不可变的

- 修饰
  - 类：无法被继承
  - 方法：无法被重写
  - 局部变量：赋值后无法改变值（只能赋值一次）
  - 成员变量：必须手动赋值（不然报错），赋值后无法改变值
    - 可以声明时赋值，也可以声明后在构造方法中赋值
  - 引用：不能指向其他对象了，无法被GC回收（引用的值是地址，地址不能再改变）
    - 但是可以修改对象的数据（通过地址访问）

- static联合使用，被修饰的称为“常量”
  - 节省内存，值不改变
  - 常量值不能改变，属于类本身
  - 般所有字母大写，单词用下划线连接

## 包

- 本质是一个文件夹
- 类的容器，用于存放相关的类
- 命名：a.b.c
- 一个类可以使用同包下的所有类，以及其他包中的公共类

- 导入静态方法和静态字段（没啥用

```java
import static java.lang.System.*;
out.println("Well");//不必加类名前缀
```

- 类的包名要与存储路径一致

## super

- 本质：只代表对象的父类型特征，不是引用，不指向任何对象（所以不能return super;）this就可以解决父类子类同名问题
- 使用
  - super.属性名;	访问父类中属性
  - super.方法名（）;	调用父类中方法
- super.（实参）;		
  - 调用父类中构造方法，在子类对象创建前初始化父类，以便能够继承父类的特征。（不创建父类对象
  - 不提供则调用默认的super();
  - 不和this();同时出现在子类的构造方法中。因为2个都要求在构造方法第一行出现
    - this();super();		//this()执行时没有父类的初始化
    - super();this();        //第一个执行super();没有问题，执行this();后又重新进入了super();。。造成很多意想不到的结果

- this和super的变量指向同一个，而this和super的方法却不是同一个。

```java
//本md笔记演示都是再jdk14环境
public class Se {


    public static void main(String[] args) {


    D d=new D();
    System.out.println(d.a);//2

    }
}

class  C{
    int a;
    public C(){
        a=1;
    }
    
    public void test(){
        System.out.println("super method");
    }
}

class D extends C{
    public D(){
        this(2);//1.		this(2);只能在第一行，直接进入D(2)。来创建对象。
        
        //从D(2)返回时已经创建好对象。不会再创建。保证对象只创建一次
        System.out.ptintln(a);
    }
    public D(int a){
        //super();//2.默认加了然后执行。a=4
        System.out.println(this.a);//1
        this.a=a;//a=2			
        /*
        此时super.a和this.a是同一个变量。改变其一就会影响另一个。如super.a=100;后this.a==100;亦是如此
        引用类型的变量也是一样
        */
        System.out.println(super.a);//2	
        
        
        //但是对于方法。父类的副本一直在，可以调用
        super.test();//父类的
      	this.test();//子类的
      	test();//子类的
    }
    
    public void test(){
        System.out.println("this method");
    }
    
    public void fun(){
        super.test();//
    }
    /*
    	通过对象方法也可以
    	D d=new D();
    	d.fun();//父类的。
    */
}



public class Se {


    public static void main(String[] args) {

    C c=new D();
    System.out.println(c.a);//3。即使通过父类引用，拿到的依旧是子类的。
    //方法的话就是多态了，也是拿到子类的


    }
}

class C{
    int a=1;
    public C(){
        a=2;
    }
}
class D extends C{
    public D(){
        System.out.println(super.a);//2
        System.out.println(this.a);//2
        a=3;
    }
}

```

## 概念

- 类和对象
  - 类：制作月饼的模具
  - 对象：月饼
- 并不是所有的类都表现面向对象的特征。如Math类只封装了功能，并不隐藏数据。

## 类之间的关系

- 依赖：use a。一个类的方法访问其他对象。应尽量避免，来降低耦合度
- 聚合：has a。一个对象包含另一个对象。或者叫关联
- 继承：is a。

## 一些类

- Date：描述了一个时间点。如"December 31,1999,23:59:59 GMT"。

```java
Date a=new Date();
```

+ LocalDate:描述了一个用日历表示的日期

```java
//可用于记录一些日期，如借还日期
//应当使用静态工厂方法(factory method)来构建对象。而不是构造方法
LocalDate a,b,c;
now=Local.now();
a=Local.(2007,3,31);

//一些方法。并没有什么用
int year=a.getYear();
int month=a.getMonthValue();
int day=a.getDayOfMonth();

//有用点的
b=a.plusDays(100);//创建并返回一个100天后的对象
c=a.minusDays(int n);//创建并返回前或后n天的日期

DayOfWeek weekday=a.getDayOfWeek();
int m=weekday.getValue();//得到当前日期的星期几

```

## 自定义类

- 构造函数没有返回值。public Animal(){}
- 一些类内的辅助方法应该设置为private

## var关键字

- java10开始，如果初始值能推出类型，可以使用var声明局部变量，而无须指定类型。

```java
var harry=new Employee();
//注意，应该在方法内声明。一般也不对基本类型用var
```

## static-成员变量

- 声明时就要赋值。和final一起就变成了常量

- public final static或者public static 都可以。甚至 final static public。不过还是规范点吧

## 工厂方法 factory method原因 

- 返回不同名字或类型的对象

## 方法参数

- 值调用(call by value)：不会改变原基本数据类型的值
- 引用调用(call by reference):传递的也是引用的副本，即不会改变原引用的值。但是可通过引用访问对象

## 重载

- 类内。同名、不同参数的方法
- 对返回值没有要求

## 默认字段初始化

- 如果没有对成员变量显式赋值，则创建对象后默认0、null、false

## 赋值

- 声明时
- 构造函数
- 代码块

## 重写

- 也叫方法覆盖
- 名称、参数、返回值和父类中的一致
- 重写的方法访问权限不能比父类的小

```java
/*
子类不能访问父类的私有字段
*/
public getArea(){
    //不理解以下这样做。。
    double a=super.getArea();
    return a*1/2;
}
```

## 子类调用父类的构造方法

- super();//完成父类部分的构造

## 多态和instanceof

- 判断左边对象是否是右边类的实例。子类也行

```java
//y可以是父类或接口。也就是要求x具有y的特征
x instanceof y;//多态中父类向向子类转换

```

## 抽象类

- 抽象类可以没有抽象方法，抽象方法必须在抽象类
- 抽象类中可以有普通方法和变量

```java
public abstract class Person{
    ...
    public abstract void fun();//没有{}实现
}
```

## 访问修饰符

- private			仅对本类可见
- 默认                 对本包可见
- private            对本包和子类可见
- public              完全可见

## Object

- Java只有基本类型(primitive type)不是对象。数组是对象
- 一些方法
  - equals()
  - getClass()        返回一个对象的所属类
  - hashCode()
  - toStriing()
  - getName()         返回类的名i在
  - Class getSuperclass()         返回其父类

## 泛型

- <>声明时不允许是基本数据类型。填入取出数据时会自动装箱拆箱

```java
var list=new ArrayList<Integer>();
list.add(3);//自动装箱。Integer.valueOf(3);
list.get(i);//自动拆箱。x.intValue();
```

## 包装类

- ==不确定结果。

```java
Integer a=100;
Integer b=new Integer(100);
System.out.println(a==b);//false

Integer a=new Integer(100);
Integer b=new Integer(100);
System.out.println(a==b);//false

 Integer a=100;
 Integer b=100;
System.out.println(a==b);//true
```

- 判断值是否相等用equals()

```java
Integer a=100;
Integer b=new Integer(100);
System.out.println(a.equals(b);//true

Integer a=100;
Integer b=100;
System.out.println(a.equals(b));//true
```

- 字符串转数字

```java
static int parseInt(String s);//十进制
static int parseInt(String s,int radix);//radix进制

//2进制的“11”转化为十进制的“3”
Integer.parseInt("11",2);

```

## 方法的可变参数

- 传递任意数量指定类型的参数

- type...name				可以是任何类型，包括引用和基本

```java
//nums用一个数组来接收
public test(int i,double ...nums){}
```

## 继承的设计技巧

- 将公共方法、字段放在超类
- 少使用project，永远不知道有多少子类
- 覆盖方法时不要改变预期行为
- 子类里的访问权限不能比父类低

## 接口

- 概念：对希望符合这个接口的类的一组要求
- 是引用 数据类型，编译后生成.class文件
- 如Arrays的sort()方法,要求数组中的对象实现Comparable接口

```java
public interface Comparable{
    int compareTo(Object other);//只有一个方法
    //对象小于other返回负数，相等0
}
```

- 接口不是类，不能用new创建对象。

```java
Comparable x=new Comparable();//错误
```

- 但是可以声明接口变量、实现多态

```java
Comparable x=new Employee();//必须引用实现该接口的类

//可以用instanceof判断类型
if(x instanceof Comparable){}
```

- 接口语法

```java
//接口中方法自动设置为public，字段自动设置为public static final

//可以继承接口
public interface Powered extends Moveable{
    //可以包含静态方法。常放在伴随类中，如工具类
    double static move();
    
	//不能有实例字段，却可以有常量
	double MPVE_SPEED=3;
    
    //Java9中甚至可以有私有方法和实例方法，不过只能在接口中使用，用法有限，常常作为辅助方法
    
    /*
    默认实现
    	希望是这样实现
    	也是为了兼容，如新版本接口中增加了新方法，提供默认实现来兼容旧版本的代码
    */
    default void remove(){throw new UnsupportedOperationException("remove");}
}
```

- 接口方法冲突
  - 待补充

## 回调实现定时器

- java.swing.Timer

- 其他函数给定时器传递一个函数，Java给定时器传一个对象。对象可以携带一些信息，更加灵活。
- 当然，定时器要知道调用哪个方法，并要求传递的对象实现java.swt.event.ActionListener接口

```java
import javax.swing.*;//是该包下的Timer
public class 定时器02 {
    public static void main(String[] args) {
        var listener=new TimerPrint();
        new Timer(1000,listener).start();

        JOptionPane.showMessageDialog(null,"Quit program?");
        System.exit(0);
    }

}

 //每隔1秒打印下时间并响铃
class TimerPrint implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e) {
        //想要执行的语句放在actionPerformed()中。该接口只有这个方法
        System.out.println("the time is "+ Instant.ofEpochMilli(e.getWhen()));
    }
}

```

## 自定义排序

- Arrays.sort()			//默认使用调用类的比较器

```java
//第二个参数是一个比较器
static <T> void sort(T[] a, Comparator<? super T> c)

/*
Comparable
	类内比较。类A实现了，之后就能调用Arrays.sort(a);按照规则排序le
	其中唯一的方法是public int comparaTo(Object o)
	//都在类内了。因此只能给引用类型排序，不能给基本数据类型
Comparator
	没有实现Comparable接口的类编写排序规则。如类B没有实现Comparable，也可以Arrays.sort(b,cmp);
	排序方法叫做public int compare(Object o1, Object o2)
	//参数只能是Object。因此只能给引用类型排序，不能给基本数据类型
	前-后			下标0~n  升序
*/
```

- 法一：传递一个新的实现了Comparator接口的对象

```java
//字符串按照长度排序
class LenthComparator implements Comparator{//此处绝对不是Comparable
    public int compare(String str1,String str2){
        return str1.lenth()-str2.lenth();
    }
}

Arrays.sort(str,new Lenthcomparator());
```

- 法二：下文中lambda表达式

## 对象克隆

- 最浅的：引用直接赋值

```java
/*
引用的值相同，所引用的对象是同一个。所有基本数据类型和引用类型字段完全相同
*/
Employee copy=original;
```

- 浅拷贝

```java
/*
Object.clone()
2个不同的对象。基本类型是不同的副本，但是引用类型字段的值一样，也就是引用同一个对象
若是引用的对象是不可变，那么这是安全的。如String name;
*/
Employee copy=original.clone()
    
/*
Object中是projected。只能在子类调用父类的，或者父类的同包类调用
protected Object clone()throws CloneNotSupportedException

实现Cloneable接口（可选）
重写clone方法
将其设置为public			为了其他包的类能用该实现类的对象调用
*/
public class Employee implements Cloneable{
    ...
    public Employee clone(){
        //调用clone()返回一个新的对象
    	Employee cloned=(Employee)super.clone;//先进行浅拷贝
        cloned.hireDay=(Date)hire.clone();//Date本身是深拷贝
        
        return cloned;
	}
}
Employee copy=original.clone();//就是深拷贝了
```

- Cloneable

```java
public interface Cloneable {
}
//标记接口。if(x instanceof Comparable){}
```

## lambda表达式

- 是一个含有变量规范可传递的代码块，可以在以后执行一次或多次

- 格式

```java
//无须指定返回值。会根据上下文自动推出
(type args)->{}
(int a,int b)->{return a-b;}

//参数类型可推导出，则可无须指定
Comparator <String> comp=(str1,str2)->{return str1.length()-str2.length();};//命名后可重复使用

//只有一个参数且类型可推断出，甚至可以不用小括号()
ActionListener listener=event->{System.out.println("the time is"+Instant.ofEpochMilli(event.getWhen()));};

//一点原理
Arrays.sort(strs,
            (str1,str2)->{return str1.length()-str2.length();});
//调用strs对象上的compare方法时会执行lambda表达式中的代码
```



- 函数式接口
  - 只有一个抽象方法的接口，使用时可以选择lambda的方式
  - lambda前面的接口只能是函数式接口
  - 用lambda的话可以不用直接创建该接口的实现类，就能完成对应功能
- lambda表达式是对接口抽象一个方法的实现。不知道对不对？
- lambda表达式的组成
  - 代码块{}中
  - 参数()
  - 自由变量的值。(){}之外定义的
- 变量使用规范
  - 不能改变自由变量的值。（栈帧中的局部变量 是线程私有的，如果lambda表达式被其他线程调用，就很危险了）
    - lambda捕获的是final或effectively final的变量，不会再赋新值

  - 内部定义的变量名字不能与自由变量和参数一致

- 再lambda中使用this

```java
//this 是指创建lambda的方法的this
public class LambdaTest{
    public void init(){
        ActionListener listener=event->{
            System.out.println(this.toString());//此处this代表LambdaTest
        }
    }
}
```

- 处理lambda表达式
  - 待补充。一些重要的函数式接口

## 方法引用

- 它指示编译器生成一个函数式接口的实例，覆盖该接口的抽象方法，来调用给定的方法
- 类似于lambda表达式，方法引用也不是一个对象。不过一个类型为函数式接口的变量赋值时会生成一个对象

```java
//形式
object::instanceMethod		对象
Class::instanceMethod		类
Class::staticMethod			类

//生成一个ActionListener对象，它actionPerformed(ActionEvent e)要调用System.out.println(e);
var timer=new Timer(1000,System.out::pritln);

//对字符串排序，而不考录大小写
Arrays.sort(strs,String::compareToIgnoreCase);
```

- 构造方法引用
  - 待补充。跟流库有关，在卷二中学习。

```java
String::new
String[]::new
```

## 再谈Comparator

- Comparator包含很多方便的方法来创建比较器

```java
//转换为字符串，按照名字排序
Arrays.sort(peoples,Comparator.comparing(Person::getName()));

//第一个相同，则按照第二个排序
Arrays.sort(people,Comparator.comparing(Person::getName()).thenComparing(Person::getAge()));

//还有其他很多种形式
```

- 待补充。

## 内部类

- 为什么要使用内部类？
  - 内部类对同包下其他类隐藏
  - 内部类可以访问外部类的所有数据。包括私有
  - 但是外部类不可以访问内部类的私有数据？？。。

```java
pulic class outer{
    ...
    //修饰符和引用一样，可以有public、protected、private，默认
    public class inner{}
}
```



- 局部内部类
  - 待补充。方法内定义的类
- 匿名内部类
  - 与内部类的定义一个类不同，匿名内部类有new，是定义并直接创建对象
  - 一般是放在方法中，外呢？
  - 好处：用一个父类或接口来直接声明并创建一个对象

```java
//语法
new SuperType(construction parameters){inner class methods and data}

/*
SuperType可以是类，相当于继承了该类。可以是接口，那么在{}中就要实现接口的方法
*/

public void start(int interval){
    var listener=new ActonLisstener(){
        public void actionPerformed(ActionEvent e){
            System.out.println("the time is");
        }
    };
    
    var timer=new Timer(interval,listener);
    timer.start(); 
}

//配合Lambda表达式更加简洁
public void start(int interval){
   new Timer(interval,event->{
        System.out.println("the time is");
    }).start();
}

```

- 静态内部类
  - 当inner不需要outer的引用时，可以将其设置为static

```java
//如
class ArrayAlg{
    public static class Pair{
        //属于静态引用了吧
    }
}
```

- 服务器加载类
  - 有时侯开发一个服务架构应用
  - 待补充。
- 代理
  - 可以在运行时创建实现了一组给定接口的新类
  - 待补充
- 克隆、服务加载类和代理等高级技术主要是设计库及构建工具的人感兴趣，对应用开发的人员吸引力不大

# ==异常==

 ## 异常结构

- 异常类型

```java
/*
	都是JVM抛出
    总：Throwable
            Exception(异常
                编译时异常
                    ClassNotFoundException(试图使用一个不存在的类

                    IOException(输入输出的异常

                RuntimeException(运行时异常
                    ArithmeticException(一个整数除以0

                    NullPointerException(空引用访问对象

                    IndexOutOfBoundsException(数组越界

                    IllegalArgumentException(传递参数异常

            Error(系统错误
                LinkageError(依赖的类编译前后进行了修改

                VirtualMachineError(JVM崩溃
     */
```



- 分类
  - 非检查型异常：派生于Error类或RuntimeException的异常
  - 检查型：除了派生于Error类或RuntimeException的异常的所有异常

## 抛出异常

```java
class MyClass{
    ...
    public String readData()throws EOFException{//方法头抛出用throws
        ...
        while(...){
            if(!in.hashNext()){
                if(n<len){
                    throw new EOFException();//方法内抛出用throw
                }
            }
        }
        return s;
    }
        
}
```

## try-catch-finally

- 在try中定义的变量无法在finally中使用、关闭

```java
try{
    //没有抛出catch中指定的异常则执行下去
}catch(Exception e){
    //try中抛出了指定异常才执行
   
}finally{
    //一定执行
}

//多个异常，应当子类写在前面。否则父类会覆盖子类
try{
    
}catch(FileNotFoundException e){
    
}catch(IOException e){
    
}finally{
    
}
//或者
catch(FileNotFoundException |IOException e)

```

## 打印异常信息

```java
//返回描述信息
System.out.println(ex.getMessage());
System.out.println(ex.toString());
//返回堆栈跟踪元素数组
System.out.println(ex.getStackTrace(
//打印Throwable对象以及它的调用堆栈信息
ex.printStackTrace();

//打印堆栈信息
var t=new Throwable();
var out=new StringWrite();
t.printStackTrace(new PrintWriter(out));
String description=out.toString();
//更简洁的
StackWalker walker=StackWalker.getInstance();
walker.forEach(frame->analyze name);//没看懂，卷2的吧
//或者
walker.walk(stream->process stream);
```

## 自定义异常

```java
/*
一般继承Exception或其子类都行
习惯做法：2个构造器
	一个无参
	一个返回一个描述详细信息的字符串
*/
class MyException extends Exception{
    public Exception(){}
    public Exception(String s){
        super.(s);
    }
}

class Test{
    public void test(){
        throw new MyException("出错了");
    }
}
```



## 异常链

```java
//再次抛出异常
//1.可能会丢失原始异常细节
try{
    
}catch(SQLException e){
    throw new ServeltException("database error"+e.getMessage());
}

//2.将原始异常设置为新异常的原因。
public void fun1(){
    try{
        
    }catch(SQLException e){
        throw new ServletException("database error").initCause(e);        
    }finally{
        
    }
}

public void fun2()[
    try{
        fun1();
    }catch(ServletException e){
        Throwalbe cause=e.getCause();//获取原始异常
    }finally{
        
    }
]
    
//3.只是记录下，再重新抛出原来的
try{
    
}catch(Exception e){
    logger.log(level,message,e);
    throw e;
}finally{
    
}
```

- 嵌套try

```java
//可以没有catch
try{
    
}finally{
    
}

//分析以下代码
//如果在关闭资源的时候再发生异常呢？就没有来得及处理
InputStream in=...;
try{
    
}catch(IOException e){
    
}finally{
    in.close();
}

//解决方法，内层try确保关闭流，外层try确保报告出现的错误
try{
    try{
        ...
    }finally{
        in.close();
    }
}catch(IOException e){
    
}
//功能清楚，更加安全
```

## try-with-Resources简洁写法

```java
/*
当然这里只是为了抛出跟资源关闭的异常
有异常会自动抛出
无论有没有都执行close()
*/

try(resource re){}
/*
AutoClouseable接口
	void close()throws Exception
Closeable接口（是Auto...的子接口。
	void close()throws IOException
都是只有一个方法，只不过抛出的异常不同
对于实现了以上2个之一接口的资源类，都可以用try-with-Resources语法
*/
public static void main(String[] args) throws IOException {//方法头得抛出，因为没有处理
        try(var in=new Scanner(new File("src/一些验证/hello.txt"),//文件名就不能从当前？？？
                StandardCharsets.UTF_8);
           var out=new Scanner(new File("src/一些验证/Hello.txt"),
                StandardCharsets.UTF_8);)//可以管理多个文件
    	{
            while (in.hasNext()){
                System.out.println(in.next());
            }
        }
}


//也可以是try之前的事实最终变量（不再更改）
public test(PrintWriter out){
    try(out){
        
    }
}
```

## 使用异常技巧

- 优先使用简单的测试。如if判断除数是否是0，效率更高
- 尽量抛出明细的异常（也即是子类）。如只抛出很多RuntimeException代码逻辑就有点乱的。

## 日志

- 输出程序运行信息

```java
//获取全局日志记录器并写入日志
Logger.geGlobal.info(" ")
```



# ==高级==

## main参数

- public static void main(String[] args) {}
- JVM调用main方法时会自动传递一个String数组过来
  - args.length默认值是0,但args不是null，不会空指针异常
    - 平时也可以创建长度为0的数组，表示没有任何数据
- 在运行时在类名后面传递String数组
  - 如：java Test01 abc xyz		
  - 输出时可以args[i]
  - 用IDEA时用Run->Edit Confi…->Program arguments输入就可以了
- 作用：需要用户输入密码才可以登录系统

## 双亲委派机制

```java
/**
     * 加载class文件
     *
     * 自带的3个类加载器
     *
     *  启动类加载器（父
     *      首先启动
     *      加载JDK最核心类库rt.jar
     *
     *  扩展类加载器（母
     *      启动类加载器加载不到时
     *      加载ext/*.jar
     *
     *  应用类加载器
     *      启动类加载器和扩展类加载器都加载不到时
     *      加载环境变量classpath中的类
     */

    /**
     *   保证先使用自带的类
     *  若有同名类，先使用核心类库中的
     *  防止用户自定义与java核心类库相同的类植入病毒
     */
```

## properties文件创建对象

```java
/*
class.properties配置文件中内容。左右都不用写双引号""。也不用分号;结尾
className=java.lang.String
*/
public static void main(String[] args) throws IllegalAccessException, InstantiationException, IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException {

        //通过配置文件创建对象
        /*
        low点的
         String path=Thread.currentThread().getContextClassLoader().
                getResource("class.properties").getPath();
        FileReader reader=new FileReader(path);
        下面高级点以流形式直接返回
         */

        InputStream reader=Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("class.properties");//路径是相对于Module下的src为根路径

    	//FileReader reader=new Filereader("class.properties");还没试验成功
        Properties pro=new Properties();
        pro.load(reader);
        reader.close();

        Class c2=Class.forName(pro.getProperty("className"));
        //创建对象的类本身要存在
        Constructor con=c2.getConstructor(String.class);
        Object obj1 =con.newInstance("Hello World");

        System.out.println(obj1.toString());
        System.out.println(obj1 instanceof String);//true。但是吧，本质还是Object

        if(obj1 instanceof  String){
            String obj2=(String)obj1;
            System.out.println(obj2.charAt(0));//转型后可使用其他方法
        }
    }


//还有更加简单的
/**
 * 只绑定类路径下.propeties文件
 * 写路径时不用后缀
 * 目前最简单的方法了
 */
ResourceBundle bundle=ResourceBundle.getBundle("class");
String className=bundle.getString("className");
Class c3=Class.forName(className);

        
```

## 注解

- 概述

```
/**
 * Annotation
 * 是一种引用数据类型
 * 可以通过反射获取类的注解信息，猜测:那这样不就可以通过注解信息来检查代码是否符合规范，以及获取对象的对应信息？
 * 作用：规范代码
 *
 */

/**
 * 定义注解
 * 修饰列表  @interface 注解名{
 *
 * }
 *
 * 使用时直接放在要使用的代码上面就可以了
 */

/**
 * 常见注解
 * @Override：被注解的方法打算重写方法
 * @Deprecated：被注解的代码已经过时或者有更好的方法
 * @SuppressWarnings    抑制编译器警告
 * @SafeVarargs         “堆污染警告”
 * @FunctionalInterface     函数式接口
 *
 * 元注解：注解注解的注解
 * @Target：表明被标注的注解可以出现的位置
 * @Retention：表明被标注的注解最终保存的位置
 */
```

- 定义一个注解

```java
@Target({ElementType.TYPE,ElementType.METHOD,ElementType.FIELD})//可以被反射
@Retention(RetentionPolicy.RUNTIME)//只能出现在类，方法，属性上
public @interface MyAnnotation{
    /**
     * 注解中可以有属性
     * 使用时
     * 01.属性只有一个且名字是value，可以不指定名字
     * 02.属性是default，可以赋值，也可以不赋值
     * 03.其他情况，志明属性名字并赋值
     */
    String name();

    int num() default 2;//注解中才可以这样使用

    int value() default 4;
}
```

- 使用注解		不是很懂。

```java
public class 注解的定义和使用 {
    public static void main(String[] args) throws ClassNotFoundException {
        Class c=Class.forName("A");

        //判断该类是否有@myAnnotation
        boolean ishave=c.isAnnotationPresent(MyAnnotation.class);
        System.out.println(ishave);

        //取得注解上的信息
        MyAnnotation myAnno= (MyAnnotation) c.getAnnotation(MyAnnotation.class);
        System.out.println(myAnno.name());


        //其他用法：判断被注解内容是否合理，
        //如某个类上是否有name这个变量：通过反射来判断1.是否有注解2.是否有该变量


    }
}

@MyAnnotation(name = "admin")
class A{
}
```

## 数据结构

- 类型

```java
/*
Collection接口
	集合是一个容器，存放对象的引用(基本类型会自动装箱
	集合用数据结构实现
	存放的是Object类型引用且同一个集合可以存放不同的引用类型
Collection的子接口
	Set接口。

 */

/*
Vector：动态括容数据，线程安全，效率低点
ArrayList：动态扩容数组，线程不安全，效率高点
LinkedList：双向链表，线程不安全

Stack：栈，Vector子类
Queue:队列
PriorityQueue:优先队列
              存放引用数据类型要自定义比较器Comparator
HashSet：底层Hash表。存储元素等于HashMap的key部分，无序不重复(只有key，没有value
        不允许重复的值(所以要重写equals和hashCode方法
TreeSet：底层红黑树存储了TreeMap的key部分，有序不重复（只有key，没有value
*/

/*
Map接口
	数据以key-value形式存储
*/

/*
Hashtable:哈希表，线程安全
HashMap：哈希表，线程不安全
        不允许重复的key
        链地址法处理哈希冲突：位桶+链表+红黑树

TreeMap：二叉排序树，按照key排序

Properties：HashMap，线程安全，key和value只能是String		
	没错就是配置文件.properties相关的那个

 */
```

- 迭代器

```java
 //如
		Collection ct= new TreeSet();
        ct.add(1);
        ct.add(3);
        ct.add(2);

        //获取迭代器
        Iterator it= ct.iterator();

        //遍历
        while (it.hasNext()){
            System.out.println(it.next());
        }
```

- 遍历Map

```java
/*
2种方法：
    把Map的key转化为Set
    把Map的key和value转化为Set
 */
HashMap<Integer,String> mes=new HashMap<Integer,String>();
mes.put(01,"Ricardo");
mes.put(02,"
//01通过可以变成set(不能是HashSet
Set<Integer> keys=mes.keySet();
Iterator<Integer> it= keys.iter
//while和foreach都行
while(it.hasNext()){
    String str=mes.get(it.next());
}
for(Integer key:keys){
    String str1=mes.get(it.next());

//02用entry方法
Set<Map .Entry<Integer,String >> set=mes.entr
for(Map.Entry<Integer,String> elem:set){
    Integer a=elem.getKey();
    String str=elem.getValue();
}
```

## 日期数字格式化

```java
Date date=new Date();

//专门负责格式化(年 月 日 日 分 秒 毫秒
SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
System.out.println(sdf.format(date));//2022-03-19 10:34:37 137


//可以用来获取当前的时间
SimpleDateFormat sdf1=new SimpleDateFormat("yy");
System.out.println(sdf1.format(date));//22

//反过来用字符串创建Date对象
String str="2021-07-21 12:12:12 333";
SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
Date date2=sdf2.parse(str);
System.out.println(date2.toString());//Wed Jul 21 12:12:12 CST 2021


/*
返回的是字符串,传递进去的不能是字符串，是double
 */
//逗号隔开，保留2位小数
DecimalFormat df=new DecimalFormat("###,###.##");
//保留4位小数，不够补0
DecimalFormat df1=new DecimalFormat(".0000");

double a=13254.173;

System.out.println(df.format(a));//13,254.17
System.out.println(df1.format(a));//13254.1730
```



## 反射

- 能够分析类能力的程序称为反射
- 作用
  - 在运行时分析类
  - 在运行时检查对象，如编写一个适合所有类的同String()方法
  - 实现泛型数组操作代码（泛型代码
  - 利用Method对象，类似于C++的函数指针

- 利用Class分析类

```java
Constructor getConstructor()//构造方法
    
//公共的，包括从父类继承的
Field getField()//字段
Method getMethod()//方法
    
//所有
Field [] getDeclaredFields()//所有字段。（父类的？）
Method[] getDeclareMethods()//类或接口的所有方法。但不包括从父类继承的
    
int getModifiers()//返回一个整数，描述上面3个的修饰符。用Modifier类可以分析这个返回值
```

- 编写泛型代码
  - 待补充

- Class
  - 保存了Java对象运行时的类型信息的类
  - 相应方法

```java
//获取一个Class的三种方法
//jvm只加载一份字节码文件

//1.从对象获取Class。不适用于类
Class c1=str.getClass();

//2.从类或任意类型获取Class。不适用于对象
Class c2=String.class;
Class c3=int.class;

//3.由类名获取Class。Class.forName(String className);
//作用是让jvm加载相应的类，在加载类时会执行类中的静态代码块
//并返回对应的Class类型
Class c4=Class.forName("java.lang.String");

//创建对象
Object o=c1.getConstructor().newInstance();//怎样拿到有参的呢

//有参构造
Constructor con=c1.getConstructor(String.class);
Object o1=con.newInstance("Hello World");
```

- 获取类关联的数据文件。(放在jar包)
  - 如图像和声音文件
  - 包含消息字符串、按键按钮的文本文件

```java
Class c1=Test.class;
URL url=cl.getResource("about.txt");
```

- Filed

```java
Class c1=harry.getClass();
Filed f1=c1.getDeclaredFiled();
f1.setAccessible(true);//设置后可以访问。即使是私有。但是有时候还是不允许访问呢，则抛出异常
Object obj=f1.get(harry);//访问
f1.set(f1,"jack");//修改

int i=f1.getModifiers();
String Modi= Modifier.toString(i);
System.out.println(Modi);//获取修饰符
```

- Method

```java
public Object invoke(Object obj,Object... args) //调用方法  
//获取方法
Method m1=Employee.class.getMethod("addInt",int.class,int.class);//方法名和参数
m1.invoke(harry,2,3);//对象和参数。毕竟方法是从类获得，调用还得依靠具体对象

Method m2=Employee.class.getConstructor(long.class,String.class);//参数
Object obj=m2.newInstance(2,"tom");
```

# ==JDBC==

## 步骤

```java
//注册驱动->告诉Java程序要连接的数据库品牌

//获取连接->Jvm进程和数据库进程之间的通道打开。使用后要关闭

//获取数据库操作对象->专门执行SQL语句的对象

//执行SQL语句

//处理查询结果集

//释放资源

```

## mysql中的Driver类

```java
/*
这个类本身就是com.mysql.cj.jdbc.Driver()。只要static代码块执行就相当于创建了自己类的对象，并注册驱动
*/
public class Driver extends NonRegisteringDriver implements java.sql.Driver{
    static{
       try{
           java.sql.DriverManager.registerDriver(new Driver());
       } catch(SQLException E){
           throw new RuntimeException("Can't register driver!");
       }
    }
    ...
}


//注册驱动public static void registerDriver(Driver driver)

/*
第一种
DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
*/

//最常用。字符串参数，可以写到配置文件中
Class.forName("com.mysql.cj.jdbc.Driver");
    
```

## ResultSet

```java
ResultSet rs=stmt.executeQuery(sql);//类似于二维数组
/*
boolean next()。执行一次后指向下一行。若该行有效则返回true，否则false
所以配合while(rs.next()){}就很有用
*/
//rs.next();//一开始迭代器指向第一行之前。执行后指向第一行

while(rs.next()){
   
    //getString()无论在数据库是什么数据类型，都转化为String返回
    /*
    String getString(int columnIndex)
    JDBC中所有下标以1开始
    */
    String id=rs.getString(1);
    String name=rs.getString(2);
    String sno=rs.getString(3);
    String age=rs.getString(4);
    
    /*
    String getString(String columnLabel)
    以列名返回值。程序更加健壮。以下标的话万一顺序乱了呢
    若查询时对列名取了别名，则应该使用别名作为参数，否则报错
    select id as ID,name from student		//ID
    rs.getString("ID");
    */
    String id=rs.getString("id");
    String name=rs.getString("name");
    String sno=rs.getString("sno");
    String age=rs.getString("age");
    
    //其他类型。getInt(),getDouble()...
    int age=rs.getInt("age");
    
}
```

## mysql.properties

```java
driver=com.mysql.cj.jdbc.Driver
url=jdbc:mysql://127.0.0.1:3306/beimysql
user=root
password=beiyuan3721
```



## MySQL.java

- MySQL.java

```java
public class MySQL {
    public void connection(){
        Connection con=null;//放在外面为了在finally中可以关闭
        Statement stmt=null;
        ResultSet rs=null;

        ResourceBundle bundle=ResourceBundle.getBundle("mysql");//src为根路径


        String dirver=bundle.getString("driver");
        String url=bundle.getString("url");
        String user=bundle.getString("user");
        String password=bundle.getString("password");


        try{
            //注册驱动
            Class.forName(dirver);
            //获取连接
            con=DriverManager.getConnection(url,user,password);
            //获取数据库操作对象
            stmt=con.createStatement();
            //执行SQL。可以不用分号结尾。加也可以
            String sql="select * from student";
            rs=stmt.executeQuery(sql);
            //处理结果集
            while (rs.next()){
                System.out.println(rs.getString("name"));
                System.out.println(rs.getInt("age"));
            }
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }finally {
            //释放资源

            if(rs!=null){//先释放小的
                try{
                    rs.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(stmt!=null){
                try{
                    stmt.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(con!=null){
                try{
                    con.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }

    }
}
```

## SQL注入

- 用户登录

```java
//name和password可以用Map存储

//密码为name' or '1'=1'	也能登录成功		//name是用户名

//获取数据库操作对象
stmt=con.createStatement();
//执行SQL。SQL中字符串要用单引号括起来
String sql="select * from student where name = '"+name+"' and password = '"+password+"'";
rs=stmt.executeQuery(sql);
//处理结果集
if(rs.next()){
    loginSuccess=true;
}
```

- 本质
  - 用户的输入中含有SQL关键字，参与了SQL的编译过程，改变了语义

## 解决SQL注入

- 用Statement的子类,预编译的数据库操作对象PreparedStatement //有d
- 原理：占位符方式，预先对SQL语句的框架进行编译，然后再传参数，参数不参与编译

```java
PreparedStatement ps=null;
//得先传入SQL语句框架
String sql="select * from student where name=? and password =?";//用?作为占位符
ps=con.prepareStatement(sql);//无d

//传值。下标从1开始
ps.setString(1,name);//select * from student where name= 'lisi' and password ='lisi01'
ps.setString(2,password);
//若是ps.setInt(3,100)//则不会加''单引号 select * from student where sno=100

rs=ps.executeQuery();
//处理结果集
if(rs.next()){
    loginSuccess=true;
}
```

- 也可以用填值的方式来增删改查

## Statement和PreparedStatement

- SQL注入

  - Statement存在SQL注入问题

  - 而PreparedStatement可以解决

- 执行效率

  - mysql数据库中，若本条SQL语句和上一条SQL语句完全一样(就连空格等位置也一样)，则不会编译，而是直接执行

  - Statement编译一次执行一次，因为不断有新的name和password导致SQL语句不一样了。

  - PreparedStatement中的SQL是模板，不会改变，所以有新的name和password也可以不用编译，而直接执行。编译一次可执行多次。效率略高

- 安全
  - PreparedStatement会在编写代码时作安全检查。如ps.setString()只能传字符串，不能是其他的。
  - 而Statement直到执行时才可能报错
- 综上所述。大多数情况使用PreparedStatement，极少数情况使用Statement

## 什么时候 使用Statement

- 要求进行SQL语句拼接

```java
select * from student order by sno desc;
改为降序
select * from student order by sno asc;

//如果用PreparedStatement。则会带单引号''
String order="desc"
String sql="select * from student order by sno ?";
ps=con.prepareStatement(sql);
ps.setString(1,order);//会变成select * from student order by sno 'desc'	不合符要求。报错
rs=ps.executeQuery();

//改为
String order="asc";
stmt=con.createStatement();
String sql="select * from student order by sno"+order;
rs=stmt.executeQuery(sql);
```

## 事务

- 自动提交

```java
JDBC中的事务是自动提交的。每执行一条DML（数据库 操作语言）语句都提交一次。
但是实际业务中
    一个功能是多条DML语句联合才能完成
    必须保证在同一个事务中，同时成功或同时失败
```

- 事务控制
  - con.setAutoCommit(false);//取消自动提交
  - con.commit();//手动提交
  - con.rollback();//事务回滚

```java
public void connection(){
        Connection con=null;//放在外面为了在finally中可以关闭
        PreparedStatement ps=null;
        ResultSet rs=null;

        ResourceBundle bundle=ResourceBundle.getBundle("mysql");//src为根路径


        String dirver=bundle.getString("driver");
        String url=bundle.getString("url");
        String user=bundle.getString("user");
        String password=bundle.getString("password");


        try{
            //注册驱动
            Class.forName(dirver);
            //获取连接
            con= DriverManager.getConnection(url,user,password);
            con.setAutoCommit(false);//取消自动提交
            //获取数据库操作对象
            String sql="update student set sno=? where name=?";
            ps=con.prepareStatement(sql);

            //传值
            ps.setInt(1,109);//如果数据库中该类型是数字而不是字符串，则不能使用setString(),用setInt(),
            ps.setString(2,"lisi");
            //执行SQL。
            ps.execute();

            //制造异常，然后事务会回滚
//            String s=null;
//            s.toString();
            con.commit();//手动提交

        }catch (Exception e){
            if(con!=null){//先释放小的
                try{
                    con.rollback();//事务回滚
                }catch (SQLException e1){
                    e1.printStackTrace();
                }
                }
                e.printStackTrace();
            }finally {
                if(ps!=null){
                    try{
                        con.close();
                    }catch (SQLException e){
                        e.printStackTrace();
                    }
                }

                if(con!=null){
                    try{
                        con.close();
                    }catch (SQLException e){
                        e.printStackTrace();
                    }
                }
            }

      }
```

## SQL的执行方式

```java
boolean execute()		//最简单。是否成功
ResultSet executeQuery()	//只有这个能拿到结果集
int executeUpdate()			//更新的数量
```

## 锁

- 悲观锁（行级锁）
  - 当前事务还没结束，其他事务不能修改锁住的记录
  - 在SQL语句后面加上for update即可
  - 不允许并发

```sql
select * from student where ... for update; --所有满足条件的记录都被锁住
```

- 乐观锁
  - 记录被修改后追加一个版本号
  - 若某个线程发现开始事务、提交事务记录的版本号不一致。则当前线程执行回滚
  - 允许并发

## 手写JDBC工具类

- ```java
  package Utils;
  public class JDBCl {
      //jdbc.properties还是放在src下。不然真的找不到了
      private static ResourceBundle bundle=ResourceBundle.getBundle("jdbc");
      
      private static String driver=bundle.getString("driver");
      private static String url=bundle.getString("url");
      private static String user=bundle.getString("user");
      private static String password=bundle.getString("password");
      
      static {
          try {
              Class.forName(driver);
          } catch (ClassNotFoundException e) {
              e.printStackTrace();
          }
      }
      
      public static Connection getConnection() throws SQLException {
          Connection con= DriverManager.getConnection(url, user, password);
          return con;
      }
  }
  
  //释放资源
  public static void close(Connection con, Statement ps, ResultSet rs){
          if(rs!=null){//先释放小的
              try{
                  rs.close();
              }catch (SQLException e){
                  e.printStackTrace();
              }
          }
          if(ps!=null){
              try{
                  ps.close();
              }catch (SQLException e){
                  e.printStackTrace();
              }
          }
  
          if(con!=null){
              try{
                  con.close();
              }catch (SQLException e){
                  e.printStackTrace();
              }
          }
  }
  ```

- 使用工具类

- ```java
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
  
        try {
            con= JDBC.getConnection();
            //String sql="select * from student order by sno ?";
  		  //ps=con.prepareStatement(sql);
            
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBC.close(con,ps,rs);
            //没有结果集的话JDBC.close(con,ps,null);
        }
    }
  ```