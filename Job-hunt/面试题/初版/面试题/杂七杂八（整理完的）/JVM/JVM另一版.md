# [JVM（一）虚拟机基础](https://www.cnblogs.com/mouren/p/14321691.html)

> JVM 全称Java Virtual Machine，也就是我们耳熟能详的Java 虚拟机。它能识别.class 后缀的文件，并且能够解析它的指令，最终调用操作系统上的函数，完成我们想要的操作。

### Java执行流程

 当我们编写完程序之后文件就是`.Java`文件通过Javac指令编译之后就是`.class`文件，之后就是由JVM将类加载到方法区中，执行引擎执行这些字节码。

 过程如下：`Java 文件->编译器>字节码->JVM->机器码`。

### JVM的跨平台、跨语言性

- #### 跨平台

   Java官网提供了各个系统版本的JDK，我们根据系统下载对应版本的JDK就可以将我们写的类运行在不同的操作系统上。

   官网直达：https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html

![img](https://img2020.cnblogs.com/blog/1360865/202101/1360865-20210124175546027-1807619405.png)

- #### 跨语言

   因为JVM是通过识别字节码（.class文件）的方式进行运行的，所以只要其他语言如果也可以编译为字节码文件则也可以在JVM上运行，例如：Groovy、Kotlin等等一些语言。所以JVM也是一定程度上奠定了Java强大的生态圈。

![img](https://img2020.cnblogs.com/blog/1360865/202101/1360865-20210124175601315-280060357.png)

### JVM相关实现

 JVM规范的存在提供了一些JVM的具体的实现。

- ##### Hotspot

  使用最多的Java虚拟机，通过javac -version的命令可以看到。

- ##### Jrocket

  原隶属于BEA公司，号称最快的JVM，后被Oracle所收购，与Hotspot所合并。

- ##### J9

  IBM公司的JVM，主要用于自家产品上（IBM WebSphere 和IBM 的AIX 平台）。

- ##### TaobaoVM

  淘宝根据HotSpot为自身定制的JVM，目前阿里、天猫都在使用。

- ##### zing

  属于zual公司，很牛，但是很贵。它的垃圾回收速度非常快，之后它的垃圾回收算法被HotSpot所吸收形成了现在的ZGC。

### JVM 整体知识模块

 从下图中可以看到JVM的知识模块是比较多的，但是基本上都会与内存结构牵扯到一些关系，所以内存结构是非常重要的一块知识点。
![img](https://img2020.cnblogs.com/blog/1360865/202101/1360865-20210124175616666-593517833.png)

### JVM 内存区

![img](https://img2020.cnblogs.com/blog/1360865/202101/1360865-20210127222109868-1391267944.png)

 class文件初始化时会将初始化的数据存放到方法区和堆中，当调用方法的时候会生成一个线程，线程中会虚拟出一块内存，通过执行引擎执行指令集，操作数据进行入栈出栈以及程序计数器计数，最后返回地址，从而完成一个方法的调用。

- #### 运行时数据区

  - ##### 线程共享区

    - 方法区（规范，逻辑划分）

      方法区是JVM中的一种规范。JDK 1.7 是以永久代实现方法区，JDK 1.8是以元空间实现方法区的，所以存在不同的叫法。此区域是在class加载的时候就已经将数据加载到方法区中了。

      `运行时常量池`：主要存放引用地址。即在类加载的时候会存在**将符号替换为直接引用**的一部，其实就是将**对象 a = new 对象()**转换为**对象 a = 地址**，其地址就是存放在运行时常量池的。

    - 堆

      用于存放对象的。

  - ##### 线程私有区

    - 线程
      ![img](https://img2020.cnblogs.com/blog/1360865/202101/1360865-20210127222204540-1777688878.png)

      此处每一个线程之间都是独立的、互不干扰的。每一个线程中都会存在三个大块—虚拟机栈、本地方法栈、程序计数器。

      `虚拟机栈`：主要核心为一个个栈帧，遵循栈的数据结构，先进后出。通过**-Xss**可以设置每个虚拟机栈的大小，一般默认为1MB(不同的操作系统默认设置的大小不一样)。每一个方法都会创建一个栈帧，直至方法执行结束则会出栈。

       **局部变量**：主要存放八大基本数据类型以及对象的引用。

       **操作数栈**：执行引擎的一个工作区，通过执行引擎执行指令集对数据进行入栈、出栈、计算等等。

       **动态连接**：后期文章。

       **方法返回**：将最终结果返回。

      `本地方法`：存放native所修饰的方法，因为这些方法都是由C/C++所实现的，可以通过此方法来操作系统。

      `程序计数器`：单独划分的一块内存，主要存放当前执行引擎所执行的地址。因为多线程的情况所以需要记录当前线程所执行的地址/行号，类似于CPU时间片轮转，它的内部也存在一个类似程序计数器。

       此区域是永远不会出现栈溢出异常。

- #### 直接内存

  虚拟机内存以外的的内存，NIO可以操作此区域的内存。

  UnSafe类也可以，Ehcache就是基于此类实现的，但不建议自己使用。

# [JVM（二）内存区域](https://www.cnblogs.com/mouren/p/14337330.html)

### 一、内存处理流程

- #### 申请内存

   通过配置参数或者默认的参数向操作系统申请内存，根据内存的大小找到内存段的起始地址和结束地址分配给JVM，由JVM进行内部分配。

- #### 初始化运行数据区

   根据参数进行堆、方法区、栈的分配。

- #### 类加载（后面的文章会详解）

   将class、常量、静态属性放到方法区，对象放在堆中。

- #### 创建对象

   创建线程，分配内存给虚拟机栈，虚拟机栈（操作数栈）创建对象，局部变量表存储对象引用（栈），对象放在堆中。

### 二、堆空间分代划分

#### ![img](https://img2020.cnblogs.com/blog/1360865/202101/1360865-20210127221517333-1298584712.png)

 堆总被分为两个部分：新生代和老年代，其中新生代中又被分为Eden区和Survivor区，Survivor区由Form Survivor和To Survivor组成（具体的GC、对象分配方面会在后面的文章讲到）。

### 三、栈优化

 在栈帧中一般来说两个栈帧是不会相互有关系的，都是独立存在的。但是在某些情况下，会使两个独立的栈帧会有一个共享的数据区，此行为为栈的优化。

```java
public class JVMTest01 {
    public int add(int num){
           return num + 2;
    }
    public static void main(String[] args) {
        JVMTest01 jvmTest01 = new JVMTest01();
        jvmTest01.add(50); //此时50就是add栈帧和main栈帧的共享数据
    }
}
```

![img](https://img2020.cnblogs.com/blog/1360865/202101/1360865-20210127221643117-2136117746.png)
​ 此时50就是main方法操作数栈的数据，同时也是add栈帧的局部变量表中的数据。此情况为栈优化。

### 三、JHSDB可视化JVM

[JHSDB详情](https://www.cnblogs.com/mouren/p/14339663.html)

### 四、内存溢出OOM

- #### 堆异常

   内存溢出：申请的内存空间超过了堆最大的内存空间。

   若为内存溢出，则可以通过`-Xms`和`-Xmx`参数来调整堆内存的大小。如果不是就是代码写的有问题，对象的生命周期太长或者存储结构上的设计不合理。

- #### 方法区异常

   运行时常量池溢出。

   方法中的class对象占用的内存太多，超过了设置的内存大小。

- #### 栈异常

   在Hotspot版本中的vm是不可以给栈设置大小，只能设置栈中的每个线程占用内存的大小。

   每调用一个方法都会创建一个栈帧，所以一般来说出现的栈的oom基本上就是递归的问题。

   **同时要注意，栈区的空间JVM 没有办法去限制的，因为JVM 在运行过程中会有线程不断的运行，没办法限制，所以只限制单个虚拟机栈的大小。**

- #### 直接内存异常

   直接内存的容量可以通过`-XX:MaxDirectMemorySize`来设置（默认与堆内存最大值一样），所以也会出现OOM 异常；
  ​ 由直接内存导致的内存溢出，一个比较明显的特征是在HeapDump 文件中不会看见有什么明显的异常情况，如果发生了OOM，同时Dump 文件很小，可以考虑重点排查下直接内存方面的原因。

### 五、常量池

- #### class常量池(静态常量池)

   主要存放字面量和符号引用。

   **字面量**：就是变量的值，例如：`String str = "Hello"` ,'Hello'就是str的字面量。同理，int、double等等都是这样。

   **符号引用**：就是在类被编译的时候会被编译成一个class字节码文件，如果类中存在其他类的方法的在编译的时候是不指到其他类的内存地址的，那么就用一个占位符（符号）来代替这个其他类，在类加载的时候就会把符号替换类的引用（内存地址）。

   例如：Student类中有调用Tool类中的一个方法，在编译的时候就不知道这个Tool类的引用（内存地址），那么就会用com.xxx.Tool来代替。之后在类被加载的时候就会被替换成具体的引用（内存地址）。（因为这个对象可能还没创建？

- #### 运行时常量池

   在类被加载的时候会把符号转换为具体的引用（内存地址）时，会把引用（内存地址）保存到这个区域。

   在JDK1.7 被放在了堆中，但是理论中还是属于方法区（JVM规范中）。

   JDK1.8 之后由元空间来代替的永久代实现方法区，但是理论上还是隶属于方法区。

- #### 字符串常量池

   字符串常量池在网上是比较有争议的一个存在，实际在官方文档或者JVM规范中并没有这个概念。

   在JDK1.8中是存放在堆中的，且于Sting类有很大的关系。这块内存的存在使Sting可以被更高效的使用，从而提升了的整体性能。

   **String (JDK 1.8)**:

![img](https://img2020.cnblogs.com/blog/1360865/202101/1360865-20210127221839195-441780560.png)

 在String源码中可以看到String是被final所修饰的，它的底层为char数组同时也是被final所修饰。因此可以知道String一旦被赋值后就不可被更改，那么这样做的话会有什么好处呢？

 （1）保证了String对象的安全性。

 （2）保证它的hash值不会被频繁变动，使得HashMap容器才能实现k-v的缓存实现。

 （3）可以实现字符串常量。

 **String创建方式**：

```java
String str = "abc";
//此方法创建的话，首先会去常量池中查找是否存在abc这个常量，有的话就直接返回引用，没有就创建。

String str1 = new String("abc");
//这个方法比起上面，在堆中多了一个String对象，而这个String对象则指向常量池中的abc常量，所以str1首先指向的是堆中String对象而不是常量池中的abc。

public class Student {
    String name;
    String gender;
   public static void main(String[] args) {
        Student stu = new Student();
        stu.setName("小明");
    }
}
//此地的字符不会被存放在字符串常量池中，只会被存在的堆中。

String str2 = "a" + "b" + "c";
//这个在目前的主流编译器在被编译的时候会被优化成 String str2 = "abc"。以前则会生成 a、b、c、ab、abc这几个对象。

String str1 = new String("abc").intern();
//在对象中调用intern方法，会和 String str1 = "abc" 等效。且直接放入常量池
```

# [JHSDB使用](https://www.cnblogs.com/mouren/p/14339663.html)

> ```mipsasm
> JHSDB 是一款基于服务性代理实现的进程外调试工具。服务性代理是HotSpot 虚拟机中一组用于映射Java 虚拟机运行信息的，主要基于Java 语言实现的API 集合。
> ```

### 一、打开JHSDB

jdk9之后不用

 ![img](https://img2020.cnblogs.com/blog/1360865/202101/1360865-20210128142232781-62216795.png)

 打开JDK所在目录（一般默认装在c盘），保证在JDK的bin目录下也存在`sawindbg.dll`,没有的话可以从jre目录下面复制一份过来，只要保证两个文件夹都有就行了。

![img](https://img2020.cnblogs.com/blog/1360865/202101/1360865-20210128142405511-694858009.png)

 在JDK的lib目录下`Shift+右键`点击`在此处打开Powershell窗口`。

![img](https://img2020.cnblogs.com/blog/1360865/202101/1360865-20210128142427316-2023778196.png)

 在命令行中输入以下代码就可以开启了（可能需要等待个三秒左右）。

```mipsasm
java -cp .\sa-jdi.jar sun.jvm.hotspot.HSDB
```

输入

jhsdb hsdb      即可

### 二、设置参数

 代码如下：

```java
public class Student {
    private String name;
    private String gender;
    private int age;

    public String getName() {        return name;    }
    public void setName(String name) {        this.name = name;    }
    public String getGender() {        return gender;    }
    public void setGender(String gender) {        this.gender = gender;    }
    public int getAge() {        return age;    }
    public void setAge(int age) {        this.age = age;    }

public static void main(String[] args) throws Exception {
       
        //启动程序添加JVM参数 -XX:+UseConcMarkSweepGC -XX:-UseCompressedOops
        Student student = new Student();
        student.setName("王二狗");
        student.setGender("男");
        student.setAge(35);

        for (int i = 0; i < 15; i++) {
            System.gc();//主动发起 GC 15次
        }

        Student student1 = new Student();
        student1.setName("张三丫");
        student1.setGender("女");
        student1.setAge(25);

        Thread.sleep(Long.MAX_VALUE);
    }
}    
```

##### 启动程序添加JVM参数：

官方JVM参数详解：https://docs.oracle.com/javase/8/docs/technotes/tools/unix/java.html

```undefined

```

![img](https://img2020.cnblogs.com/blog/1360865/202101/1360865-20210128142641813-40426512.png)

![img](https://img2020.cnblogs.com/blog/1360865/202101/1360865-20210128142656395-1955097680.png)
![img](https://img2020.cnblogs.com/blog/1360865/202101/1360865-20210128142850371-1001129053.png)

### 三、监听进程

 打开`cmd`或者之前已经打开的`Powershell`输入`jps`指令回车，可以看到启动的进程ID。
![img](https://img2020.cnblogs.com/blog/1360865/202101/1360865-20210128142904123-429531107.png)

 下一步~~
![img](https://img2020.cnblogs.com/blog/1360865/202101/1360865-20210128142956360-676832213.png)

 弹出以下页面表示成功。
![img](https://img2020.cnblogs.com/blog/1360865/202101/1360865-20210128143030899-63392159.png)

### 四、相关参数查看

![img](https://img2020.cnblogs.com/blog/1360865/202101/1360865-20210128143107763-1205213097.png)

 选中`main`线程，选择`Tools`中的`Heap Parameters`可以查看堆中的信息。在这可以看到堆的分区以及对应的内存地址范围。

![img](https://img2020.cnblogs.com/blog/1360865/202101/1360865-20210128143125740-677850718.png)

 选择`Tools`中的`Object Histogram`可以看到方法区里的所有class，这里的查询是按照全局限定名来查询的，双击可以看到创建的两个对象。

 ![img](https://img2020.cnblogs.com/blog/1360865/202101/1360865-20210128143146581-1346291760.png)



 随便选择一个对象，点击`Inspect`可以看到对象的相关属性，此时就可以看到之前创建的对象了。

![img](https://img2020.cnblogs.com/blog/1360865/202101/1360865-20210128143204591-2091292214.png)

 选中main线程，点击StackMemory可以查询到栈中的信息，从这里也可以看到本地方法栈和虚拟机栈在Hotspot合二为一了。

# [JVM（三）对象及引用](https://www.cnblogs.com/mouren/p/14349215.html)

### 一、对象创建过程

- #### 检查加载

   检查类是否 以及被加载（是否存在对应类型的class）。

- #### 分配内存

  - ##### 内存划分方式

    - **指针碰撞**

       如果堆中的内存是绝对规整的，那么就会按照对象的大小直接进行内存的划分，此情况称为**指针碰撞**。

    - **空闲列表**

       如果堆中的内存是碎片化的、不规整的，那么JVM就不能进行指针碰撞，JVM需要维护一个列表，记录哪些内存地址被使用，哪些没有被使用，在给对象分配内存的时候只需要找到和对象一致大小的连续内存地址进行划分。此情况称为**空闲列表**。

      **Tip**：至于什么时候采用那种方案由JVM来决定。如果是Serial、ParNew 等带有压缩的整理的垃圾回收器的话，系统采用的是指针碰撞，既简单又高效。

  - ##### 解决并发安全

     在给对象划分内存的时候同时还需要考虑的问题是创建对象在虚拟机中时非常的频繁的那么就会存在并发的相关问题，当给A对象划分的时候，B也在划分这个内存那么就存在了问题，在JVM中有以下两种解决办法：

    - CAS加失败重试
      ![img](https://img2020.cnblogs.com/blog/1360865/202101/1360865-20210130152747356-1598196522.png)

      当在进行内存划分时，对当前的内存地址进值行一个保存（当前地址是没有其他数据），之后进行一些数据的预处理后再取当前内存地址的值，与之前取到的old值进行比较如果相等则直接分配给当前对象，如果不一致那么就重新进行一轮（当前如果当前内存地址已经存在了对象就会去找其他的内存地址。）

    - TLAB(分配缓存)

       这个是比较简单粗暴的，预先给每个线程预先在堆中划分一块私有内存给当前的线程使用，那么每个线程就会拥有一块独有的内存，就可以直接给对象分配。当内存被使用光之后，可以重新申请一块内存。此方式效率是杠杠的。

      `-XX:+UseTLAB`:允许在年轻代空间中使用线程本地分配块（TLAB）。默认情况下启用此选项。要禁用TLAB，请指定`-XX:-UseTLAB`。

- #### 内存空间初始化

   对对象中的属性进行初始化（默认值），这一步保证了对象的实例属性可以不用赋值就可以直接使用。

- #### 设置

   接下来话就是对对象进行必要的一些设置，比如这个对象是属于哪个类的、对象的哈希码、对象的GC分代年龄等等，这些信息都是存储在对象头中的。

![img](https://img2020.cnblogs.com/blog/1360865/202101/1360865-20210130152809829-1127773706.png)

 在Hotspot VM中，对象在内存中的布局分为三块：：对象头（Header）、实例数据（Instance Data）和对齐填充（Padding）。

 **对象头**主要分为两个部分，第一个部分主要存储对象自身的运行时数据。如：哈希码、GC分代年龄、锁状态标识等等。

 **对象头**的另一个部分主要指向这个对象是属于哪个类（class）的。如果对象是数组的话那么还会记录数组的长度信息。

 **实例数据**则是对象中的真实的有效的数据，各个类型字段的内容。

 **对齐填充**的话不是一定存在的因为一个对象的大小必须是8字节或者8的倍数，如果这个对象不符合这个条件的话那么就会进行补充使得对象可以符合这个条件。

- #### 对象初始化

  到现在为止在JVM视角来看的话一个新的对象已经产生了，但是从代码上来看的话，对象才刚刚开始创建，所以接下来的话就是调用构造方法，这样一个对象才被真正的创建了出来。

### 二、对象定位

 一般来说我们都是在栈中根据对象的引用（reference）来操作对象的，那虚拟机到底是怎么定位到对象的？在

- #### 句柄

![img](https://img2020.cnblogs.com/blog/1360865/202101/1360865-20210130152832126-688289427.png)

 使用句柄的话，会在堆中划出一块句柄池内存区域，而引用就是指向句柄池中的句柄，每一个句柄包括了对象实例数据与类型的地址信息。这样做的话就会比较稳定，当对象被移动的时候就只需要更改句柄的指针即可，而引用就还是指向这个句柄。

- #### 直接指针（Hotspot）

   直接指针的就是引用直接指向实例对象，这样做的话就是一个字，快！它比句柄的话减少了一次指针的定位开销。

### 三、什么是垃圾

 在堆中存放的几乎都是实例对象，随着程序的运行，堆中的对象会越来越多那么垃圾回收器就需要对一些没有用的对象（垃圾）进行回收以确保堆的内存可以被极大的利用。

- #### 手动内存

   在C/C++中的话就需要手动进行申请内存，之后还要手动回收内存。而手动回收内存的话就会存在两个问题：

   （1）、忘记回收

   （2）、多次回收

- #### 自动内存

   自动回收的就比较爽，写代码就完全不用自己申请，回收内存啥的了。

### 四、对象存活的判断

 既然是自动回收的话，那么JVM就需要判断什么可以回收什么不可回收。

- #### 引用计数法

   在每个对象中添加一个引用计数器，每有一个地方引用那么就会加一，当每有一个引用被断开就减一，如果这个计数器为0的时候表示这个对象可以被回收了。

   但是有一个情况会导致对象无法永远无法被回收，当两个对象互相引用的时候那么这两个对象的计数器都会用于存在1，那么就需要其他的手段来解决这个办法。

   Python的垃圾回收就是采用的这个方案+其他手端来做的。

- #### 可达性分析（根可达）

   **这块面试问的还是比较多的**。

   此方法就是Hotspot VM目前所采用的，这个算法的话就是采用“GC Root”来确定对象的头目，根据这个头目来向下搜索，搜索过的路径叫做**引用链**，只要对象在不在引用链上那么就是没用的垃圾，可以被回收。

  这个不形成环路就不会循环引用吧？

   **GC Root**主要包块以下几种：

  - 虚拟机栈里的局部变量表所引用的对象；各个现场被调用方法堆栈中使用到的参数、局部变量、临时变量等。
  - 方法区里的静态属性所引用的对象。
  - 方法区例的常量所引用的对象。
  - 本地方法栈Native方法（JNI）所引用的对象。
  - JVM 的内部引用（class 对象、异常对象NullPointException、OutofMemoryError，系统类加载器）。
  - 所有被同步锁(synchronized 关键)持有的对象。
  - JVM 内部的JMXBean、JVMTI 中注册的回调、本地代码缓存等。
  - JVM 实现中的“临时性”对象，跨代引用的对象

### 五、class回收条件

 class回收的话条件比较苛刻，必须要满足以下几个条件，而且还不是肯定被回收，会被设置的JVM参数所影响。

 1、这个类没有任何实例。

 2、加载这个类的类加载器（ClassLoader）被回收了。

 3、这个类没有被任何地方所引用，反射也没法访问这个类的那种。

 4、`-Xnoclassgc`参数

![img](https://img2020.cnblogs.com/blog/1360865/202101/1360865-20210130152856786-526367073.png)

### 六、Finalize方法

 可以拯救被回收的对象，但是只能拯救一次而且优先级比较低。

 目前很多地方不推荐使用，不多介绍。

### 七、各种引用

- #### 强引用

  `String str = new String("abc")` 这个代码里面的 "="，就属于强引用，就不会被GC回收掉。

- #### 软引用(SoftReference)

   当系统出现OOM异常的时候就会回收掉软引用的对象，以确保有足够的内存来运行程序。一般来说缓存会使用。

- #### 弱引用(WeakReference)

   当发生GC的时候就会被清除掉，比软还不行。基本上也是缓存会采用。

- #### 虚引用(PhantomReference)

   幽灵引用，最弱（随时会被回收掉）
  ​ 垃圾回收的时候收到一个通知，就是为了监控垃圾回收器是否正常工作。

### 八、对象分配原则

- #### 栈上分配

   **逃逸分析**:其实不是所有对象都是分配在堆中的，比如：调用参数传递到其他方法中，这种称之为方法逃逸。甚至还有可能被外部线程访问到，例如：赋值给其他线程中访问的变量，这个称之为线程逃逸。
  ​ 从不逃逸到方法逃逸到线程逃逸，称之为对象由低到高的不同逃逸程度。
  ​ 如果确定一个对象不会逃逸出线程之外，那么让对象在栈上分配内存可以提高JVM 的效率。

- #### 堆上分配

   堆在上一个文章中就说了，在堆中是分代的。
  ![img](https://img2020.cnblogs.com/blog/1360865/202101/1360865-20210130152716200-214317282.png)

  - ##### 对象优先Eden区分配

     一般来说所有创建的对象都会被优先分配在Eden区。

  - ##### 大对象直接老年代分配

     如果对象太大的话就被直接分配到老年代区，比如一个很长的字符串或者数量很多的数组。

     在写代码的时候就需要避免这些大的对象，如果在复制一个大对象，拿么开销就非常大。

     Hotspot 虚拟机提供了`-XX:PretenureSizeThreshold` 参数，指定大于该设置值的对象直接在老年代分配，这样做的目的就是避免在Eden 区及两个Survivor区之间来回复制，产生大量的内存复制操作。

  - ##### 长期对象存活老年代分配

     Hotspot VM中大多数的收集器都采用了分代收集来管理堆内存，在分配内存的时候就需要决定哪些对象放在新生代哪些放在老年代。

     在前面就说到每个对象的对象头中就存放了GC分代年龄，当Eden经过第一次Minor GC后仍然存活的对象就会被移动到Survivor区域，并且GC分代年龄加一，对象在Survivor区域每经过一次Minor GC都会加一，当它的年龄增加到一定程度(并发的垃圾回收器默认为15)，CMS 是6 时，就会被晋升到老年代中。

     `-XX:MaxTenuringThreshold`调整
    ![img](https://img2020.cnblogs.com/blog/1360865/202101/1360865-20210130152929101-628779038.png)

  - ##### 对象动态对象年龄判断

     当对象被移动到Survivor区的时候，当Survivor区中的所有对象大小超过Survivor区空间的阈值 的话就被直接被移动到老年代区。

  - ##### 空间分配担保

     在发生Minor GC 之前，虚拟机会先检查老年代最大可用的连续空间是否大于新生代所有对象总空间，如果这个条件成立，那么Minor GC 可以确保是安全的。如果不成立，则虚拟机会查看HandlePromotionFailure 设置值是否允许担保失败。如果允许，那么会继续检查老年代最大可用的连续空间是否大于历次晋升到老年代对象的平均大小，如果大于，将尝试着进行一次Minor GC，尽管这次Minor GC 是有风险的，如果担保失败则会进行一次Full GC；如果小于，或者HandlePromotionFailure 设置不允许冒险，那这时也要改为进行一次Full GC。

# [JVM（四）分代垃圾回收机制和垃圾回收算法](https://www.cnblogs.com/mouren/p/14361439.html)

### 一、什么是GC

 GC (Garbage Collection)垃圾回收，顾名思义就是专门回收垃圾的。，在C/C++中，我们需要用到内存的时候，需要先手动申明一下，使用完后又需要在手动回收一下，这两部非常麻烦而且还经常会出这个方面的问题。而这一切在Java中就已经被自动执行掉了，所以我们写代码的时候都不用再管这些无效的数据。

### 二、GC分类

 在目前主流的虚拟机中，大多都是根据**分代收集**的理论来进行设计的。因为在虚拟机中绝大部分的对象都是朝生夕死的，而熬过了多次的垃圾回收后的对象就越难被回收。所以前面的理论堆就被划分成了两个区域，**新生代**和**老年代**，前者主要存储那些朝生夕死的对象，后者存放难死的对象。

 1、 `新生代回收（Minor GC/Young GC）`:指只是进行新生代的回收。

 2、`老年代回收（Major GC/Old GC）`:指只是进行老年代的回收。目前只有 CMS 垃圾回收器会有这个单独的回收老年代的行为。 （Major GC 定义是比较混乱，有说指是老年代，有的说是做整个堆的收集，这个需要你根据别人的场景来定，没有固定的说法）

 3、`整堆回收（Full GC）`:收集整个 Java 堆和方法区(注意包含方法区)

### 三、垃圾回收算法

#### 1、复制算法(Copying)

 将一块内存区域进行对半分，当有一半的内存使用完时将还存活的对象放到另一半内存区域中，原来的内存区域进行回收，不用考虑内存碎片区域，只要按顺序分配内存就行。实现简单，运行高效。

 但是这样也有个缺点就是对内存的利用率只有50%，于是在JVM中就有了以下的解决办法：

##### Appel式回收

 Eden区的添加，一般来说的内存区域的分配为：Eden:80%，Survivor:20%(From 10%，To 10%)，当Survivor区不够用的时候，就需要老年代进行分配担保。

#### 2、标记-清除法(Mark-Sweep)

如根据可达性来标记

 算法分为“标记”和“清理”两个阶段：第一步扫描需要标记所有可以被回收的对象，第二遍扫描需要清理被第一步标记的对象，效率略低。因为需要大量的标记对象和清除所以回收效率是不复制算法的，如果大部分的对象是朝生夕死的那么标记的对象就会更多，效率会更低。

 它还有个主要问题就是会产生大量的内存碎片导致大对象无法进行存储，从而不得不提前触发其他的垃圾回收。

#### 3、标记-整理法(Mark-Compact )

 步骤与清除法步骤一致但是，它的第二步是整理标记之外的所有对象，将所有对象向前移动之后直接清除掉这些对象所在之外的内存区域。标记法**不会存在内存碎片**，但是效率是遍低的。

 整理法和清除法的主要区别就是一个是回收对象，一个整理对象，而移动对象还会需要暂停所有的业务线程后更新所有对象的引用（**直接指针需要调整**）。

### 四、JVM垃圾回收器

![img](https://img2020.cnblogs.com/blog/1360865/202102/1360865-20210202132246805-125632410.png)

#### 1、Serial/Serial Old

 JVM诞生初期所采用的垃圾回收器，单线程，独占式，适合单CPU。

 它只适合堆内存几十兆到几百兆，如果超过的这个内存的大小则会大大的降低回收效率，所以在目前很鸡肋。
![img](https://img2020.cnblogs.com/blog/1360865/202102/1360865-20210202131925759-1316103449.png)

#### Stop The World（**STW**）

 单线程进行垃圾回收时，必须暂停所有的工作线程，直到它回收结束。这个暂停称之为“Stop The World”,但是这种 STW 带来了恶劣的用户体验,例如:应用每运行一个小时就需要暂停响应 5 分。这个也是早期 JVM 和 java 被 C/C++ 语言诟病性能差的一个重要原因。所以 JVM 开发团队一直努力消除或降低 STW 的时间。

#### 2、Parallel/Parallel Old

 为了提高JVM的回收效率，从JDK 1.3开始，JVM使用了多线程的垃圾回收器，关注吞吐量的垃圾回收器，可以更高效的利用CPU时间，从而尽快完成程序的运算任务。

 所谓吞吐量就是 CPU 用于运行用户代码的时间与 CPU 总消耗时间的比值，即吞吐量=运行用户代码时间/（运行用户代码时间+垃圾收集时间），虚拟机总 共运行了 100 分钟，其中垃圾收集花掉 1 分钟，那吞吐量就是 99%。

 该垃圾回收器适合回收堆空间上百兆~几个G。

##### JVM参数设置

**JDK1.8** **默认就是以下组合**

`-XX:+UseParallelGC`新生代使用 Parallel Scavenge，老年代使用 Parallel Old

**-XX:MaxGCPauseMillis**

![img](https://img2020.cnblogs.com/blog/1360865/202102/1360865-20210202131956930-72860557.png)

不过不要异想天开地认为如果把这个参数的值设置得更小一点就能使得系统的垃圾收集速度变得更快，垃圾收集停顿时间缩短是以牺牲吞吐 量和新生代空间为代价换取的:系统把新生代调得小一些，收集 300MB 新生代肯定比收集 500MB 快，但这也直接导致垃圾收集发生得更频繁，原来 10 秒收集一次、每次停顿 100 毫秒，现在变成 5 秒收集一次、 每次停顿 70 毫秒。停顿时间的确在下降，但吞吐量也降下来了。

**-XX:GCTimeRatio**

`-XX:GCTimeRatio` 参数的值则应当是一个大于 0 小于 100 的整数，也就是垃圾收集时间占总时间的比率，相当于吞吐量的倒数。

例如：把此参数设置为 19, 那允许的最大垃圾收集时占用总时间的 5% (即 1/(1+19))， **默认值为** **99**，即允许最大 1% (即 1/(1+99))的垃圾收集时间由于与吞吐量关系密切，ParallelScavenge 是“吞吐量优先垃圾回收器”。

**-XX:+UseAdaptiveSizePolicy**

`-XX:+UseAdaptiveSizePolicy` （默认开启）。这是一个开关参数， 当这个参数被激活之后，就不需要人工指定新生代的大小(-Xmn)、Eden 与 Survivor 区的比例(`-XX:SurvivorRatio`)、 晋升老年代对象大小(`-XX:PretenureSizeThreshold`)等细节参数了，虚拟机会根据当前系统的运行情况收集性能监控信息，动态调整这些参数以提供最合适的停顿时间或者最大的吞吐量。

![img](https://img2020.cnblogs.com/blog/1360865/202102/1360865-20210202132012270-286078568.png)

#### 3、ParNew/CMS

##### ParNew

 多线的垃圾回收器与Parallel差不多，唯一的区别：多线程，多 CPU 的，停顿时间比 Serial 少。（在 JDK9 以后，把 ParNew 合并到了 CMS 了） 。

##### Concurrent Mark Sweep(CMS)

 ![img](https://img2020.cnblogs.com/blog/1360865/202102/1360865-20210202132024198-246460228.png)

 此类垃圾回收器是追求最短的回收停顿时间（STW）为目标的。目前还有是很大一部分的 Java 应用集中在互联网或者 B/S 系统的服务端上，这类应用比较重视服务的响应速度，希望停顿时间更短以提升用户的体验。

 Mark Sweep 从名字上可以看出来，这个回收器采用的是标记 - 清除法。而它的步骤比起前面的几个回收器都更麻烦些。

 整体过程分为 4 个步骤：

 **初始标记**：只标记与 GC Root 有直接关联的对象，这类的对象比较少，标记快。

 **并发标记**：标记与初始化标记的对象有关联的所有对象，这类的对象比较多所以采用的并发，与用户线程一起跑。

 **重新标记**：修正那些并发标记时候标记产生异动的对象标记，这块的时间比初始标记稍长一些，但是比起并发标记要快很多。

 **并发清除**：与用户线程一起运行，进行对象回收。

`-XX:+UseConcMarkSweepGC` ，表示新生代使用 ParNew，老年代的用 CMS。

##### **缺点**：

 **CPU敏感**：因为采用的并发的技术所以对处理器的核心要求较大。

 **浮动垃圾**：在CMS进行并发清楚的时候因为采用的是并发的轻快，所以在清除的时候用户线程会产出新的垃圾。

 因此在进行回收的时候需要预留一部分的空间来存放这些产生垃圾（JDK 1.6 设置的阈值为92%）。

 但是如果用户线程产出的垃圾比较快，预留内存放不下的时候就会出现 Concurrent Mode Failure，这时虚拟机将临时启用 Serial Old 来替代 CMS。

 **内存碎片**：因为采用的是 标记 - 清除 法所以会产生内存碎片。

##### 特点：

 总体来说因为 CMS 是 JVM 产生的第一个并发垃圾收集器，所以还是具有代表性的。为什么采用 标记 - 清除 法，因为在实现 CMS 的时候如果还整理对象的话，那么需要再暂停业务线程，进行一个对象的整理那么 STW 的时间会更长，为了追求 STW 的时间所以没有采用 标记 - 整理。

但是最大的问题是 CMS 采用了标记清除算法，所以会有内存碎片，当碎片较多时，给大对象的分配带来很大的麻烦，为了解决这个问题，CMS 提供一个 参数：`-XX:+UseCMSCompactAtFullCollection`，一般是开启的，如果分配不了大对象，就进行内存碎片的整理过程。 这个地方一般会使用 Serial Old ，因为 Serial Old 是一个单线程，所以如果内存空间很大、且对象较多时,CMS 发生这样情况会很卡。

 该垃圾回收器适合回收堆空间几个 G~ 20G 左右。

#### 4、 Garbage First (G1)

 G1 垃圾回收器的设计思想与前面所有的垃圾回收器的都不一样，前面垃圾回收器采用的都是 分代划分 的方式进行设计的，而 G1 则是将堆看作是一个整体的区域，这个区域被划分成了一个个大小一致的独立区域（Region），而每个区域都可以根据需要扮演Eden、Survivor以及老年代区域。当进行对象回收的时候就可以根据每个区域的情况进行一个回收，从而效率高。

##### Region

 上面讲到除了每个Region可以扮演不同的区域，还有一个类似老年代的区域 Humongous 区域，用来专门存放大对象的。当一个对象超过了Region区空间的一半大小则判定为大对象。（每个 Region 的大小可以通过参数-`XX:G1HeapRegionSize` 设定，取值范围为 1MB~32MB,且应为 2 的 N 次 幂。）

 而对于那些超过了整个 Region 容量的超级大对象，将会被存放在 N 个连续的 Humongous Region 之中，G1 的进行回收大多数情况下都把 Humongous Region 作为老年代的一部分来进行看待。

![img](https://img2020.cnblogs.com/blog/1360865/202102/1360865-20210202132050798-2058500214.png)

**开启参数** ：-XX:+UseG1GC `

**分区大小**:`-XX:+G1HeapRegionSize`

![img](https://img2020.cnblogs.com/blog/1360865/202102/1360865-20210202132113015-1119035580.png)

一般建议逐渐增大该值，随着 size 增加，垃圾的存活时间更长，GC 间隔更长，但每次 GC 的时间也会更长。

**最大** **GC** **暂停时间** :`-XX:MaxGCPauseMillis`

![img](https://img2020.cnblogs.com/blog/1360865/202102/1360865-20210202132125106-1236182152.png)

##### 运行过程

![img](https://img2020.cnblogs.com/blog/1360865/202102/1360865-20210202132136665-1365998327.png)

G1 的运作过程大致可划分为以下四个步骤:

 **初始标记** **(Initial Marking)** ：标记与 GC Roots 能关联到的对象，修改 TAMS 指针的值，这个过程是需要暂停用户线程的，但是耗时非常的短。

 TAMS (Top at Mark Start)：当进行下一步并发标记的时候用户线程是会产生新的对象的，而这些对象是被判定为可存活对象而非垃圾。这个时候就需要划分一小块区域来存放这这些对象。

 **并发标记** **(Concurrent Marking)**：进行扫描标记所有可回收的对象。当扫描完成后，并发会有引用变化的对象，而这些对象会漏标这些漏标的对象会被 SATB 算法所解决。

 SATB(snapshot-at-the-beginning)：类似快照，对当前区域进行一个快照的保存，之后再最终标记的时候进行对比查看漏标的会被重新标记上（后面的文章会详解）。

 **最终标记 (Final Marking)**： 暂停所有的用户线程，对之前漏标的对象进行一个标记。

 **筛选回收( Live Data Counting and Evacuation)**：更新Region的统计数据，对各个 Region 的回收价值进行一个排序，根据用户所设置的停顿时间制定一个回收计划，自由选择任意个 Region 进行回收。将需要回收的Region 复制到空的 Region 区域中，再清除掉原来的整个Region区域。这块还涉及到对象的移动所以需要暂停所有的用户线程，有多条回收器线程进行完成。

##### 特点：

 **并行与并发**：G1 能充分利用多 CPU、多核环境下的硬件优势，使用多个 CPU（CPU 或者 CPU 核心）来缩短 Stop-The-World 停顿的时间，部分其他收集器

原本需要停顿 Java 线程执行的 GC 动作，G1 收集器仍然可以通过并发的方式让 Java 程序继续执行。

 **分代收集**：与其他收集器一样，分代概念在 G1 中依然得以保留。虽然 G1 可以不需要其他收集器配合就能独立管理整个 GC 堆，但它能够采用不同的方式

去处理新创建的对象和已经存活了一段时间、熬过多次 GC 的旧对象以获取更好的收集效果。

 **空间整合**：与 CMS 的“标记—清理”算法不同，G1 从整体来看是基于“标记—整理”算法实现的收集器，从局部（两个 Region 之间）上来看是基于“复制”算法实现的，但无论如何，这两种算法都意味着 G1 运作期间不会产生内存空间碎片，收集后能提供规整的可用内存。这种特性有利于程序长时间运行，分配大对象时不会因为无法找到连续内存空间而提前触发下一次 GC。

 **追求停顿时间：**`-XX:MaxGCPauseMillis` 指定目标的最大停顿时间，G1 尝试调整新生代和老年代的比例，堆大小，晋升年龄来达到这个目标时间。

 该垃圾回收器适合回收堆空间上百 G。一般在 G1 和 CMS 中间选择的话平衡点在 6~8G，只有内存比较大 G1 才能发挥优势

# [JVM（五） GC 底层细节](https://www.cnblogs.com/mouren/p/14387192.html)

### 一、三色标记

 在三色标记之前有个算法叫 标记 - 清除 法，这个标记会根据这个对象是否可达（GC Roots）来进行一个标记设置，GC 回收的时候就会根据这个标记进行清除。

 标记 - 清除 法最大的问题就是在清除的-时候需要 STW 这就导致了 GC 的效率就比较低。

 三色标记最大的好处就是可以与业务线程并发执行，从而显著的提升标记的效率，三色 对应黑、白、灰三种颜色，每种颜色都代表着不同的意思：

 **黑色**：根对象或它所有的子对象都以及被扫描过。

 **灰色**：本身被扫描但是它的子对象还没被扫描完。

 **白色**：没有被扫描过的对象，如果扫描标记完成后还是白色的说明这个对象是垃圾。

![img](https://img2020.cnblogs.com/blog/1360865/202102/1360865-20210207233941601-2065668926.png)

#### 三色标记问题

 因为在标记的时候业务线程还在继续运行，那么就会存在当 GC 其中一条线程以及完成标记扫描后与其他一条线程未被扫描完成后的对象发生了引用的改变。

 CMS 与 G1 都有不同的解决方案，但是对比来说还是 G1 的效率是比较高的。

![img](https://img2020.cnblogs.com/blog/1360865/202102/1360865-20210207234009098-1574924917.png)

##### CMS 解决方案

 当 C 对象被一个黑色对象所引用，那么 A 对象就会被修改为灰色，而 B 则会被标记为黑色，让垃圾回收器重新标记这个对象链上的所有对象，这个算法为 **Incremental Update** 算法。

##### G1 解决方案

 在开始 GC 之前先在 Region 中存放一个快照，当 B 和 C 的引用断开之后则会把这个引用推到 GC 堆栈中（灰色对白色对象的引用），以确保 C 对象还能被扫描到，下次扫描的时候**（最终标记）**就能扫描到这个 C 对象了（以这个对象的引用为根进行重新扫描）。这个算法为 **STAB(snapshot-at-the-beginning)** 算法。

 **最终标记**：对所有业务线程进行暂停，处理那些在并发标记的时候漏标的对象。

##### CMS 和 G1 对比

 CMS 关注的是对象引用的增加。

 G1 关注的是对象的引用的删除。

 CMS 是重新扫描所有的对象，而 G1 则是以修改的引用作为根进行扫描的所以 CMS 对比 G1 来说效率是比较低的。

### 二、跨代引用

 跨代引用 一般指的是老年代的对象中引用了新生代的对象，如果新生代发生 Young GC 的时候就需要扫从老年代到新生代的所有引用，这样的扫描就比较消耗性能，所以就存在了Card Table（卡表） 和 Rset（记忆集）。

![img](https://img2020.cnblogs.com/blog/1360865/202102/1360865-20210207234029702-455046429.png)

##### CardTable

 当发生新生代 GC 的时候需要扫描整个老年代，效率很低，所以 JVM 就设计了 CardTable，类似于一个数组，数组中的每个元素就对应了每个内存区域。当某个区域发生了跨代引用的话那么就会把这个区域的设置为 Dirty（标记修改为 1 ），每个数组的元素对应了一个个老年代的内存区域，而这一个个数组元素被称为“卡页”，一般来说卡页的大小都是 2 次幂。被标记为 1 的卡页就会存放一个记录跨代引用的记忆集（RSet）。

##### RSet

 记录了其他 Region 区到当前 Region 区的引用，这个的存在就可以在扫描老年代的时候只扫描这个里面所存储的对象引用就行了。

##### G1 与 CMS 对比

 其实在 CMS 中也有类似的处理方式，只是在 G1 中是每个 Region 中都会存放一个 RSet ，但是在 CMS 中只有一块内存区域，那么就导致了 G1 的 RSet 占用的内存过多，而 CMS 只会存在一个老年代，所以在选择 GC 时需要考虑当前堆内存的大小。

### 三、安全点、安全区域

 在 GC 开始工作需要暂停当前业务所有线程的时候，并不是强制停止所有的业务线程的，而是会修改一个主动式中断标记为中断，而所有的业务线程会去轮询查询这个标记来判断是否需要停止当前的线程。

#### 安全点

 当业务线程发现需要中断时，那么业务线程就会跑到最近的一个安全点进行挂起，而这个安全点就可能是方法的调用、循环的跳转、异常的跳转。

#### 安全区域

 当然只有在跑动的线程才会区轮询查询这个标记，但是当线程 Sleep 活着 Blocked 状态的话，那么线程就不知道需不需要挂起当前线程。那么就会看这个代码是否在这个安全区内（区间不会发生引用的变动），所以 GC就不用管这些线程，可以进行 GC 了。当然，在 GC 没有完成根节点枚举的时候这些线程即使已经完成了也需要进行挂起，等待 GC 完成后继续进行下一步。

### 四、GC日志

![img](https://img2020.cnblogs.com/blog/1360865/202102/1360865-20210207234042437-481478395.png)

#### GC常用参数

```diff
Xmn -Xms -Xmx –Xss 年轻代 最小堆 最大堆 栈空间
-XX:+UseTLAB 使用 TLAB，默认打开
-XX:+PrintTLAB 打印 TLAB 的使用情况
-XX:TLABSize 设置 TLAB 大小
-XX:+DisableExplicitGC 启用用于禁用对的调用处理的选项 System.gc() -XX:+PrintGC 查看 GC 基本信息
-XX:+PrintGCDetails 查看 GC 详细信息
-XX:+PrintHeapAtGC 每次一次 GC 后，都打印堆信息
-XX:+PrintGCTimeStamps 启用在每个 GC 上打印时间戳的功能
-XX:+PrintGCApplicationConcurrentTime 打印应用程序时间(低) -XX:+PrintGCApplicationStoppedTime 打印暂停时长（低）
-XX:+PrintReferenceGC 记录回收了多少种不同引用类型的引用（重要性低）
-verbose:class 类加载详细过程
-XX:+PrintVMOptions 可在程序运行时，打印虚拟机接受到的命令行显示参数
-XX:+PrintFlagsFinal -XX:+PrintFlagsInitial 打印所有的 JVM 参数、查看所有 JVM 参数启动的初始值（必须会用）
-XX:MaxTenuringThreshold 升代年龄，最大值 15, 并行（吞吐量）收集器的默认值为 15，而 CMS 收集器的默认值为 6。
```

#### Parallel 常用参数

```diff
-XX:SurvivorRatio 设置伊甸园空间大小与幸存者空间大小之间的比率。默认情况下，此选项设置为 8
-XX:PreTenureSizeThreshold 大对象到底多大，大于这个值的参数直接在老年代分配
-XX:MaxTenuringThreshold 升代年龄，最大值 15, 并行（吞吐量）收集器的默认值为 15，而 CMS 收集器的默认值为 6。
-XX:+ParallelGCThreads 并行收集器的线程数，同样适用于 CMS，一般设为和 CPU 核数相同
-XX:+UseAdaptiveSizePolicy 自动选择各区大小比例
```

#### CMS常用参数

```diff
-XX:+UseConcMarkSweepGC 启用 CMS 垃圾回收器
-XX:+ParallelGCThreads 并行收集器的线程数，同样适用于 CMS，一般设为和 CPU 核数相同
-XX:CMSInitiatingOccupancyFraction 使用多少比例的老年代后开始 CMS 收集，默认是 68%(近似值)，如果频繁发生 SerialOld 卡顿，应该调小，（频繁 CMS 回
收）
-XX:+UseCMSCompactAtFullCollection 在 FGC 时进行压缩
-XX:CMSFullGCsBeforeCompaction 多少次 FGC 之后进行压缩
-XX:+CMSClassUnloadingEnabled 使用并发标记扫描（CMS）垃圾收集器时，启用类卸载。默认情况下启用此选项。
-XX:CMSInitiatingPermOccupancyFraction 达到什么比例时进行 Perm 回收，JDK 8 中不推荐使用此选项，不能替代。
-XX:GCTimeRatio 设置 GC 时间占用程序运行时间的百分比（不推荐使用）
-XX:MaxGCPauseMillis 停顿时间，是一个建议时间，GC 会尝试用各种手段达到这个时间，比如减小年轻代
```

#### G1常用参数

```diff
-XX:+UseG1GC 启用 CMS 垃圾收集器
-XX:MaxGCPauseMillis 设置最大 GC 暂停时间的目标（以毫秒为单位）。这是一个软目标，并且 JVM 将尽最大的努力（G1 会尝试调整 Young 区的块数来）来实
现它。默认情况下，没有最大暂停时间值。
-XX:GCPauseIntervalMillis GC 的间隔时间
-XX:+G1HeapRegionSize 分区大小，建议逐渐增大该值，1 2 4 8 16 32。随着 size 增加，垃圾的存活时间更长，GC 间隔更长，但每次 GC 的时间也会更长
-XX:G1NewSizePercent 新生代最小比例，默认为 5%
-XX:G1MaxNewSizePercent 新生代最大比例，默认为 60%
-XX:GCTimeRatioGC 时间建议比例，G1 会根据这个值调整堆空间
-XX:ConcGCThreads 线程数量
-XX:InitiatingHeapOccupancyPercent 启动 G1 的堆空间占用比例，根据整个堆的占用而触发并发 GC 周期
```

### 五、低延迟垃圾回收器

 传统的垃圾回收器一般情况下内存、吞吐量、延迟只能满足其中的两个，但随着目前的科技发展以 延迟 成为主要发展目标，所以就有现在的低延迟的垃圾回收器。

#### Eplison

 这个垃圾回收器不回收垃圾，主要用于压力测试，可以查看堆的布局、对象分配等等相关信息。

#### ZGC

 有类似于 G1 的 Region，但是没有分代。

 标志性的设计是染色指针 ColoredPointers（涉及的东西较多），染色指针有 4TB 的内存限制，但是效率极高，它是一种将少量额外的信息存储在指针上 的技术。 它可以做到几乎整个收集过程全程可并发，短暂的 STW 也只与 GC Roots 大小相关而与堆空间内存大小无关，因此可以实现任何堆空间 STW 的时间小于 十毫秒的目标。

#### Shenandoah

 第一款非 Oracle 公司开发的垃圾回收器，有类似于 G1 的 Region，但是没有分代。

 也用到了染色指针 ColoredPointers。 效率没有 ZGC 高，大概几十毫秒的目标。

# [JVM（六）Class 文件结构及字节码指令](https://www.cnblogs.com/mouren/p/14529512.html)

### 一、class 文件结构

 Java 技术之所以能保持非常好的向后兼容性，这点原因和 Class 文件结构有很大的关系。虽然 Java 到目前位置以及发展了很多的版本了，但是 Class 文件结构的内容在 JDK 1.2 的时代就已经定义好了，即使现在已经经历了很多的版本也只是在原来的基础上新增内容、扩充功能，并没有修改定义的内容。

 首先使用 Sublime Text 软件打开一个 Class 文件，可以看到一堆看不懂的内容，其实这个就是一个 Class 的真实内容——都是以二进制字节流所组成的一个文件。
![img](https://img2020.cnblogs.com/blog/1360865/202103/1360865-20210313165559333-1170562927.png)

 Class 文件格式采用一种类似于 C 语言结构体的伪结构来存储数据，这种伪结构中只有两种数据类型：无符号数和表。

 无符号数属于基本的数据类型，以 u1、u2、u4、u8 来分别代表 1 个字节（一个字节是由两位 16 进制数组成）、2 个字节、4 个字节和 8 个字节的无符号数，无符号数可以用来描述数字、索引引用、数量值或者按照 UTF-8 编码构成字符串值。

 表是由多个无符号数或者其他表作为数据项构成的复合数据类型，所有表都习惯性地以“_info”结尾。表用于描述有层次关系的复合结构的数据，整个 Class 文件本质上就是一张表。

#### Class 文件格式详解

 Class 文件不存在间隔符号那么，JVM 是怎么识别其中的内容的呢？其实 Class 文件读取的时候是被严格的限制的，位置、数量、哪个字节代表什么含义都是不允许改变的。

- ##### 魔数

![img](https://img2020.cnblogs.com/blog/1360865/202103/1360865-20210313165629100-1932705148.png)

 每个 Class 文件的开头四个字节`ca fe ba be`被称为**魔数**，它的唯一作用就是确定这个文件是不是能被 JVM 所接受。

 之后的四个字节`00 00 00 34`则代表着 Class 文件的版本号：`00 00`字节是次版本号（MinorVersion），`00 34`字节是主版本号（Major Version）。 Java 的版本号是从 45 开始的，JDK 1.1 之后的每个 JDK 大版本发布主版本号向上加 1 高版本的 JDK 能向下兼容以前版本的 Class 文件，但不能运行以后版 本的 Class 文件，即使文件格式并未发生任何变化，虚拟机也必须拒绝执行超过其版本号的 Class 文件。 代表 JDK1.8（16 进制的 34，换成 10 进制就是 52）。

- ##### 常量池

   常量池中的数量是不固定的，所以在常量池的入口需要放置一项 u2 类型的数据，代表常量池中的常量个数（constant_pool_count），这里十六进制转成十进制后需要减一才是常量池中的总个数，而且计数是从 1 开始的。

![img](https://img2020.cnblogs.com/blog/1360865/202103/1360865-20210313165648195-192066725.png)

 常量池里面主要存放两个大类：字面量（Literal）和符号引用（Symbolic References）。

 **字面量**就是常量里具体的值（包括 final 修饰的）。

 **符号引用**就是在之前文章中讲到的类的权全限定名、字段的名称和描述符等等。。

![img](https://img2020.cnblogs.com/blog/1360865/202103/1360865-20210313165711554-1590998182.png)

用 jclasslib 插件可以看到。

- ##### 访问标志

   用于识别一些类或者接口层次的访问信息，包括：这个 Class 是类还是接口、是否定义为 public 类型、是否定义为 abstract 类型，如果是类的话，是否被声明为 final 等。

- ##### 类索引、父类索引与接口索引集合

   这三项数据来确定这个类的继承关系。类索引用于确定这个类的全限定名，父类索引用于确定这个类的父类的全限定名。由于 Java 语言不允许多重继承， 所以父类索引只有一个，除了 java.lang.Object 之外，所有的 Java 类都有父类，因此除了 java.lang.Object 外，所有 Java 类的父类索引都不为 0。接口索引集合就用来描述这个类实现了哪些接口，这些被实现的接口将按 implements 语句（如果这个类本身是一个接口，则应当是 extends 语句）后的接口顺序 从左到右排列在接口索引集合中。

- ##### 字段表集合

   描述接口或者类中声明的变量。字段（field）包括类级变量以及实例级变量。

   而字段叫什么名字、字段被定义为什么数据类型，这些都是无法固定的，只能引用常量池中的常量来描述。

   字段表集合中不会列出从超类或者父接口中继承而来的字段，但有可能列出原本 Java 代码之中不存在的字段，譬如在内部类中为了保持对外部类的访问 性，会自动添加指向外部类实例的字段。

- ##### 方法表集合

![img](https://img2020.cnblogs.com/blog/1360865/202103/1360865-20210313165728139-911406396.png)

 描述了方法的定义，但是方法里的 Java 代码，经过编译器编译成字节码指令后，存放在属性表集合中的方法属性表集合中一个名为“Code”的属性里面。 与字段表集合相类似的，如果父类方法在子类中没有被重写（Override），方法表集合中就不会出现来自父类的方法信息。但同样的，有可能会出现由编译器自动添加的方法，最典型的便是类构造器“＜clinit＞”方法和实例构造器“＜init＞”

- ##### 属性集合

  存储 Class 文件、字段表、方法表都自己的属性表集合，以用于描述某些场景专有的信息。如方法的代码就存储在 Code 属性表中。

### 二、异常表

> JVM 是如何做到当代码出现错误的时候能抛出对应的错误的呢？为什么 finally 中的代码不管如何都会被执行呢？ 因为在 JVM 中是存在一个异常表的存在，会根据哪一行到哪一行代码错误后会直接跳转到对应的字节码指令集处顺利的执行下去。

 具体代码：

```java
public class ExceptionTest {
    synchronized void test01(){
        System.out.println("test01");
    }

    synchronized void test02(){
        System.out.println("test02");
    }
    final  Object lock = new Object();

    void doLock() {
        synchronized (lock) {
            System.out.println("lock!");
        }
    }

}
```

 编译之后：

![img](https://img2020.cnblogs.com/blog/1360865/202103/1360865-20210313165742760-883588661.png)

 从字节码指令中可以看到,其中包含了三条`monitorexit`指令，这个是为了保证所有的异常条件可以成功的退出。

 往下看可以看到一个 Exception table：

 **form** 指定字节码指令索引开始位置

 **to** 指定字节码索引的结束位置。

 **target** 异常处理的起始位置。

 **type** 异常类型。

 其实，从字面意思来也可以看出来，例如：7~17 行直接发生了异常则会直接跳转到 20 行。

##### finally实例：

 代码如下：

```java
    public int getNum(){
        try {
            int a = 100/0;
            return a;
        }finally {
            return 100;
        }
    }
```

 编译后：

![img](https://img2020.cnblogs.com/blog/1360865/202103/1360865-20210313165823653-685621662.png)

 从实例中可以看出来其实 finally 的实现就是异常表控制的。

### 三、字节码指令——装箱拆箱

 Java 中是有 8 种基本数据类型的以及对应的包装类，比如 int 和 Integer ,因为包装类是可以赋值 null 而有的时候就需要用到 null 值，而且数据库中是有 null 值的，所以一般用在类的属相上面。

 具体代码：

```java
package com.test.demo;

public class JVMTest {

    public int getNum() {
        Integer num = 9999;
        int num2 = num * 3;
        return num2;
    }
    public static void main(String[] args) {

    }
}
```

 `cmd` 中执行 `javap -v .\JVMTest.class`

![img](https://img2020.cnblogs.com/blog/1360865/202103/1360865-20210313165840575-1001166815.png)

 方法的字节码指令集里面可以看到相关注释，先是调用了`Integer.ValueOf()`的方法之后才进行的一个运算，从这里我们可以看出来确实是进行了自动的拆箱了。

 之后我们看下`Integer.ValueOf()`的源代码：

```java
 /**
     * Returns an {@code Integer} instance representing the specified
     * {@code int} value.  If a new {@code Integer} instance is not
     * required, this method should generally be used in preference to
     * the constructor {@link #Integer(int)}, as this method is likely
     * to yield significantly better space and time performance by
     * caching frequently requested values.
     *
     * This method will always cache values in the range -128 to 127,
     * inclusive, and may cache other values outside of this range.
     *
     * @param  i an {@code int} value.
     * @return an {@code Integer} instance representing {@code i}.
     * @since  1.5
     */
    public static Integer valueOf(int i) {
        if (i >= IntegerCache.low && i <= IntegerCache.high)
            return IntegerCache.cache[i + (-IntegerCache.low)];
        return new Integer(i);
    }



 /**
     * Cache to support the object identity semantics of autoboxing for values between
     * -128 and 127 (inclusive) as required by JLS.
     *
     * The cache is initialized on first usage.  The size of the cache
     * may be controlled by the {@code -XX:AutoBoxCacheMax=<size>} option.
     * During VM initialization, java.lang.Integer.IntegerCache.high property
     * may be set and saved in the private system properties in the
     * sun.misc.VM class.
     */

    private static class IntegerCache {
        static final int low = -128;
        static final int high;
        static final Integer cache[];

        static {
            // high value may be configured by property
            int h = 127;
            String integerCacheHighPropValue =
                sun.misc.VM.getSavedProperty("java.lang.Integer.IntegerCache.high");
            if (integerCacheHighPropValue != null) {
                try {
                    int i = parseInt(integerCacheHighPropValue);
                    i = Math.max(i, 127);
                    // Maximum array size is Integer.MAX_VALUE
                    h = Math.min(i, Integer.MAX_VALUE - (-low) -1);
                } catch( NumberFormatException nfe) {
                    // If the property cannot be parsed into an int, ignore it.
                }
            }
            high = h;

            cache = new Integer[(high - low) + 1];
            int j = low;
            for(int k = 0; k < cache.length; k++)
                cache[k] = new Integer(j++);

            // range [-128, 127] must be interned (JLS7 5.1.7)
            assert IntegerCache.high >= 127;
        }

        private IntegerCache() {}
    }
```

 从源代码里面我们可以看到是有一个`IntegerCache`的一个存在而它的最小值写死为 -128，而最大值却没有写死。

 其实Integer的最大值我们是可以通过虚拟机参数的方式进行一个设置`-XX:AutoBoxCacheMax`。

```java
public class BoxCache {
	public static void main(String[] args) {
		Integer num1 = 123;
        Integer num2 = 123;
        Integer num3 = 123;
        Integer num4 = 123;
        
        System.out.println(num1 == num2);
        System.out.println(num3 == num4);
	}
}
//那么上面如果在没有设置 -XX:AutoBoxCacheMax 参数的情况下为： true,false
//但是设置的数字大于等于 128 那么都会是true
```

#### 2、数组

> 其实数组这个数据类型是 JVM 内置的一种对象类型，在 Java 中是不存在数组的源代码的。

```java
public class ArrayDemo{
    int getValue() {
        int[] arr = new int[]{1111,2222,3333,4444};
        return arr[2];
    }
    int getLength(int[] arr) {
        return arr.length;
    }
}
```

 经过反编译之后：

![img](https://img2020.cnblogs.com/blog/1360865/202103/1360865-20210313165905288-1071487525.png)

 可以看到在新建数组的时候是采用的`newarray + 数据类型`的指令进行创建的，之后数组内的元素都是一个个压入进去的。

 而数组元素的访问则是在 28 ~ 30 行代码进行实现的。 `aload_1`将第二个引用类型推送到栈中 ，之后在`iconst_2`再往栈中压入常量 2，最后在`iaload`获取数组对应下标的元素并返回。

 而数组长度的获取就更简单了：

![img](https://img2020.cnblogs.com/blog/1360865/202103/1360865-20210313165917737-1184606986.png)

 直接调用`arraylength`之后返回就可以了。

#### 3、foreach

> 增强 for 循环在代码中是比较常见的一个循环，其实集合使用 foreach 则在编译后会被编译成迭代器的方式进行一个循环。

 手动编写的代码：

```java
  public void  foreach(int[] arr) {
        for (int i : arr) {
            System.out.println(i);
        }
    }

  public void  foreach2(List<Integer> list) {
        for (int i : list) {
            System.out.println(i);
        }
    }
```

 编译之后的代码

  [JVM（七）类加载机制和类加载](https://www.cnblogs.com/mouren/p/14529545.html)

> 上一个章节主要学习记录了 class 文件的文件结构以及字节码文件。
>
> 那么这一章节主要记录的是类的一个生命周期，研究 class 文件被加载到内存之后做了什么，有那些步骤，其次就是加载类的类加载器，以及其中的双亲委派机制，最后再了解下什么是OSGI。

### 一、类的生命周期

 一个类被加载到内存之后其实会有一些步骤来初始化类的一些信息,初始化完毕之后就是使用类，最后被 GC 所回收，其整个生命周期：**加载（Loading）**、**链接**(验证、准备、解析)、 **初始化（Initialization）**、使用（Using）和卸载（Unloading）7 个阶段：

![img](https://img2020.cnblogs.com/blog/1360865/202103/1360865-20210313170128008-1800946880.png)

#### 加载

 将 Class 文件加载到内存中。在虚拟机规范里面并没有强制约束加载的过程，具体是由虚拟机的实现来把控的。主要步骤为下：

 1）通过一个类的全限定名来获取类的二进制字节流（不是一定在 Class 中获取，可以从网络、数据库、压缩包中获取也行）。

 2）把字节流里面的数据转换为方法区的运行时数据结构。

 3）在内存里面生成一个对应类的class对象，以供其他数据的访问。

#### 链接

 链接这步涉及的东西比较多，主要分为三个步骤：

- ##### 验证

   主要是校验 Class 文件是不是符合 JVM 的规范，大致可以分为四个阶段：文件格式校验、元数据验证、字节码验证、符号引用验证（步骤不是关键，只需要了解这步是校验的就行了）。

  *文件格式校验*：以二进制字节流法的方式进行校验，主要校验上节文章中所提到的 Class 文件结构(https://www.cnblogs.com/mouren/p/14529512.html)。

  *元数据校验*：这块的话校验的就是字节码描述信息的分析，看看是不是符合《Java 语言规范》的要求，例如：当前类是不是有父类（除 Object 外都应该有父类）。

  *字节码验证*：这块的校验是整个校验里面最麻烦的一个，主要校验字节码的指令是不是符合规范，这块是字节码的指令不是字节码。

  *符号引用验证*：这块就是校验这个类里面是不是有其他的引用是缺失的之类的。

  **总结**：这块的校验在类加载机制上来说是很重要的一个阶段，但是这步是可通过 JVM 参数上跳过这个步骤。 `-Xverify:none`

  JVM 参数官网直达：https://docs.oracle.com/javase/8/docs/technotes/tools/unix/java.html

  ###### ![img](https://img2020.cnblogs.com/blog/1360865/202103/1360865-20210313170212782-1238645151.png)

![img](https://img2020.cnblogs.com/blog/1360865/202103/1360865-20210313170225935-1228235777.png)

- ##### 准备

   给静态变量进行一个初始化的一个赋值（static 修饰），这块的内存都在方法区中分配。例如：

  ```java
  public static int num = 2333;
  //这块初始化赋值是 0 不是 2333 的赋值。
  ```

- ##### 解析

   解析就是把 JVM 常量池里的符号引用替换为直接引用的过程。符号引用就是一开始还没进行对象内存分配的时候的一个占位符，而对象进行内存分配之后就有内存地址了，然后占位符换成对象的内存地址。

#### 初始化

 主要是对一个class 中的静态块进行一个初始化（对应的字节码就是 clinit 方法）。当然也不是必须的如果类里面没有静态块也没有对变量赋值的操作，那么就不会生成 clint 方法。

 **初始化阶段，只有六种情况是必须对类进行初始化的，当前是在加载和验证之后。**

1）遇到`new`、`getstatic`、`putstaic`或`invokestatic`这四个字节码指令的时候，如果类还没有进行初始化的时候那么就会先进行对类的初始化，对应的 Java 代码场景：

 使用new关键字创建对象的时候。

 读取或者设置一个类的静态变量字段的时候。

 调用一个类的静态方法。

2）使用反射类对类进行反射的时候，如果没有初始化那么会进行类初始化。

3）初始化类的时候发现这个类的父类还没被初始化的时候，会对这个父类进行初始化先。

4）当指定一个拥有main函数运行的时候需要初始化这个类。

5）当使用 JDK 1.7 的动态语言支持时， 如果一个 `java.lang.invoke.MethodHandle` 实例最后的解析结果`EF_getStatic`、 `REF_putStatic`、 `REF_invokeStatic` 的方法句柄， 并且这个方法句柄所对应的类没有进行过初始化， 则需要先触发其初始化。

6)接口拥有被 default 所修饰的方法时，如果实现该结构的类发生初始化的时候，那么这个接口肯定在之前就需要初始化。
![img](https://img2020.cnblogs.com/blog/1360865/202103/1360865-20210313170333256-1955033167.png)

### 二、类加载器

 类加载器就是加载上面三个主要步骤的工具。

#### JDK 中类加载器

- ##### Bootstrap ClassLoader（根加载器）

   最底层的类加载器，Java 代码无法获取到的类加载器，由 c/c++编写，主要加载核心类库，例如：rt.jar、resources.jar等等。`-Xbootclasspath`可以修改加载的目录。

- ##### Extention ClassLoader（扩展类加载器）

   扩展类加载器，主要加载 lib/ext 目录下的 jar 和 class 文件。通过系统变量 java.ext.dirs 可以指定这个目录，契父类为 URLClassloader。

- ##### Application ClassLoader（系统加载器）

   也可以被称为 System ClassLoader，用来加载我们写的代码。

- ##### Custom ClassLoader (自定义类加载器)

   自定义的类加载器。

#### 双亲委派机制

 当我们自己写一个 String 类，包名和 Java 中的 String 类即使一致也不会被加载，因为在系统加载器进行加载的时候会往上——扩展类加载器进行询问是否已经加载了这个类，若找不到则会继续向上——根加载器，此时就会发现 String 类已经被加载了那么我们自己写的就不会被加载。这就是双亲委派机制。

 这个机制的存在使得 Java 代码不会被覆盖保证了代码的安全性。

 我们可以翻阅下 `ClassLoader.class` 的源代码，可以看到是有个 parent 的一个存在，会进行判断这个类加载器是否拥有父类，有的话则会调用父类的类加载方法。

```java
 /**
     * Loads the class with the specified <a href="#name">binary name</a>.  The
     * default implementation of this method searches for classes in the
     * following order:
     *
     * <ol>
     *
     *   <li><p> Invoke {@link #findLoadedClass(String)} to check if the class
     *   has already been loaded.  </p></li>
     *
     *   <li><p> Invoke the {@link #loadClass(String) <tt>loadClass</tt>} method
     *   on the parent class loader.  If the parent is <tt>null</tt> the class
     *   loader built-in to the virtual machine is used, instead.  </p></li>
     *
     *   <li><p> Invoke the {@link #findClass(String)} method to find the
     *   class.  </p></li>
     *
     * </ol>
     *
     * <p> If the class was found using the above steps, and the
     * <tt>resolve</tt> flag is true, this method will then invoke the {@link
     * #resolveClass(Class)} method on the resulting <tt>Class</tt> object.
     *
     * <p> Subclasses of <tt>ClassLoader</tt> are encouraged to override {@link
     * #findClass(String)}, rather than this method.  </p>
     *
     * <p> Unless overridden, this method synchronizes on the result of
     * {@link #getClassLoadingLock <tt>getClassLoadingLock</tt>} method
     * during the entire class loading process.
     *
     * @param  name
     *         The <a href="#name">binary name</a> of the class
     *
     * @param  resolve
     *         If <tt>true</tt> then resolve the class
     *
     * @return  The resulting <tt>Class</tt> object
     *
     * @throws  ClassNotFoundException
     *          If the class could not be found
     */
    protected Class<?> loadClass(String name, boolean resolve)
        throws ClassNotFoundException
    {
        synchronized (getClassLoadingLock(name)) {
            // First, check if the class has already been loaded
            Class<?> c = findLoadedClass(name);
            if (c == null) {
                long t0 = System.nanoTime();
                try {
                    if (parent != null) {
                        c = parent.loadClass(name, false);
                    } else {
                        c = findBootstrapClassOrNull(name);
                    }
                } catch (ClassNotFoundException e) {
                    // ClassNotFoundException thrown if class not found
                    // from the non-null parent class loader
                }

                if (c == null) {
                    // If still not found, then invoke findClass in order
                    // to find the class.
                    long t1 = System.nanoTime();
                    c = findClass(name);

                    // this is the defining class loader; record the stats
                    sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
                    sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                    sun.misc.PerfCounter.getFindClasses().increment();
                }
            }
            if (resolve) {
                resolveClass(c);
            }
            return c;
        }
    }
```

#### Tomcat 类加载机制

 在 Tomcat 里面有时候启动项目的时候会需要启动多个项目，那么就必定违反了双亲委派机制，那么他的结构是怎么设计类加载器的？看下图：
![img](https://img2020.cnblogs.com/blog/1360865/202103/1360865-20210313170358767-146493267.png)

 其实从图里面可以看出来其实 Tomcat 并没有破坏 JDK 原来的类加载器的结构，只是在其 JDK 加载器之外写了其他的类加载器。对于一些需要加载的非基础类， 会由一个叫作 WebAppClassLoader 的类加载器优先加载。 等它加载不到的时候， 再交给上层的 ClassLoader 进行加载。

各自都是独立继承ClassLoder？然后互不影响？

这个加载器用来隔绝不同应用的 .class 文件， 比如你的两个应用， 可能会依赖同一个第三方的不同版本， 它们是相互没有影响的。

如何在同一个 JVM 里， 运行着不兼容的两个版本， 当然是需要自定义加载器才能完成的事。
那么 tomcat 是怎么打破双亲委派机制的呢？ 可以看图中的 WebAppClassLoader， 它加载自己目录下的 .class 文件， 并不会传递给父类的加载器 ：

![img](https://img2020.cnblogs.com/blog/1360865/202103/1360865-20210313170428217-1598100026.png)

![img](https://img2020.cnblogs.com/blog/1360865/202103/1360865-20210313170509750-1936241936.png)

![img](https://img2020.cnblogs.com/blog/1360865/202103/1360865-20210313170525736-203024851.png)

但是， 它却可以使用 SharedClassLoader 所加载的类， 实现了共享和分离的功能。
但是我们自己写一个 ArrayList， 放在应用目录里， tomcat 依然不会加载。 它只是自定义的加载器顺序不同， 但对于顶层来说， 还是一样的。

#### SPI（Service provider Interface）

 Java 中有一个 SPI 机制， 全称是 Service Provider Interface， 是 Java 提供的一套用来被第三方实现或者扩展的 API， 它可以用来启用框架扩展和替换组件。

 其实 JDBC 就是以 SPI 的一个具体的实现，JDK 写接口，每个数据库厂商写具体的实现，以 MySQL为例：

![img](https://img2020.cnblogs.com/blog/1360865/202103/1360865-20210313170540088-941847242.png)

 以上代码其实就算没有导入 MySQL 的驱动包也是可以编译通过的，但是运行的话就会报错。那么为什么我们并没有创建具体的实现类但是也可以调用到具体的实现类，里面最重要的一步就是第二步——就是获取数据库链接：

```java
public class DriverManager {
    //这里是静态块，基本上肯定会调用的那种。
	static {
        loadInitialDrivers();
        println("JDBC DriverManager initialized");
    }

 private static void loadInitialDrivers() {
      // ....省略相关代码
        AccessController.doPrivileged(new PrivilegedAction<Void>() {
            public Void run() {
				//此处代码就是关键，这里就是从固定的位置进行读取
                ServiceLoader<Driver> loadedDrivers = ServiceLoader.load(Driver.class);
                Iterator<Driver> driversIterator = loadedDrivers.iterator();
                try{
                    while(driversIterator.hasNext()) {
                        driversIterator.next();
                    }
                } catch(Throwable t) {
                // Do nothing
                }
                return null;
            }
        });
		//       ....省略相关代码
    }
}
```

 ServiceLoader 这个类会从固定的位置——`META-INF/services/`中读取文件中的具体的实现类，那么 MySQL 驱动包中可以看到：

![img](https://img2020.cnblogs.com/blog/1360865/202103/1360865-20210313170554105-107495194.png)

里面确实是有一个这样的目录，目录中文件的内容就是具体实现类的全限定名，这样 ServiceLoader 中就会从这个文件中读取具体的实现类进行一个遍历从而找到具体的实现类进行调用。

下面就是一个具体的代码测试（可跳过）：

```java
package com.test.demo.spi;

//创建一个hello接口
public interface Hello {
     void hello();
}
package com.test.demo.spi;
//随便写一个实现类来实现上面这个接口
public class IHello implements Hello {
    @Override
    public void hello() {
        System.out.println("Hello world!!!");
    }
}
```

![img](https://img2020.cnblogs.com/blog/1360865/202103/1360865-20210313170607650-491881090.png)

在 java 目录下面创建一个以接口全限定名创建的一个文件，文件内容为具体实现类的全限定名。

```java
package com.test.demo.spi;

import java.sql.DriverManager;
import java.util.Iterator;
import java.util.ServiceLoader;

public class Test {
    public static void main(String[] args) {
        ServiceLoader<Hello> load = ServiceLoader.load(Hello.class);
        Iterator<Hello> iterator = load.iterator();
        while (iterator.hasNext()){
            iterator.next().hello();
        }
    }
}
```

![img](https://img2020.cnblogs.com/blog/1360865/202103/1360865-20210313170623120-799252338.png)

成功获取到实现类中的方法。

#### OSGI（了解）

 OSGi 曾经非常流行， Eclipse 就使用 OSGi 作为插件系统的基础。 OSGi 是服务平台的规范， 旨在用于需要长运行时间、 动态更新和对运行环境破坏
最小的系统。
​ OSGi 规范定义了很多关于包生命周期， 以及基础架构和绑定包的交互方式。 这些规则， 通过使用特殊 Java 类加载器来强制执行， 比较霸道。
​ 比如， 在一般 Java 应用程序中， classpath 中的所有类都对所有其他类可见， 这是毋庸置疑的。 但是， OSGi 类加载器基于 OSGi 规范和每个绑定包的 manifest.mf 文件中指定的选项， 来限制这些类的交互， 这就让编程风格变得非常的怪异。 但我们不难想象， 这种与直觉相违背的加载方式， 这些都是由专用的类加载器来实现的。

# JVM（八）方法调用的底层实现

> 上一章中主要记录的类加载机制和类的加载器的知识，那么这一章的话主要是记录方法调用的一些原理，如果有不对的地方请评论区指出，这块的理论比较多，请耐心看下去。

##### 本次测试所使用的代码，配和 jclasslib 插件使用：

```java
package com.test.demo.method;

public class Test01 implements ITest01 {
    public Test01(){
        System.out.println("构造调用！");
    }

    public void hello(){
        System.out.println("Hello world!");
    }

    public static void sayHello(){
        System.out.println("static Hello world!");
    }

    @Override
    public void sayHi() {
        System.out.println("hi!!!");
    }

    public static void main(String[] args) {

        Test01 test01 = new Test01();//构造
        Test01.sayHello();//静态方法

        test01.hello();//普通public方法
        ITest01 iTest01 = test01;
        iTest01.sayHi();//接口调用
    }
}
```

### 一、方法的调用指令

 我们写代码的时候方法的调用是最正常不过的一个行为了，其实从调用的方法的类别上的不同，它底层实现上也是不同的。从字节码的角度上来看的话其实方法调用是分五种指令来进行执行的。

`invokestatic`：调用的静态方法。

`invokespecial`：调用的是私有方法、构造器或者 super 关键字等。

`invokevirtual`：调用的是 public 、protected 等修饰的方法，其实我们调用的方法大多就是这个指令。

`invokeinterface`：以接口类调用的方法就是使用的这个指令。

`invokedynamic`：调用动态方法比如 Lambda 表达式。

 我们所写的方法是可以分为两种的—— **非虚方法** 和 **虚方法** ，那么是 **虚** 什么是 **非虚** 具体的可以继续往下看。

### 二、非虚方法

 非虚方法 的话从字面上理解就是实的方法，就是在代码编译的阶段就可以确定了版本的方法，到后面 JVM 调用的时候就直接调用就可以了。

![img](https://img2020.cnblogs.com/blog/1360865/202103/1360865-20210313170808709-1961610986.png)

 构造方法和静态方法都属于编译后就已经确定了的，所以都属于非虚方法，而且从 jclasslib 中的字节码可以看到两个方法对应的字节码是`invokespecial`、`invokestatic`除了这两个其实也包括了被 final 所修饰的方法（即使它的字节码是`invokevirtual`）。

### 三、虚方法

 上面介绍了 非虚方法 那么这里的虚方法就是非虚方法的反面——在代码编译阶段是没法确定调用的版本。

![img](https://img2020.cnblogs.com/blog/1360865/202103/1360865-20210313170822622-2104310981.png)

 `invokevirtual`（除被final所修饰的方法）和`invokeinterface`这两这个指令都属于虚方法的类别中。

 在很多的时候，JVM 需要根据调用者的动态类型来确定调用的方法，这就是动态绑定的过程。相对比`invokestatic` 指令加上 `invokespecial` 指令， 就属于静态绑定过程。

 其实我个人认为虚方法其实就是多态的一个体现，那么提到多态的就要讲到一个分派的概念，往下看。。

#### 1、分派

 分派的加其实也是多态的基本的体现，比如**重载** 、**重写**等，分派的话也是有分别的一个是静态分派、另一个是动态分派。

- ##### 静态分派

  静态分派多体现在方法的**重载**（方法名相同但是参数不同）。

   虚拟机（ 准确地说是编译器） 在重载时是通过参数的静态类型而不是实际类型作为判定依据的。 并且静态类型是编译期可知的， 因此， 在编译阶段， Javac 编译器会根据参数的静态类型决定使用哪个重载版本，所有依赖静态类型来定位方法执行版本的分派动作称为静态分派。

   静态分派的典型应用是方法重载。 静态分派发生在编译阶段， 因此确定静态分派的动作实际上不是由虚拟机来执行的

- ##### 动态分派

  动态分派多体现在方法的**重写**（覆盖父类的方法）。

  重写也是使用`invokevirtual`指令， 只是这个时候具备多态性。
  `invokevirtual`指令有多态查找的机制， 该指令运行时， 解析过程如下：

  - 找到操作数栈顶的第一个元素所指向的对象实际类型， 记做 c。
  - 如果在类型 c 中找到与常量中的描述符和简单名称都相符的方法， 则进行访问权限校验， 如果通过则返回这个方法直接引用， 查找过程结束， 不通过则返回 java.lang.IllegalAccessError。
  - 否则， 按照继承关系从下往上依次对 c 的各个父类进行第二步的搜索和验证过程。
  - 如果始终没找到合适的方法， 则抛出 java.lang.AbstractMethodError 异常， 这就是 Java 语言中方法重写的本质。另外一点， 这个时候我如果结合之前课程中讲过虚拟机栈中栈中的内容， 我就知道动态链接是干嘛的。invokevirtual 可以知道方法 call()的符号引用转换是在运行时期完成的， 在方法调用的时候。 部分符号引用在运行期间转化为直接引用,这种转化就是动态链接。

  但是在类关系比较复杂的时候寻找对应数据类型调用的方法就比较麻烦了，于是就有了方法表，那么什么是方法表呢？往下看。。。。。

#### 2、方法表

![img](https://img2020.cnblogs.com/blog/1360865/202103/1360865-20210313170836535-1109458828.png)

 从图上也可以看到，方法表是根据数据类型进行的一个创建，之后找方法的时候就直接按照调用的数据类型区查找方法即可，这样大大的节省了时间。

### 四、语法糖原理

 上面已经讲了四个指令那么第五个指令——`invokedynamic`主要讲的是 JDK 1.8 的 Lambda 表达式，那么我们使用的时候就可以感觉到，可能会感觉这个写的时候感觉很骚，就是好几行的代码可以浓缩成一行，那它底层是怎么实现的呢？ 来来来继续往下看（快没了就这一块了）。。

![img](https://img2020.cnblogs.com/blog/1360865/202103/1360865-20210313170858097-1655540079.png)

#### 1、方法句柄

 这里语法糖的底层实现根 MethodHandle 这个类是密不可分的，大体的实现看代码：

```java
package com.test.demo.method;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandleTest {

    public String find(Object obj) throws Throwable {
        //此处获取lookup，可以看作是一个工厂。
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        
        //这里是获取需要的调用的方法的MethodType，第一个参数是返回值，往后是入参
        MethodType methodType = MethodType.methodType(String.class);
        
        //这里的findxxx可以和相关字节码关联（findStatic、findSpecial、findVirtual）
        MethodHandle sayHi = lookup.findVirtual(obj.getClass(), "sayHi", methodType);
        
        return String.valueOf(sayHi.invoke(obj));
    }

    public static void main(String[] args) throws Throwable {
        MethodHandleTest methodHandleTest = new MethodHandleTest();
        methodHandleTest.find(new MethodTest01());
        methodHandleTest.find(new MethodTest02());
        //运行结果:
        //调用了 MethodTest01 的 sayHi ，返回值：Hi 
        //调用了 MethodTest02 的 sayHi ，返回值：Hi 
    }
}

class MethodTest01{
    String sayHi( ){
        System.out.println("调用了 MethodTest01 的 sayHi ，返回值：Hi ");
        return "Hi";
    }
}

class MethodTest02{
    String sayHi( ){
        System.out.println("调用了 MethodTest02 的 sayHi ，返回值：Hi ");
        return "Hi";
    }
}
我们可以从代码里面看出来其实这里的实现和反射机制很像很像，但是这个的效率对比反射来讲的话效率是比较高的，而且比起反射的话就更简单。
```



#### 2、捕获与非捕获

```java
package com.test.demo.method;

public class Test02 {

    /**
     *
     * @param str
     * 这个方法就属于捕获，因为涉及到了lambda之外的参数。
     */
    public void say(String str){
        ITest01 iTest01 = () -> System.out.println("你好，" + str);
        iTest01.sayHi();
    }

    /**
     * 这个方法属于非捕获，因为没有涉及到其他的参数
     */
    public void say(){
        ITest01 iTest01 = () -> System.out.println("你好，李焕英！");
        iTest01.sayHi();
    }

    public static void main(String[] args) {
        //这里从效率方面来讲的话，毫无疑问肯定是非捕获的效率更高。
        Test02 test02 = new Test02();
        test02.say("李铁蛋");
        test02.say();
    }
}
```

 Lambda 表达式是否是捕获的和性能悄然相关。一个非捕获的 lambda 通常比捕获的更高效， 非捕获的 lambda 只需要计算一次. 然后每次使用到它都会返回一个唯一的实例。 而捕获的 lambda 表达式每次使用时都需要重新计算一次， 而且从目前实现来看， 它很像实例化一个匿名内部类的实例。