## 本地方法栈

- Java虚拟机栈用于管理Java方法的调用，而本地方法栈用于管理本地方法的调用。本地方法栈，也是线程私有的。
- 本地方法栈允许被实现成固定或者是可动态扩展的内存大小。如果线程请求分配的栈容量超过本地方法栈允许的最大容量，Java虚拟机将会抛出一个StackOverflowError异常；如果本地方法可以动态扩展，并且在尝试扩展的时候无法申请到足够的内存，或者在创建新的线程时没有足够的内存去创建对应的本地方法栈，那么Java虚拟机将抛出一个outofMemoryError异常。
- 本地方法是使用C语言实现的，它的具体做法是Native Method Stack中登记native方法，在Execution Engine执行时加载本地方法库。
- 当某个线程调用一个本地方法时，它就进入了一个全新的并且不受虚拟机限制的世界，它和虚拟机拥有同样的权限。本地方法可以通过本地方法接口来访问虚拟机内部的运行时数据区，它甚至可以直接使用本地处理器中的寄存器，直接从本地内存的堆中分配任意数量的内存。
- 不是所有的JVM都支持本地方法，因为Java虚拟机规范没有明确要求本地方法栈的使用语言、具体实现方式、数据结构等。如果JVM产品不打算支持native方法，也可以无需实现本地方法栈。在Hotspot JVM中，直接将本地方法栈与虚拟机栈合二为一。

- 本地方法
  一个Native Method就是一个Java调用非Java代码的接口。
  Native Method是这样一个Java方法：该方法的实现由非Java语言实现，比如C。这个特征并非Java特有，很多其他的编程语言都有这一机制，比如在C++中可以用 extern "C" 告知C++编译器去调用一个C的函数
  本地方法接口的作用是融合不同的编程语言为Java所用，它的初衷是融合C/C++程序

  标识符native可以与其他的Java标识符连用，但是abstract除外

- 为什么要使用Native Method？
  与Java环境外交互：有时Java应用需要与Java外面的环境交互，这是本地方法存在的主要原因。例如Java需要与一些底层系统如操作系统或某些硬件交换信息时的情况。本地方法正是这样一种交流机制：它为我们提供一个非常简洁的接口，而且我们无需去了解Java应用之外的繁琐的细节。
  与操作系统交互：JVM由一个解释器（解释字节码）和一些连接到本地代码的库组成。然而不管怎样，它毕竟不是一个完整的系统，经常依赖于一些底层系统的知识，这些底层系统常常是强大的操作系统。通过使用本地方法，我们得以用Java实现jre与底层系统的交互，甚至JVM的一部分就是用C写的
  Sun's Java：Sun的解释器是用C实现的，这使得它能与外部交互，jre大部分是用Java实现的，它也通过一些本地方法与外界交互

- 现状
  目前该方法使用的越来越少了，除非是与硬件有关的应用，比如通过Java程序驱动打印机或Java系统管理生产设备，在企业级应用中已经比较少见。因为现在的异构领域间的通信很发达，比如可以使用Socket通信，也可以使用Web Service等等

## 常用命令

- jsp
  - 查看jvm运行的进程。类似于linux的ps
- jmap  [参数] pid
  - 查看进程的相关信息
- jconsole
  - 启动java监视和管理控制台（图形化界面
- javac
  - javac F:\Thread_study\src\com\nyima\JVM\day01\Main.java
  - 编译java源文件为.class
- javap 
  - javap -v F:\Thread_study\src\com\nyima\JVM\day01\Main.class
  - 在控制台输出反编译后的代码

## 五种引用的使用场景

- 1、强引用
  特点

  一般而言直接创建的对象，都是强引用指向的，只要有强引用指向的对象，

  当内存空间不足，JVM宁愿抛出OutOfMemoryError错误，使程序异常终止，也不会靠随意回收具有强引用的对象来解决内存不足的问题，使用最普遍

  只要还有强引用指向一个对象，垃圾收集器就不会回收这个对象。

  显式地设置 置引用为 null，或者超出对象的生命周期，此时就可以回收这个对象。具体回收时机还是要看垃圾收集策略。

  使用场景

  该变量在有引用指向的时候，不希望被垃圾回收器回收

  String a = "111"

  // 指向字符串常量"111"的是一个强引用
  // 注：只要给强引用对象赋空值null，该对象就可被垃圾回收器回收

- 2、软引用（SoftReference）
  特点

  当内存不足时，垃圾回收器会回收

  应用场景

  内存中不重要的数据缓存

  图片缓存。图片缓存框架中，“内存缓存”中的图片是以这种引用保存，使得 JVM 在发生 OOM 之前，可以回收这部分缓存。

  网页缓存。

  // 1. 声明强引用
  User user = new User("张三"); 

  // 2. 对user进行软引用
  ReferenceQueue<User> userQue=new ReferenceQueue<>(); 
  SoftReference<User> userRef=new SoftReference<>(user,userQue); 

  // 3. 撤掉强引用
  user = null;

  // 4. 取出软引用的对象
  User u1= userRef.get(); 


  注意：

  1、 软引用可与1个引用队列使用

  2、若软引用所引用的对象被垃圾回收器回收，JVM就会把这个软引用加入到与之关联的引用队列中

  3、也可以不与引用队列一起使用

- 3、弱引用（weakReference）
  特点

  只能存活到下一次GC发现之前

  GC中，一旦发现弱引用对象，无论内存足否，都会进行回收

  使用场景

  避免内存泄漏

  如果类 B 不是虚引用类 A 的话，执行 main 方法会出现内存泄漏的问题， 因为类 B 依然依赖于 A，因为GC回收不了，强引用对象。在实际场景中，如果一个对象一段时间后，一直得不到释放，会造成内存泄漏

  public class Main {
      public static void main(String[] args) {

          A a = new A();
          B b = new B(a);
          a = null;
          System.gc();
          System.out.println(b.getA());  // null
      
      }

  }

  class A {}

  class B {

      WeakReference<A> weakReference;
      
      public B(A a) {
          weakReference = new WeakReference<>(a);
      }
      
      public A getA() {
          return weakReference.get();
      }

  }

- 4、虚引用（Phantom Reference）
  虚引用并不会决定对象的生命周期。如果一个对象仅持有虚引用，那么它就和没有任何引用一样，在任何时候都可能被垃圾回收器回收。

  虚引用与软引用和弱引用的一个区别在于：虚引用必须和引用队列（ReferenceQueue）联合使用。当垃圾回收器准备回收一个对象时，如果发现它还有虚引用，就会在回收对象的内存之前，把这个虚引用加入到与之关联的引用队列中。

  Object obj = new Object();
  ReferenceQueue refQueue = new ReferenceQueue();
  PhantomReference<Object> phantomReference = new PhantomReference<Object>(obj,refQueue);

  
  使用场景：

  可以用来跟踪对象呗垃圾回收的活动。一般可以通过虚引用达到回收一些非java内的一些资源比如堆外内存的行为。例如：在 DirectByteBuffer 中，会创建一个 PhantomReference 的子类Cleaner的虚引用实例用来引用该 DirectByteBuffer 实例，Cleaner 创建时会添加一个 Runnable 实例，当被引用的 DirectByteBuffer 对象不可达被垃圾回收时，将会执行 Cleaner 实例内部的 Runnable 实例的 run 方法，用来回收堆外资源。
  