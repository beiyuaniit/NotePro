# 线程安全类可以在lamda表达式中通过方法改变其值

- 不能直接通过=直接改变变量的值
- 如Integer没有提供改变其值的方法
- 因为方法都是值传递，所以也无法直接通过方法来改变变量的值

```java
EventLoopGroup group=new NioEventLoopGroup(2);
AtomicInteger count=new AtomicInteger(3);
Integer n=0;
group.next().execute(()->{
    //System.out.println(count.incrementAndGet());
	//n=4;//报错，不行
    System.out.println(Thread.currentThread().getName());
});

```

## lamda中不能改变局部变量的值，也不能定义同名的本地变量

- 在Java的线程模型中，栈帧中的局部变量是线程私有的，永远不需要进行同步。假如说允许通过匿名内部类把栈帧中的变量地址泄漏出去（逃逸），就会引发非常可怕的后果：一份“本来被Java线程模型规定永远是线程私有的数据”可能被并发访问！哪怕它不被并发访问，栈中变量的内存地址泄漏到栈帧之外这件事本身已经足够危险了，这是Java这种内存安全的语言绝对无法容忍的
- lamda表达式是一个声明，什么时候执行都有可能，甚至在其他线程中运行
- 但类变量可以在lamda中改变
- 同时函数式编程为了简便性，本来也不打算支持修改外部变量

## Runnable和Callable的区别

(1)Callable规定的方法是call()，Runnable规定的方法是run()。其中Runnable可以提交给Thread来包装下，直接启动一个线程来执行，而Callable则一般都是提交给ExecuteService来执行。 
(2)Callable的任务执行后可返回值，而Runnable的任务是不能返回值得 
(3)call方法可以抛出异常，run方法不可以 
(4)运行Callable任务可以拿到一个Future对象，c表示异步计算的结果。

callable接口实现类中的run方法允许异常向上抛出，可以在内部处理，try catch，但是runnable接口实现类中run方法的异常必须在内部处理，不能抛出

## Thread

方法

```java
Thread.yeild();//让出当前代码所在线程的运行权，回到就绪态，但是可以马上抢占
Thread.currentThread(); //返回当前代码所在的线程
Thread.currentThread().setPriority(); //在线程代码中可以设置优先级，因为此时线程还没有运行

```

join

```java
    @Test
    public void testJoin() throws InterruptedException {
        Runnable runnable=()->{
            System.out.println(Thread.currentThread().getName());

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        };

       Thread t1=new Thread(runnable);
       Thread t2=new Thread(runnable);
       t1.start();
       t2.start();

        //main线程等待t1，t2线程结束
       t1.join();   
       t2.join(2000); //最多等待2000毫秒，过了就向下执行

       System.out.println("t1,t2线程都运行结束");
    }


```

Thread.interrupt()

  1、没有任何语言方面的需求一个被中断的线程应该终止。中断一个线程只是为了引起该线程的注意，被中断线程可以决定如何应对中断。

  2、Thread.interrupt()方法不会中断一个正在运行的线程。

  3、如果线程在调用 Object 类的 wait()、wait(long) 或 wait(long, int) 方法，或者该类的 join()、join(long)、join(long, int)、sleep(long) 或 sleep(long, int) 方法过程中受阻，则其中断状态将被清除，它还将收到一个InterruptedException异常。这个时候，我们可以通过捕获InterruptedException异常来终止线程的执行，具体可以通过return等退出或改变共享变量的值使其退出。

  4、synchronized在获锁的过程中是不能被中断的，意思是说如果产生了死锁，则不可能被中断（请参考后面的测试例子）。与synchronized功能相似的reentrantLock.lock()方法也是一样，它也不可中断的，即如果发生死锁，那么reentrantLock.lock()方法无法终止，如果调用时被阻塞，则它一直阻塞到它获取到锁为止。但是如果调用带超时的tryLock方法reentrantLock.tryLock(long timeout, TimeUnit unit)，那么如果线程在等待时被中断，将抛出一个InterruptedException异常，这是一个非常有用的特性，因为它允许程序打破死锁。你也可以调用reentrantLock.lockInterruptibly()方法，它就相当于一个超时设为无限的tryLock方法。

  5、如果该线程在可中断的通道上的 I/O 操作中受阻，则该通道将被关闭，该线程的中断状态将被设置并且该线程将收到一个 ClosedByInterruptException。这时候处理方法一样，只是捕获的异常不一样而已。

If this thread is blocked in an invocation of the [`wait()`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Object.html#wait()), [`wait(long)`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Object.html#wait(long)), or [`wait(long, int)`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Object.html#wait(long,int)) methods of the [`Object`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Object.html) class, or of the [`join()`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Thread.html#join()), [`join(long)`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Thread.html#join(long)), [`join(long, int)`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Thread.html#join(long,int)), [`sleep(long)`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Thread.html#sleep(long)), or [`sleep(long, int)`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Thread.html#sleep(long,int)) methods of this class, then its interrupt status will be cleared and it will receive an [`InterruptedException`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/InterruptedException.html).



```java
t1.interrupt();  //t1线程收到中断通知

第一种是打断正在运行的t1线程。如下所示，主线程休眠100ms后，中断t1线程，并将t1线程的中断标志设置为true。t1一直在进行while循环，当他发现自己的打断标志为true时，就手动退出。
    
    public static void main(String[] args) throws InterruptedException {
         Thread t1 = new Thread(() -> {
             while (true) {
                 if (Thread.currentThread().isInterrupted()) {
                     System.out.println(Thread.currentThread().getName() + " has been interrupted.");
                     break;
                 }
             }
         });
 
        t1.start();
        TimeUnit.MILLISECONDS.sleep(100);
        t1.interrupt();
    }

第二种情况是，打断正在休眠的t1线程，比如目标线程调用了sleep方法而处于阻塞状态，这时候如果打断他，就会抛出InterruptedException异常。
    public static void main(String[] args) throws InterruptedException {
         Thread t1 = new Thread(() -> {
             try {
                 TimeUnit.MILLISECONDS.sleep(1000);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         });
 
        t1.start();
        TimeUnit.MILLISECONDS.sleep(100);
        t1.interrupt();
    }
```

## 访问临界区

- 不加锁
  - 数量少时可能不容易出错，但数量多时一般都会出错

```java
    private final static Object lock=new Object();
    static int count=0;

    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(()->{
            for(int i=0;i<10003;i++){
                count++;
            }
        },"t1");
        Thread t2=new Thread(()->{
            for(int i=0;i<10000;i++){
                count--;
            }
        },"t2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(count); //2487
    }
```

- 加锁

```java
    private final static Object lock=new Object();
    static int count=0;

    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(()->{
            for(int i=0;i<10003;i++){
                synchronized (lock){
                    count++;
                }
            }
        },"t1");
        Thread t2=new Thread(()->{
            for(int i=0;i<10000;i++){
                synchronized (lock){
                    count--;
                }
            }
        },"t2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(count); //3
    }
```

## ConcurrentHashMap的线程安全性

- ConcurrentHashMap的线程安全指的是**ConcurrentHashMap中的put,get方法是线程安全的,**也就是说在使用这两个方法时不需要额外加锁。但是这并不意味着其他使用到ConcurrentHashMap的地方都不需要加锁。
- 以下不是原子操作，线程不安全

```java
class JobTest {
	private Map<String, Job> jobMap = new ConcurrentHashMap();
	boolean test(Job job) {
		if(jobMap.containsKey(job.getId, job){
			jobMap.put(job.getId,job);
			return true;
		}else{
			log.info("任务已存在");
			return false;
		} 
	}
}
```

-  可以用putIfAbsent()来优化

```java
class JobTest {
	private Map<String, Job> jobMap = new ConcurrentHashMap();
	boolean test(Job job) {
		Job job = jobMap.putIfAbsent(job.getId, job);
		if(job==null){
			return true;
		}else{
			log.info("任务已存在");
			return false;
		} 
	}
}
```



## wait(),notify(),notifyAll()必须在同步（Synchronized）方法/代码块中调用

- （1）为什么wait()必须在同步（Synchronized）方法/代码块中调用？

  答：调用wait()就是释放锁，释放锁的前提是必须要先获得锁，先获得锁才能释放锁。

  （2）为什么notify(),notifyAll()必须在同步（Synchronized）方法/代码块中调用？

  答：notify(),notifyAll()是将锁交给含有wait()方法的线程，让其继续执行下去，如果自身没有锁，
  怎么叫把锁交给其他线程呢；（本质是让处于入口队列的线程竞争锁）

  ------------------------------------------------------------------------
  这是因为调用这三个方法之前必须拿要到当前锁对象的监视器monitor对象，也就是说notify/notifyAll
  和wait方法依赖于monitor对象，又因为monitor存在于对象头的Mark Word中(存储monitor引用指针)，
  而synchronized关键字可以获取monitor ，所以，notify/notifyAll和wait方法必须在
  synchronized代码块或者synchronized方法中调用。

- wait()

  wait是要释放对象锁，进入等待池。
  既然是释放对象锁，那么肯定是先要获得锁。
  所以wait必须要写在synchronized代码块中，否则会报异常。

- notify方法

  也需要写在synchronized代码块中,
  调用对象的这两个方法也需要先获得该对象的锁.
  notify,notifyAll, 唤醒等待该对象同步锁的线程,并放入该对象的锁池中.
  对象的锁池中线程可以去竞争得到对象锁,然后开始执行.

  如果是通过notify来唤起的线程,
  那进入wait的线程会被随机唤醒;
  (注意: 实际上, hotspot是顺序唤醒的!! 这是个重点! 有疑惑的百度: notify()是随机唤醒线程么?)

- **notify()或者notifyAll()调用时并不会真正释放对象锁, 必须等到synchronized方法或者语法块执行完才真正释放锁.**

- void wait(long timeoutMillis)
  调用该方法会使线程进入TIMED_WAITING状态，当等待时间结束或其他线程调用了该对象的notify或notifyAll方法时会将该线程唤醒。

  若唤醒时没有拿到锁，则阻塞并进入等待队列。拿到锁则继续向下执行

  这个方法主要是防止其他线程释放锁了，但是忘记notify来唤醒其他线程。所以wait(long timeoutMillis)时间结束后获得一次竞争锁的机会

```java
    private Object lock=new Object();
    @Test
    public void test01() throws InterruptedException {
        Thread t1=new Thread(()->{
            synchronized (lock){
                System.out.println("t1拿到锁");
                //保证有有线程在等待锁
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                System.out.println("将要释放锁");
                try {
                    lock.wait(3000);
                    System.out.println("hi");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                //拿到锁后再wait释放，等待锁，3秒后不再等待
                System.out.println("wait(3000)在等待3秒后若没有拿到锁，则一直阻塞在这条语句，重新竞争锁");
                System.out.println("那感觉这方法没啥用...不如直接wait()");
            }
        },"t1");

        Thread t2=new Thread(()->{
            //保证后拿锁
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


            synchronized (lock){
                System.out.println("t2拿到锁了");
                try {
                    Thread.sleep(1000*10);//占用锁
                } catch (InterruptedException e) {

                }
                System.out.println("t2将要释放锁");
            }

        },"t2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();
        
        /*
        t1拿到锁
        将要释放锁
        t2拿到锁了
        t2将要释放锁
        hi
        wait(3000)在等待3秒后若没有拿到锁，则一直阻塞在这条语句，重新竞争锁
         */
    }
```



## Java生产者消费者

- synchronized

```java
public class ProducerAndConsumer01 {
    final int SIZE=3;
    //局部变量没有线程安全问题，全局变量才有
    Queue<Integer>queue=new LinkedList<>();

    @Test
    public void testProCon() throws InterruptedException {
        Thread producer=new Thread(()->{
            int i=0;
           while (true){
               synchronized (queue){ //获得锁
                   if(queue.size()==SIZE){
                       try {
                           queue.wait(); //释放锁，进入阻塞
                       } catch (InterruptedException e) {
                           throw new RuntimeException(e);
                       }
                   }
                   queue.add(i++);
                   queue.notify(); //唤醒等待同一锁的一个线程

                   try {
                       TimeUnit.SECONDS.sleep(1);
                   } catch (InterruptedException e) {
                       throw new RuntimeException(e);
                   }
               }
               //同步代码快结束后真正释放锁
           }
        },"producer");

        Thread consumer=new Thread(()->{
            while (true){
                synchronized (queue){
                    if(queue.isEmpty()){
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.println(queue.poll());
                    queue.notify();

                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        },"consumer");

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();
    }
}

```

- https://www.cnblogs.com/xindoo/p/11426659.html
  - 学到后面再补充

## 一些区别

### Thread.sleep()和Object.wait()的区别

首先，我们先来看看Thread.sleep()和Object.wait()的区别，这是一个烂大街的题目了，大家应该都能说上来两点。

（1）Thread.sleep()不会释放占有的锁，Object.wait()会释放占有的锁；

（2）Thread.sleep()必须传入时间，Object.wait()可传可不传，不传表示一直阻塞下去；

（3）Thread.sleep()到时间了会自动唤醒，然后继续执行；

（4）Object.wait()不带时间的，需要另一个线程使用Object.notify()唤醒；

（5）Object.wait()带时间的，假如没有被notify，到时间了会自动唤醒，这时又分好两种情况，一是立即获取到了锁，线程自然会继续执行；二是没有立即获取锁，线程进入同步队列等待获取锁；

其实，他们俩最大的区别就是Thread.sleep()不会释放锁资源，Object.wait()会释放锁资源。

### Thread.sleep()和LockSupport.park()的区别

LockSupport.park()还有几个兄弟方法——parkNanos()、parkUtil()等，我们这里说的park()方法统称这一类方法。

（1）从功能上来说，Thread.sleep()和LockSupport.park()方法类似，都是阻塞当前线程的执行，且**都不会释放当前线程占有的锁资源**；

（2）Thread.sleep()没法从外部唤醒，只能自己醒过来；

（3）LockSupport.park()方法可以被另一个线程调用LockSupport.unpark()方法唤醒；

（4）Thread.sleep()方法声明上抛出了InterruptedException中断异常，所以调用者需要捕获这个异常或者再抛出；

（5）LockSupport.park()方法不需要捕获中断异常；

（6）Thread.sleep()本身就是一个native方法；

（7）LockSupport.park()底层是调用的Unsafe的native方法；

### Object.wait()和LockSupport.park()的区别

二者都会阻塞当前线程的运行，他们有什么区别呢？经过上面的分析相信你一定很清楚了，真的吗？往下看！

（1）Object.wait()方法需要在synchronized块中执行；

（2）LockSupport.park()可以在任意地方执行；

（3）Object.wait()方法声明抛出了中断异常，调用者需要捕获或者再抛出；

（4）LockSupport.park()不需要捕获中断异常【本文由公从号“彤哥读源码”原创】；

（5）Object.wait()不带超时的，需要另一个线程执行notify()来唤醒，但不一定继续执行后续内容；

（6）LockSupport.park()不带超时的，需要另一个线程执行unpark()来唤醒，一定会继续执行后续内容；

（7）**如果在wait()之前执行了notify()会怎样？抛出IllegalMonitorStateException异常**；

（8）**如果在park()之前执行了unpark()会怎样？线程不会被阻塞，直接跳过park()，继续执行后续内容；**

## Unsafe类

Unsafe是Java中一个底层类，包含了很多基础的操作，比如数组操作、对象操作、内存操作、CAS操作、线程(park)操作、栅栏（Fence）操作，JUC包、一些三方框架都使用Unsafe类来保证并发安全。

Unsafe类在jdk 源码的多个类中用到，这个类的提供了一些绕开JVM的更底层功能，基于它的实现可以提高效率。但是，它是一把双刃剑：正如它的名字所预示的那样，它是Unsafe的，它所分配的内存需要手动free（不被GC回收）。Unsafe类，提供了JNI某些功能的简单替代：确保高效性的同时，使事情变得更简单。

Java 不能直接访问操作系统底层，而是通过本地方法来访问。Unsafe 类提供了硬件级别的原子操作。

Unsafe 类在 sun.misc 包下，不属于 Java 标准。很多 Java 的基础类库，包括一些被广泛使用的高性能开发库都是基于 Unsafe 类开发，比如 Netty、Hadoop、Kafka 等。

Unsafe 是用于在实质上扩展 Java 语言表达能力、便于在更高层（Java 层）代码里实现原本要在更低层（C 层）实现的核心库功能用的。
这些功能包括裸内存的申请/释放/访问，低层硬件的 atomic/volatile 支持，创建未初始化对象等。
它原本的设计就只应该被标准库使用，因此不建议在生产环境中使用。

- Unsafe API的大部分方法都是native实现，它由105个方法组成，主要包括以下几类：

（1）Info相关。主要返回某些低级别的内存信息：addressSize(), pageSize()

（2）Objects相关。主要提供Object和它的域操纵方法：allocateInstance(),objectFieldOffset()

（3）Class相关。主要提供Class和它的静态域操纵方法：staticFieldOffset(),defineClass(),defineAnonymousClass(),ensureClassInitialized()

（4）Arrays相关。数组操纵方法：arrayBaseOffset(),arrayIndexScale()

（5）Synchronization相关。主要提供低级别同步原语（如基于CPU的CAS（Compare-And-Swap）原语）：monitorEnter(),tryMonitorEnter(),monitorExit(),compareAndSwapInt(),putOrderedInt()

（6）Memory相关。直接内存访问方法（绕过JVM堆直接操纵本地内存）：allocateMemory(),copyMemory(),freeMemory(),getAddress(),getInt(),putInt()


获取实例
Unsafe 对象不能直接通过 new Unsafe() 或调用 Unsafe.getUnsafe() 获取。

Unsafe 被设计成单例模式，构造方法私有。

getUnsafe 被设计成只能从引导类加载器（bootstrap class loader）加载。

private Unsafe() {
}

public static Unsafe getUnsafe() {
        Class var0 = Reflection.getCallerClass(2);
        if (var0.getClassLoader() != null) {
            throw new SecurityException("Unsafe");
        } else {
            return theUnsafe;
        }
}

非启动类加载器直接调用 Unsafe.getUnsafe() 方法会抛出 SecurityException 异常。

解决办法有两个:

令代码 " 受信任 "
运行程序时，通过 JVM 参数设置 bootclasspath 选项，指定系统类路径加上使用的一个 Unsafe 路径。

java -Xbootclasspath:/usr/jdk1.7.0/jre/lib/rt.jar:. com.mishadoff.magic.UnsafeClient

Java 反射机制
通过将 private 单例实例暴力设置 accessible 为 true，然后通过 Field 的 get 方法，直接获取一个 Object 强制转换为 Unsafe。

Field f = Unsafe.class.getDeclaredField("theUnsafe");
f.setAccessible(true);
Unsafe unsafe = (Unsafe) f.get(null);

在 IDE 中，这些方法会被标志为 Error，可以通过以下设置解决：

Preferences -> Java -> Compiler -> Errors/Warnings -> Deprecated and restricted API -> Forbidden reference -> Warning

常用方法
Unsafe 的大部分 API 都是 native 的方法。

- Classes/Objects 相关
  创建类对象，不进行初始化：

```java
//实例化对象，但是不进行初始化，类初始化和实例初始化都不调用
Object allocateInstance(Class<?> cls)

对象和字段的地址获取：

//字段在内存中的地址相对于实例对象内存地址的偏移量
public long objectFieldOffset(Field f)
public long objectFieldOffset(Class<?> c, String name)
//静态字段在类对象中的偏移
public long staticFieldOffset(Field f)
//获得静态字段所对应类对象，等同于f.getDeclaringClass()
public Object staticFieldBase(Field f)

读取/修改对象上指定偏移位置的值，其他基本数据类型(boolean,char,byte,short,float,double,long)类似：

public native int getInt(Object o, long offset);
public native void putInt(Object o, long offset, int x);

此外还可以读取/设定引用值reference value,指针native pointer

//获得给定对象偏移量上的引用类型的值
public native Object getObject(Object o, long offset);
//设置给定对象偏移量上的引用类型的值
public native void putObject(Object o, long offset, Object x);
//获取、设定本地指针的值，等同于获取、设定int或者是long类型的数据
public long getAddress(Object o, long offset)
public void putAddress(Object o, long offset, long x)

以上方法都是通过两个参数引用到一个变量，叫做double-register地址模式，
当Object引用为null时，把offset作为绝对地址，使用绝对地址的API，叫做single-register地址模式，例如：

public void putInt(long address, int x) {
	putInt(null, address, x);
}

```

​	此时address可以指向通过Unsafe.allocateMemory获取的内存块中的地址，类似于C语言中的指针，直接指明实际要读写的地址

- 数组相关
  数组头部还存储有数组的长度信息，索引访问数组元素时需要知道第一个元素与起始位置的偏移地址，Unsafe类包含所有的基本数据类型和Object类型的偏移的常量值，名称为ARRAY_***_BASE_OFFSET

  同时访问数组第i个元素，对应偏移的确定需要乘以一个比例值，即数组中单个元素的长度，Unsafe中对应的常量为ARRAY_***_INDEX_SCALE

```java
// 初始偏移
public int arrayBaseOffset(Class<?> arrayClass)

public static final int ARRAY_BOOLEAN_BASE_OFFSET
        = theUnsafe.arrayBaseOffset(boolean[].class);

public static final int ARRAY_OBJECT_BASE_OFFSET
        = theUnsafe.arrayBaseOffset(Object[].class);
// 比例值
public int arrayIndexScale(Class<?> arrayClass)

public static final int ARRAY_BOOLEAN_INDEX_SCALE
        = theUnsafe.arrayIndexScale(boolean[].class);

public static final int ARRAY_OBJECT_INDEX_SCALE
        = theUnsafe.arrayIndexScale(Object[].class);

两个信息配合使用，就可以得到数组中每个元素相对内存起点的偏移，然后进行读写

// java8中的ConcurrentHashMap
Class<?> ak = Node[].class;
ABASE = U.arrayBaseOffset(ak);
int scale = U.arrayIndexScale(ak);
ASHIFT = 31 - Integer.numberOfLeadingZeros(scale);

//具体使用，i代表索引
static final <K,V> boolean casTabAt(Node<K,V>[] tab, int i,
									Node<K,V> c, Node<K,V> v) {
	return U.compareAndSwapObject(tab, ((long)i << ASHIFT) + ABASE, c, v);
}
```



- CAS 相关
  compareAndSwap，内存偏移地址 offset，预期值 expected，新值 x。如果变量在当前时刻内存的值和预期值 expected 相等，尝试将变量的值更新为 x。如果更新成功，返回 true；否则，返回 false。

  ```java
  //更新变量值为x，如果当前值为expected
  //o：对象 offset：偏移量 expected：期望值 x：新值
  public final native boolean compareAndSwapObject(Object o, long offset, Object expected, Object x);
  
  public final native boolean compareAndSwapInt(Object o, long offset, int expected, int x);
  
  public final native boolean compareAndSwapLong(Object o, long offset, long expected, long x);
  ```

compareAndSwapObject方法中的第一个参数和第二个参数，用于确定待操作对象在内存中的具体位置的，然后取出值和第三个参数	进行比较，如果相等，则将内存中的值更新为第四个参数的值，同时返回true，表明原子更新操作完毕。反之则不更新内存中的值，同	时返回false，表明原子操作失败。

​	同样的，compareAndSwapInt方法也是相似的道理，第一个，第二个参数用来确定当前操作对象在内存中的存储值，然后和第三个expect value比较，如果相等，则将内存值更新为第四个update value值。

​	JDK 1.8 中基于 CAS 扩展。作用都是，通过 CAS 设置新的值，返回旧的值。

```java
//增加
public final int getAndAddInt(Object o, long offset, int delta) {
 int v;
 do {
 v = getIntVolatile(o, offset);
 } while (!compareAndSwapInt(o, offset, v, v + delta));
 return v;
}

public final long getAndAddLong(Object o, long offset, long delta) {
 long v;
 do {
 v = getLongVolatile(o, offset);
 } while (!compareAndSwapLong(o, offset, v, v + delta));
 return v;
}
//设置
public final int getAndSetInt(Object o, long offset, int newValue) {
 int v;
 do {
 v = getIntVolatile(o, offset);
 } while (!compareAndSwapInt(o, offset, v, newValue));
 return v;
}

public final long getAndSetLong(Object o, long offset, long newValue) {
 long v;
 do {
 v = getLongVolatile(o, offset);
 } while (!compareAndSwapLong(o, offset, v, newValue));
 return v;
}

public final Object getAndSetObject(Object o, long offset, Object newValue) {
 Object v;
 do {
 v = getObjectVolatile(o, offset);
 } while (!compareAndSwapObject(o, offset, v, newValue));
 return v;
```



- ABA 问题
  在多线程环境中，使用 CAS，如果一个线程对变量修改 2 次，第 2 次修改后的值和第 1 次修改前的值相同，其他线程对此一无所知，这类现象称为 ABA 问题。

  ABA 问题可以使用 JDK 并发包中的 AtomicStampedReference 和 AtomicMarkableReference 处理。

  AtomicStampedReference 是通过版本号（时间戳）来解决 ABA 问题的，也可以使用版本号（verison）来解决 ABA，即乐观锁每次在执行数据的修改操作时，都带上一个版本号，一旦版本号和数据的版本号一致就可以执行修改操作并对版本号执行 +1 操作，否则执行失败。

  AtomicMarkableReference 则是将一个 boolean 值作是否有更改的标记，本质就是它的版本号只有两个，true 和 false，修改的时候在两个版本号之间来回切换，虽然这样做并不能解决 ABA 的问题，但是会降低 ABA 问题发生的几率。

- 线程调度相关
  主要包括监视器锁定、解锁等。

  - ```java
    //取消阻塞线程
    public native void unpark(Object thread);
    //阻塞线程
    public native void park(boolean isAbsolute, long time);
    //获得对象锁
    public native void monitorEnter(Object o);
    //释放对象锁
    public native void monitorExit(Object o);
    //尝试获取对象锁，返回 true 或 false 表示是否获取成功
    public native boolean tryMonitorEnter(Object o);
    
    volatile 相关读写
    使用volatile机制加载读取数据，保证可见性，API包含所有的基本数据类型和Object类型
    
    //设置给定对象的int值，使用volatile语义，即设置后立马更新到内存对其他线程可见
    public native void  putIntVolatile(Object o, long offset, int x);
    //获得给定对象的指定偏移量offset的int值，使用volatile语义，总能获取到最新的int值。
    public native int getIntVolatile(Object o, long offset);
    
    此外还有一种惰性设定值的方式，通常出现在AtomicXXX.lazySet，它允许volatile变量和后续的内存操作重排序，就像普通非volatile变量的写操作，所以其他线程可能不能立即获取到新的值
    
    putOrderedObject(Object o, long offset, Object x)
    putOrderedInt(Object o, long offset, int x)
    putOrderedLong(Object o, long offset, long x)
    ```

    



- 内存屏障相关
  JDK 1.8 引入 ，用于定义内存屏障，避免代码重排序。

```java
//内存屏障，禁止 load 操作重排序，即屏障前的load操作不能被重排序到屏障后，屏障后的 load 操作不能被重排序到屏障前
public native void loadFence();
//内存屏障，禁止 store 操作重排序，即屏障前的 store 操作不能被重排序到屏障后，屏障后的 store 操作不能被重排序到屏障前
public native void storeFence();
//内存屏障，禁止 load、store 操作重排序
public native void fullFence();
```

- 内存管理（非堆内存）
  allocateMemory 所分配的内存需要手动 free（不被 GC 回收）。

```java
//（boolean、byte、char、short、int、long、float、double) 都有以下 get、put 两个方法。 
//获得给定地址上的 int 值
public native int getInt(long address);
//设置给定地址上的 int 值
public native void putInt(long address, int x);
//获得本地指针
public native long getAddress(long address);
//存储本地指针到给定的内存地址
public native void putAddress(long address, long x);

//分配指定大小的内存,内存没有被初始化
public long allocateMemory(long bytes)
//根据给定的内存地址address调整内存大小
public long reallocateMemory(long address, long bytes)

//将内存块中的所有字节设置为固定值，类似于C中的memoryset函数
public void setMemory(Object o, long offset, long bytes, byte value)
public void setMemory(long address, long bytes, byte value)

//内存复制，支持两种地址模式
public void copyMemory(Object srcBase, long srcOffset,
                        Object destBase, long destOffset,
                        long bytes)
public void copyMemory(long srcAddress, long destAddress, long bytes)

//释放allocateMemory和reallocateMemory申请的内存
public native void freeMemory(long l);
```



- 系统相关

```java
//返回指针的大小。返回值为 4 或 8。
public native int addressSize();

/** The value of {@code addressSize()} */
public static final int ADDRESS_SIZE = theUnsafe.addressSize();

//内存页的大小。
public native int pageSize();
```

- 其他

```java
//获取系统的平均负载值，loadavg 这个 double 数组将会存放负载值的结果，nelems 决定样本数量，nelems 只能取值为 1 到 3，分别代表最近 1、5、15 分钟内系统的平均负载。
//如果无法获取系统的负载，此方法返回 -1，否则返回获取到的样本数量（loadavg 中有效的元素个数）。
public native int getLoadAverage(double[] loadavg, int nelems);
//绕过检测机制直接抛出异常。
public native void throwException(Throwable ee);
```



- Unsafe 类的使用场景

  - 避免初始化
    当想要绕过对象构造方法、安全检查器或者没有 public 的构造方法时，allocateInstance() 方法变得非常有用。

    ```java
    public class TestA {
        private int a = 0; 
    
        public TestA() {
            a = 1;
        }
        
        public int getA() {
            return a;
        }
    
    }
    
    
    构造方法、反射方法和 allocateInstance 方法的不同实现。
    
    将 public 构造方法修改为 private，allocateInstance 方法可以得到同样的结果。
    
    // constructor
    TestA constructorA = new TestA();
    System.out.println(constructorA.getA()); //print 1
    
    // reflection
    try {
         TestA reflectionA = TestA.class.newInstance();
         System.out.println(reflectionA.getA()); //print 1
    } catch (InstantiationException e) {
         e.printStackTrace();
    } catch (IllegalAccessException e) {
         e.printStackTrace();
    }
    
    // unsafe
    Field f = null;
    try {
        //Unsafe中有这一个属性
        // private static final Unsafe theUnsafe = new Unsafe();
         f = Unsafe.class.getDeclaredField("theUnsafe");
         f.setAccessible(true);
         Unsafe unsafe = (Unsafe) f.get(null);
        //类似于反射创建对象
         TestA unsafeA = (TestA) unsafe.allocateInstance(TestA.class);
         System.out.println(unsafeA.getA()); //print 0
    } catch (NoSuchFieldException e) {
         e.printStackTrace();
    } catch (IllegalAccessException e) {
         e.printStackTrace();
    } catch (InstantiationException e) {
         e.printStackTrace();
    }
    
    ```

    

  - 内存修改

    - Unsafe 可用于绕过安全的常用技术，直接修改内存变量。
      - 反射也可以实现相同的功能。但是 Unsafe 可以修改任何对象，甚至没有这些对象的引用。

    ```java
    public class TestA {
    
        private int ACCESS_ALLOWED = 1;
        
        public boolean giveAccess() {
            return 40 == ACCESS_ALLOWED;
        }
    
    }
    
    在正常情况下，giveAccess 总会返回 false。
    
    通过计算内存偏移，并使用 putInt() 方法，类的 ACCESS_ALLOWED 被修改。
    
    在已知类结构的时候，数据的偏移总是可以计算出来（与 c++ 中的类中数据的偏移计算是一致的）。
    
    // constructor
    TestA constructorA = new TestA();
    System.out.println(constructorA.giveAccess()); //print false
    
    // unsafe
    Field f = null;
    try {
        f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        Unsafe unsafe = (Unsafe) f.get(null);
        TestA unsafeA = (TestA) unsafe.allocateInstance(TestA.class);
        Field unsafeAField = unsafeA.getClass().getDeclaredField("ACCESS_ALLOWED");
        unsafe.putInt(unsafeA, unsafe.objectFieldOffset(unsafeAField), 40); // memory corruption
        System.out.println(unsafeA.giveAccess()); //print true
    } catch (NoSuchFieldException e) {
        e.printStackTrace();
    } catch (IllegalAccessException e) {
        e.printStackTrace();
    } catch (InstantiationException e) {
        e.printStackTrace();
    }
    ```

    

  - 动态类

    - 可以在运行时创建一个类，比如从已编译的 .class 文件中将类内容读取为字节数组，并正确地传递给 defineClass 方法。

      当必须动态创建类，而现有代码中有一些代理，这非常有用。

      ```java
      public class TestA {
      
          private int a = 1;
          
          public int getA() {
              return a;
          }
          
          public void setA(int a) {
              this.a = a;
          }
      
      }
      
      动态创建类。
      
      byte[] classContents = new byte[0];
      try {
            classContents = getClassContent();
            Class c = getUnsafe().defineClass(null, classContents, 0, classContents.length);
            System.out.println(c.getMethod("getA").invoke(c.newInstance(), null)); //print 1
      } catch (Exception e) {
            e.printStackTrace();
      }
      
      private static Unsafe getUnsafe() {
              Field f = null;
              Unsafe unsafe = null;
              try {
                  f = Unsafe.class.getDeclaredField("theUnsafe");
                  f.setAccessible(true);
                  unsafe = (Unsafe) f.get(null);
              } catch (NoSuchFieldException e) {
                  e.printStackTrace();
              } catch (IllegalAccessException e) {
                  e.printStackTrace();
              }
              return unsafe;
      }
      
      private static byte[] getClassContent() throws Exception {
              File f = new File("/home/test/TestA.class");
              FileInputStream input = new FileInputStream(f);
              byte[] content = new byte[(int) f.length()];
              input.read(content);
              input.close();
              return content;
      }
      
      ```

  - 大数组

    - Java 数组大小的最大值为 Integer.MAX_VALUE。使用直接内存分配，创建的数组大小受限于堆大小。

      Unsafe 分配的内存，分配在非堆内存，因为不执行任何边界检查，所以任何非法访问都可能会导致 JVM 崩溃。

      在需要分配大的连续区域、实时编程（不能容忍 JVM 延迟）时，可以使用它。java.nio 使用这一技术。

      ```java
         public class SuperArray {
             private final static int BYTE = 1;
      
      private long size;
      private long address;
      
      public SuperArray(long size) {
          this.size = size;
          address = getUnsafe().allocateMemory(size * BYTE);
      }
      
      public void set(long i, byte value) {
          getUnsafe().putByte(address + i * BYTE, value);
      }
      
      public int get(long idx) {
          return getUnsafe().getByte(address + idx * BYTE);
      }
      
      public long size() {
          return size;
      }
      
      private static Unsafe getUnsafe() {
          Field f = null;
          Unsafe unsafe = null;
          try {
              f = Unsafe.class.getDeclaredField("theUnsafe");
              f.setAccessible(true);
              unsafe = (Unsafe) f.get(null);
          } catch (NoSuchFieldException e) {
              e.printStackTrace();
          } catch (IllegalAccessException e) {
              e.printStackTrace();
          }
          return unsafe;
      	}
      }
      //使用大数组。
      
      long SUPER_SIZE = (long) Integer.MAX_VALUE * 2;
      SuperArray array = new SuperArray(SUPER_SIZE);
      System.out.println("Array size:" + array.size()); //print 4294967294
      int sum = 0;
      for (int i = 0; i < 100; i++) {
           array.set((long) Integer.MAX_VALUE + i, (byte) 3);
           sum += array.get((long) Integer.MAX_VALUE + i);
      }
      System.out.println("Sum of 100 elements:" + sum);  //print 300
      
             
             
      ```

      

  - 并发应用

    - compareAndSwap 方法是原子的，并且可用来实现高性能的、无锁的数据结构。

      ```java
      public class CASCounter {
      private volatile long counter = 0;
      private Unsafe unsafe;
      private long offset;
      
      public CASCounter() throws Exception {
          unsafe = getUnsafe();
          offset = unsafe.objectFieldOffset(CASCounter.class.getDeclaredField("counter"));
      }
      
      public void increment() {
          long before = counter;
          while (!unsafe.compareAndSwapLong(this, offset, before, before + 1)) {
              before = counter;
          }
      }
      
      public long getCounter() {
          return counter;
      }
      
      private static Unsafe getUnsafe() {
          Field f = null;
          Unsafe unsafe = null;
          try {
              f = Unsafe.class.getDeclaredField("theUnsafe");
              f.setAccessible(true);
              unsafe = (Unsafe) f.get(null);
          } catch (NoSuchFieldException e) {
              e.printStackTrace();
          } catch (IllegalAccessException e) {
              e.printStackTrace();
          }
          return unsafe;
        }
      }
      
      
      //使用无锁的数据结构。
      
      public static void main(String[] args) {
              final TestB b = new TestB();
              Thread threadA = new Thread(new Runnable() {
                  @Override public void run() {
                      b.counter.increment();
                  }
              });
              Thread threadB = new Thread(new Runnable() {
                  @Override public void run() {
                      b.counter.increment();
                  }
              });
              Thread threadC = new Thread(new Runnable() {
                  @Override public void run() {
                      b.counter.increment();
                  }
              });
              threadA.start();
              threadB.start();
              threadC.start();
              try {
                  TimeUnit.SECONDS.sleep(1);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
              System.out.println(b.counter.getCounter()); //print 3
      }
      
      private static class TestB {
              private CASCounter counter;
      
              public TestB() {
                  try {
                      counter = new CASCounter();
                  } catch (Exception e) {
                      e.printStackTrace();
                  }
              }
      
      }
      
          
      ```

      

  -  挂起与恢复

    ```java
    public native void unpark(Thread jthread);  
    public native void park(boolean isAbsolute, long time); // isAbsolute 参数是指明时间是绝对的，还是相对的。
    ```

    - 将一个线程进行挂起是通过 park 方法实现，调用 park 后，线程将一直阻塞直到超时或者中断等条件出现。

      unpark 可以终止一个挂起的线程，使其恢复正常。

      整个并发框架中对线程的挂起操作被封装在 LockSupport 类中，LockSupport 类中有各种版本 pack 方法，但最终都调用的 Unsafe.park() 方法。

      unpark 函数为线程提供 " 许可（permit）"，线程调用 park 函数则等待 " 许可 "。

      这个有点像信号量，但是这个 " 许可 " 不能叠加，是一次性的。

      比如线程 B 连续调用了三次 unpark 函数，当线程 A 调用 park 函数就使用掉这个 " 许可 "，如果线程 A 再次调用 park，则进入等待状态。

      ```java
      Thread currThread = Thread.currentThread();
      getUnsafe().unpark(currThread);
      getUnsafe().unpark(currThread);
      getUnsafe().unpark(currThread);
      
      getUnsafe().park(false, 0);
      getUnsafe().park(false, 0);
      System.out.println("execute success"); // 线程挂起，不会打印。
      ```

      

    - unpark 函数可以先于 park 调用（但最好别这样做），比如线程 B 调用 unpark 函数，给线程 A 发了一个 " 许可 "，那么当线程 A 调用 park 时，发现已经有 " 许可 "，会马上再继续运行。

      park 遇到线程终止时，会直接返回（不同于 Thread.sleep，Thread.sleep 遇到 thread.interrupt() 会抛异常）。

      unpark 无法恢复处于 sleep 中的线程，只能与 park 配对使用，因为 unpark 发放的许可只有 park 能监听到。

      因为 park 的特性，可以不用担心 park 的时序问题。

      park / unpark 模型真正解耦了线程之间的同步，线程之间不再需要一个 Object 或者其它变量来存储状态，不再需要关心对方的状态。



## ReentrantLock

jdk中独占锁的实现除了使用关键字synchronized外,还可以使用ReentrantLock。虽然在性能上ReentrantLock和synchronized没有什么区别，但ReentrantLock相比synchronized而言功能更加丰富，使用起来更为灵活，也更适合复杂的并发场景。

 ReentrantLock获取锁定与三种方式：
  a) lock(), 如果获取了锁立即返回，如果别的线程持有锁，当前线程则一直处于休眠状态，直到获取锁

  b) tryLock(), 如果获取了锁立即返回true，如果别的线程正持有锁，立即返回false；

  c)**tryLock**(long timeout,[TimeUnit](http://houlinyan.iteye.com/java/util/concurrent/TimeUnit.html) unit)，  如果获取了锁定立即返回true，如果别的线程正持有锁，会等待参数给定的时间，在等待的过程中，如果获取了锁定，就返回true，如果等待超时，返回false；

  d) lockInterruptibly:如果获取了锁定立即返回，如果没有获取锁定，当前线程处于休眠状态，直到或者锁定，或者当前线程被别的线程中断

### ReentrantLock和synchronized的相同点

- ReentrantLock是独占锁且可重入的，但是同时只有拿到锁的那个线程能运行

- 例子

```csharp
public class ReentrantLockTest {

    public static void main(String[] args) throws InterruptedException {

        ReentrantLock lock = new ReentrantLock();

        for (int i = 1; i <= 3; i++) {
            lock.lock();
        }

        for(int i=1;i<=3;i++){
            try {

            } finally {
                lock.unlock();
            }
        }
    }
}
```

上面的代码通过`lock()`方法先获取锁三次，然后通过`unlock()`方法释放锁3次，程序可以正常退出。从上面的例子可以看出,ReentrantLock是可以重入的锁,当一个线程获取锁时,还可以接着重复获取多次。在加上ReentrantLock的的独占性，我们可以得出以下ReentrantLock和synchronized的相同点。

- 1.ReentrantLock和synchronized都是独占锁,只允许线程互斥的访问临界区。但是实现上两者不同:synchronized加锁解锁的过程是隐式的,用户不用手动操作,优点是操作简单，但显得不够灵活。一般并发场景使用synchronized的就够了；ReentrantLock需要手动加锁和解锁,且解锁的操作尽量要放在finally代码块中,保证线程正确释放锁。ReentrantLock操作较为复杂，但是因为可以手动控制加锁和解锁过程,在复杂的并发场景中能派上用场。
- 2.ReentrantLock和synchronized都是可重入的。synchronized因为可重入因此可以放在被递归执行的方法上,且不用担心线程最后能否正确释放锁；而ReentrantLock在重入时要却确保重复获取锁的次数必须和重复释放锁的次数一样，否则可能导致其他线程无法获得该锁。

### ReentrantLock可以实现公平锁。

公平锁是指当锁可用时,在锁上等待时间最长的线程将获得锁的使用权。而非公平锁则随机分配这种使用权。和synchronized一样，默认的ReentrantLock实现是非公平锁,因为相比公平锁，非公平锁性能更好。当然公平锁能防止饥饿,某些情况下也很有用。在创建ReentrantLock的时候通过传进参数`true`创建公平锁,如果传入的是`false`或没传参数则创建的是非公平锁

```csharp
ReentrantLock lock = new ReentrantLock(true);
```

继续跟进看下源码

```java
/**
 * Creates an instance of {@code ReentrantLock} with the
 * given fairness policy.
 *
 * @param fair {@code true} if this lock should use a fair ordering policy
 */
public ReentrantLock(boolean fair) {
    sync = fair ? new FairSync() : new NonfairSync();
}
```

可以看到公平锁和非公平锁的实现关键在于成员变量`sync`的实现不同,这是锁实现互斥同步的核心。以后有机会我们再细讲。

- 一个公平锁的例子

```csharp
public class ReentrantLockTest {

    static Lock lock = new ReentrantLock(true);

    public static void main(String[] args) throws InterruptedException {

        for(int i=0;i<5;i++){
            new Thread(new ThreadDemo(i)).start();
        }

    }

    static class ThreadDemo implements Runnable {
        Integer id;

        public ThreadDemo(Integer id) {
            this.id = id;
        }

        @Override

      public void run() {
            
            for(int i=0;i<2;i++){
                lock.lock();
                try {
                	TimeUnit.MILLISECONDS.sleep(10);//本身不释放锁，只是等其他线程进入等待队列
            	} 
                catch (InterruptedException e) {
                	e.printStackTrace();
            	}
                System.out.println("获得锁的线程："+id);
                lock.unlock();
            }
        }
    }
}
```

- 公平锁结果
  ![img](https://images2018.cnblogs.com/blog/1422237/201807/1422237-20180719230013861-1075860918.png)

我们开启5个线程,让每个线程都获取释放锁两次。为了能更好的观察到结果,在每次获取锁前让线程休眠10毫秒。可以看到线程几乎是轮流的获取到了锁。如果我们改成非公平锁,再看下结果

- 非公平锁结果
  ![img](https://images2018.cnblogs.com/blog/1422237/201807/1422237-20180719230043730-1158282129.png)

线程会重复获取锁。如果申请获取锁的线程足够多,那么可能会造成某些线程长时间得不到锁。这就是非公平锁的“饥饿”问题。

- 公平锁和非公平锁该如何选择
  大部分情况下我们使用非公平锁，因为其性能比公平锁好很多。但是公平锁能够避免线程饥饿，某些情况下也很有用。

### ReentrantLock可响应中断

当使用synchronized实现锁时,阻塞在锁上的线程除非获得锁否则将一直等待下去，也就是说这种无限等待获取锁的行为无法被中断。而ReentrantLock给我们提供了一个可以响应中断的获取锁的方法`lockInterruptibly()`。该方法可以用来解决死锁问题。

响应中断后会释放锁

- 响应中断的例子

```java
public class ReentrantLockTest {
    static Lock lock1 = new ReentrantLock();
    static Lock lock2 = new ReentrantLock();
    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(new ThreadDemo(lock1, lock2));//该线程先获取锁1,再获取锁2
        Thread thread1 = new Thread(new ThreadDemo(lock2, lock1));//该线程先获取锁2,再获取锁1
        thread.start();
        thread1.start();
        thread.interrupt();//是第一个线程中断
    }

    static class ThreadDemo implements Runnable {
        Lock firstLock;
        Lock secondLock;
        public ThreadDemo(Lock firstLock, Lock secondLock) {
            this.firstLock = firstLock;
            this.secondLock = secondLock;
        }
        @Override
        public void run() {
            try {
                firstLock.lockInterruptibly();
                TimeUnit.MILLISECONDS.sleep(10);//更好的触发死锁
                secondLock.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                firstLock.unlock();
                secondLock.unlock();
                System.out.println(Thread.currentThread().getName()+"正常结束!");
            }
        }
    }
}
```

- 结果
  ![img](https://images2018.cnblogs.com/blog/1422237/201807/1422237-20180719230113736-1002051172.png)

构造死锁场景:创建两个子线程,子线程在运行时会分别尝试获取两把锁。其中一个线程先获取锁1在获取锁2，另一个线程正好相反。如果没有外界中断，该程序将处于死锁状态永远无法停止。我们通过使其中一个线程中断，来结束线程间毫无意义的等待。被中断的线程将抛出异常，而另一个线程将能获取锁后正常结束。

### 获取锁时限时等待（解决死锁

ReentrantLock还给我们提供了获取锁限时等待的方法`tryLock()`,可以选择传入时间参数,表示等待指定的时间,无参则表示立即返回锁申请的结果:true表示获取锁成功,false表示获取锁失败。我们可以使用该方法配合失败重试机制来更好的解决死锁问题。

- 更好的解决死锁的例子

```java
public class ReentrantLockTest {
    static Lock lock1 = new ReentrantLock();
    static Lock lock2 = new ReentrantLock();
    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(new ThreadDemo(lock1, lock2));//该线程先获取锁1,再获取锁2
        Thread thread1 = new Thread(new ThreadDemo(lock2, lock1));//该线程先获取锁2,再获取锁1
        thread.start();
        thread1.start();
    }

    static class ThreadDemo implements Runnable {
        Lock firstLock;
        Lock secondLock;
        public ThreadDemo(Lock firstLock, Lock secondLock) {
            this.firstLock = firstLock;
            this.secondLock = secondLock;
        }
        @Override
        public void run() {
            try {
                while(!lock1.tryLock()){
                    //尝试拿到lock1
                    TimeUnit.MILLISECONDS.sleep(10); 
                }
                while(!lock2.tryLock()){
                    //没有拿到lock2则释放lock1
                    lock1.unlock();
                    TimeUnit.MILLISECONDS.sleep(10);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                firstLock.unlock();
                secondLock.unlock();
                System.out.println(Thread.currentThread().getName()+"正常结束!");
            }
        }
    }
}
```

- 结果
  ![img](https://images2018.cnblogs.com/blog/1422237/201807/1422237-20180719230215064-1644661582.png)

线程通过调用`tryLock()`方法获取锁,第一次获取锁失败时会休眠10毫秒,然后重新获取，直到获取成功。第二次获取失败时,首先会释放第一把锁,再休眠10毫秒,然后重试直到成功为止。线程获取第二把锁失败时将会释放第一把锁，这是解决死锁问题的关键,避免了两个线程分别持有一把锁然后相互请求另一把锁。

### 结合Condition实现等待通知机制

使用synchronized结合Object上的wait和notify方法可以实现线程间的等待通知机制。ReentrantLock结合Condition接口同样可以实现这个功能。而且相比前者使用起来更清晰也更简单。

- Condition使用简介

Condition由ReentrantLock对象创建,并且可以同时创建多个

```csharp
static Condition notEmpty = lock.newCondition();

static Condition notFull = lock.newCondition();
```

Condition接口在使用前必须先调用ReentrantLock的lock()方法获得锁。之后调用Condition接口的await()将释放锁,并且在该Condition上等待,直到有其他线程调用Condition的signal()方法唤醒线程。使用方式和wait,notify类似。

- 一个使用condition的简单例子

```csharp
public class ConditionTest {

    static ReentrantLock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();
    public static void main(String[] args) throws InterruptedException {

        lock.lock();
        new Thread(new SignalThread()).start();
        System.out.println("主线程等待通知");
        try {
            condition.await();  //阻塞并释放锁
        } finally {
            lock.unlock();
        }
        System.out.println("主线程恢复运行");
    }
    static class SignalThread implements Runnable {

        @Override
        public void run() {
            lock.lock();
            try {
                condition.signal(); //唤醒等待的线程
                System.out.println("子线程通知");
            } finally {
                lock.unlock();// 这里才释放锁吧
            }
        }
    }
}
```

- 运行结果
  ![img](https://images2018.cnblogs.com/blog/1422237/201807/1422237-20180719230234682-2105208491.png)

### 使用Condition实现简单的阻塞队列

其实就是一个生产者和消费者模式的队列，未满可以放，未空可以取

阻塞队列是一种特殊的先进先出队列,它有以下几个特点
1.入队和出队线程安全
2.当队列满时,入队线程会被阻塞;当队列为空时,出队线程会被阻塞。

- 阻塞队列的简单实现

```csharp
public class MyBlockingQueue<E> {

    int size;//阻塞队列最大容量

    ReentrantLock lock = new ReentrantLock();

    LinkedList<E> list=new LinkedList<>();//队列底层实现

    Condition notFull = lock.newCondition();//队列满时的等待条件
    Condition notEmpty = lock.newCondition();//队列空时的等待条件

    public MyBlockingQueue(int size) {
        this.size = size;
    }

    public void enqueue(E e) throws InterruptedException {
        lock.lock();
        try {
            while (list.size() ==size)//队列已满,在notFull条件上等待
                notFull.await();
            list.add(e);//入队:加入链表末尾
            System.out.println("入队：" +e);
            notEmpty.signal(); //通知在notEmpty条件上等待的线程
        } finally {
            lock.unlock();
        }
    }

    public E dequeue() throws InterruptedException {
        E e;
        lock.lock();
        try {
            while (list.size() == 0)//队列为空,在notEmpty条件上等待
                notEmpty.await();
            e = list.removeFirst();//出队:移除链表首元素
            System.out.println("出队："+e);
            notFull.signal();//通知在notFull条件上等待的线程
            return e;
        } finally {
            lock.unlock();
        }
    }
}
```

- 测试代码

```java
public static void main(String[] args) throws InterruptedException {

    MyBlockingQueue<Integer> queue = new MyBlockingQueue<>(2);
    for (int i = 0; i < 10; i++) {
        int data = i;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    queue.enqueue(data);
                } catch (InterruptedException e) {

                }
            }
        }).start();

    }
    for(int i=0;i<10;i++){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Integer data = queue.dequeue();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
```

- 运行结果
  ![img](https://images2018.cnblogs.com/blog/1422237/201807/1422237-20180719230254831-1756382721.png)

- 总结

ReentrantLock是可重入的独占锁。比起synchronized功能更加丰富，支持公平锁实现，支持中断响应以及限时等待等等。可以配合一个或多个Condition条件方便的实现等待通知机制。



## synchronized和reentrantlock的区别

​    reentrantlock有更多方法，更加灵活，但是也更容易出错

​	用法不同：synchronized 可以用来修饰普通方法、静态方法和代码块，而 ReentrantLock 只能用于代码块。

​	获取锁和释放锁的机制不同：synchronized 是自动加锁和释放锁的，而 ReentrantLock 需要手动加锁和释放锁。

​	锁类型不同：synchronized 是非公平锁，而 ReentrantLock 默认为非公平锁，也可以手动指定为公平锁。

​	响应中断不同：ReentrantLock 可以响应中断，解决[死锁](https://so.csdn.net/so/search?q=死锁&spm=1001.2101.3001.7020)的问题，而 synchronized 不能响应中断。

​	底层实现不同：synchronized 是 JVM 层面通过监视器实现的，而 ReentrantLock 是基于 AQS 实现的。

- 性能

  - 无可置疑，synchronized的性能确实要比ReentrantLock差个20%-30%，那是不是代码中所有用到synchronized的地方都应该换成lock？ 非也，仔细想想看，ReentrantLock几乎和可以替代任何使用synchronized的场景，而且性能更好，那为什么jdk一直要留着这个关键词呢？而且完全没有任何想要废弃它的想法。
  
    黑格尔说过存在即合理， synchronized因多线程应运而生，它的存在也大幅度简化了Java多线程的开发。没错，它的优势就是使用简单，你不需要显示去加减锁，相比之下ReentrantLock的使用就繁琐的多了，你加完锁之后还得考虑到各种情况下的锁释放，稍不留神就一个bug埋下了。
  
  - 从锁释放角度， Synchronized在JVM层面上实现的，不但可以通过一些监控工具监控 Synchronized的锁定，而且在代码执行岀现异常时，JVM会自动释放锁定；但是使用Locκ则不行，Lock是通过代码实现的，要保证锁定一定会被释放，就必须将unlock()放到finally{}中。
  
    从性能角度，Synchronized早期实现比较低效，对比ReentrantLock，大多数场景性能都相差较大。但是在Java 6中对其进行了非常多的改进，在竞争不激烈时，Synchronized的性能要优于ReetrantLock；在高竞争情况下，Synchronized的性能会下降几十倍，但是ReetrantLock的性能能维持常
  
    

## ThreadLocal



ThreadLocal 概述
ThreadLocal类用来提供线程内部的局部变量，不同的线程之间不会相互干扰
这种变量在多线程环境下访问（通过get和set方法访问）时能保证各个线程的变量相对独立于其他线程内的变量
在线程的生命周期内起作用，可以减少同一个线程内多个函数或组件之间一些公共变量传递的复杂度
使用
常用方法
方法名	描述
ThreadLocal()	创建ThreadLocal对象
public void set( T value)	设置当前线程绑定的局部变量
public T get()	获取当前线程绑定的局部变量
public T remove()	移除当前线程绑定的局部变量，该方法可以帮助JVM进行GC
protected T initialValue()	返回当前线程局部变量的初始值



一、ThreadLocal简介
ThreadLocal叫做线程变量，意思是ThreadLocal中填充的变量属于当前线程，该变量对其他线程而言是隔离的，也就是说该变量是当前线程独有的变量。ThreadLocal为变量在每个线程中都创建了一个副本，那么每个线程可以访问自己内部的副本变量。

ThreadLoal 变量，线程局部变量，同一个 ThreadLocal 所包含的对象，在不同的 Thread 中有不同的副本。这里有几点需要注意：

因为每个 Thread 内有自己的实例副本，且该副本只能由当前 Thread 使用。这是也是 ThreadLocal 命名的由来。
既然每个 Thread 有自己的实例副本，且其它 Thread 不可访问，那就不存在多线程间共享的问题。
ThreadLocal 提供了线程本地的实例。它与普通变量的区别在于，每个使用该变量的线程都会初始化一个完全独立的实例副本。ThreadLocal 变量通常被private static修饰。当一个线程结束时，它所使用的所有 ThreadLocal 相对的实例副本都可被回收。

总的来说，ThreadLocal 适用于每个线程需要自己独立的实例且该实例需要在多个方法中被使用，也即变量在线程间隔离而在方法或类间共享的场景



二、ThreadLocal与Synchronized的区别
ThreadLocal<T>其实是与线程绑定的一个变量。ThreadLocal和Synchonized都用于解决多线程并发访问。

但是ThreadLocal与synchronized有本质的区别：

1、Synchronized用于线程间的数据共享，而ThreadLocal则用于线程间的数据隔离。

2、Synchronized是利用锁的机制，使变量或代码块在某一时该只能被一个线程访问。而ThreadLocal为每一个线程都提供了变量的副本

，使得每个线程在某一时间访问到的并不是同一个对象，这样就隔离了多个线程对数据的数据共享。

而Synchronized却正好相反，它用于在多个线程间通信时能够获得数据共享。

一句话理解ThreadLocal，threadlocl是作为当前线程中属性ThreadLocalMap集合中的某一个Entry的key值Entry（threadlocl,value），虽然不同的线程之间threadlocal这个key值是一样，但是不同的线程所拥有的ThreadLocalMap是独一无二的，也就是不同的线程间同一个ThreadLocal（key）对应存储的值(value)不一样，从而到达了线程间变量隔离的目的，但是在同一个线程中这个value变量地址是一样的。



三、ThreadLocal的简单使用
直接上代码：

public class ThreadLocaDemo {

```java
private static ThreadLocal<String> localVar = new ThreadLocal<String>();
 
static void print(String str) {
    //打印当前线程中本地内存中本地变量的值
    System.out.println(str + " :" + localVar.get());
    //清除本地内存中的本地变量
    localVar.remove();
}
public static void main(String[] args) throws InterruptedException {
 
    new Thread(new Runnable() {
        public void run() {
            ThreadLocaDemo.localVar.set("local_A");
            print("A");
            //打印本地变量
            System.out.println("after remove : " + localVar.get());
           
        }
    },"A").start();
 
    Thread.sleep(1000);
 
    new Thread(new Runnable() {
        public void run() {
            ThreadLocaDemo.localVar.set("local_B");
            print("B");
            System.out.println("after remove : " + localVar.get());
          
        }
    },"B").start();
}
```
}

A :local_A
after remove : null
B :local_B
after remove : null

从这个示例中我们可以看到，两个线程分表获取了自己线程存放的变量，他们之间变量的获取并不会错乱。这个的理解也可以结合图1-1，相信会有一个更深刻的理解。



四、ThreadLocal的原理
要看原理那么就得从源码看起。

  4.1 ThreadLocal的set()方法：
 public void set(T value) {
        //1、获取当前线程
        Thread t = Thread.currentThread();
        //2、获取线程中的属性 threadLocalMap ,如果threadLocalMap 不为空，
        //则直接更新要保存的变量值，否则创建threadLocalMap，并赋值
        ThreadLocalMap map = getMap(t);
        if (map != null)
            map.set(this, value);
        else
            // 初始化thradLocalMap 并赋值
            createMap(t, value);
    }
         从上面的代码可以看出，ThreadLocal  set赋值的时候首先会获取当前线程thread,并获取thread线程中的ThreadLocalMap属性。如果map属性不为空，则直接更新value值，如果map为空，则实例化threadLocalMap,并将value值初始化。

那么ThreadLocalMap又是什么呢，还有createMap又是怎么做的，我们继续往下看。大家最后自己再idea上跟下源码，会有更深的认识。

  static class ThreadLocalMap {

        /**
         * The entries in this hash map extend WeakReference, using
         * its main ref field as the key (which is always a
         * ThreadLocal object).  Note that null keys (i.e. entry.get()
         * == null) mean that the key is no longer referenced, so the
         * entry can be expunged from table.  Such entries are referred to
         * as "stale entries" in the code that follows.
         */
        static class Entry extends WeakReference<ThreadLocal<?>> {
            /** The value associated with this ThreadLocal. */
            Object value;
     
            Entry(ThreadLocal<?> k, Object v) {
                super(k);
                value = v;
            }
        }


​        
​    }
​    
    可看出ThreadLocalMap是ThreadLocal的内部静态类，而它的构成主要是用Entry来保存数据 ，而且还是继承的弱引用。在Entry内部使用ThreadLocal作为key，使用我们设置的value作为value。详细内容要大家自己去跟。

//这个是threadlocal 的内部方法
void createMap(Thread t, T firstValue) {
        t.threadLocals = new ThreadLocalMap(this, firstValue);
    }


    //ThreadLocalMap 构造方法
ThreadLocalMap(ThreadLocal<?> firstKey, Object firstValue) {
            table = new Entry[INITIAL_CAPACITY];
            int i = firstKey.threadLocalHashCode & (INITIAL_CAPACITY - 1);
            table[i] = new Entry(firstKey, firstValue);
            size = 1;
            setThreshold(INITIAL_CAPACITY);
        }
         4.2 ThreadLocal的get方法
      

    public T get() {
        //1、获取当前线程
        Thread t = Thread.currentThread();
        //2、获取当前线程的ThreadLocalMap
        ThreadLocalMap map = getMap(t);
        //3、如果map数据不为空，
        if (map != null) {
            //3.1、获取threalLocalMap中存储的值
            ThreadLocalMap.Entry e = map.getEntry(this);
            if (e != null) {
                @SuppressWarnings("unchecked")
                T result = (T)e.value;
                return result;
            }
        }
        //如果是数据为null，则初始化，初始化的结果，TheralLocalMap中存放key值为threadLocal，值为null
        return setInitialValue();
    }

 


private T setInitialValue() {
        T value = initialValue();
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null)
            map.set(this, value);
        else
            createMap(t, value);
        return value;
    }

4.3 ThreadLocal的remove方法
 public void remove() {
         ThreadLocalMap m = getMap(Thread.currentThread());
         if (m != null)
             m.remove(this);
     }
 remove方法，直接将ThrealLocal 对应的值从当前相差Thread中的ThreadLocalMap中删除。为什么要删除，这涉及到内存泄露的问题。

实际上 ThreadLocalMap 中使用的 key 为 ThreadLocal 的弱引用，弱引用的特点是，如果这个对象只存在弱引用，那么在下一次垃圾回收的时候必然会被清理掉。

所以如果 ThreadLocal 没有被外部强引用的情况下，在垃圾回收的时候会被清理掉的，这样一来 ThreadLocalMap中使用这个 ThreadLocal 的 key 也会被清理掉。但是，value 是强引用，不会被清理，这样一来就会出现 key 为 null 的 value。

ThreadLocal其实是与线程绑定的一个变量，如此就会出现一个问题：如果没有将ThreadLocal内的变量删除（remove）或替换，它的生命周期将会与线程共存。通常线程池中对线程管理都是采用线程复用的方法，在线程池中线程很难结束甚至于永远不会结束，这将意味着线程持续的时间将不可预测，甚至与JVM的生命周期一致。举个例字，如果ThreadLocal中直接或间接包装了集合类或复杂对象，每次在同一个ThreadLocal中取出对象后，再对内容做操作，那么内部的集合类和复杂对象所占用的空间可能会开始持续膨胀。

 4.4、ThreadLocal与Thread，ThreadLocalMap之间的关系  


图4-1 Thread、THreadLocal、ThreadLocalMap之间啊的数据关系图

从这个图中我们可以非常直观的看出，ThreadLocalMap其实是Thread线程的一个属性值，而ThreadLocal是维护ThreadLocalMap

这个属性指的一个工具类。Thread线程可以拥有多个ThreadLocal维护的自己线程独享的共享变量（这个共享变量只是针对自己线程里面共享）



五、ThreadLocal 常见使用场景
如上文所述，ThreadLocal 适用于如下两种场景

1、每个线程需要有自己单独的实例
2、实例需要在多个方法中共享，但不希望被多线程共享
对于第一点，每个线程拥有自己实例，实现它的方式很多。例如可以在线程内部构建一个单独的实例。ThreadLoca 可以以非常方便的形式满足该需求。

对于第二点，可以在满足第一点（每个线程有自己的实例）的条件下，通过方法间引用传递的形式实现。ThreadLocal 使得代码耦合度更低，且实现更优雅。

场景

1）存储用户Session

一个简单的用ThreadLocal来存储Session的例子：

private static final ThreadLocal threadSession = new ThreadLocal();

    public static Session getSession() throws InfrastructureException {
        Session s = (Session) threadSession.get();
        try {
            if (s == null) {
                s = getSessionFactory().openSession();
                threadSession.set(s);
            }
        } catch (HibernateException ex) {
            throw new InfrastructureException(ex);
        }
        return s;
    }
场景二、数据库连接，处理数据库事务

场景三、数据跨层传递（controller,service, dao）

      每个线程内需要保存类似于全局变量的信息（例如在拦截器中获取的用户信息），可以让不同方法直接使用，避免参数传递的麻烦却不想被多线程共享（因为不同线程获取到的用户信息不一样）。

例如，用 ThreadLocal 保存一些业务内容（用户权限信息、从用户系统获取到的用户名、用户ID 等），这些信息在同一个线程内相同，但是不同的线程使用的业务内容是不相同的。

在线程生命周期内，都通过这个静态 ThreadLocal 实例的 get() 方法取得自己 set 过的那个对象，避免了将这个对象（如 user 对象）作为参数传递的麻烦。

比如说我们是一个用户系统，那么当一个请求进来的时候，一个线程会负责执行这个请求，然后这个请求就会依次调用service-1()、service-2()、service-3()、service-4()，这4个方法可能是分布在不同的类中的。这个例子和存储session有些像。

package com.kong.threadlocal;


public class ThreadLocalDemo05 {
    public static void main(String[] args) {
        User user = new User("jack");
        new Service1().service1(user);
    }

}

class Service1 {
    public void service1(User user){
        //给ThreadLocal赋值，后续的服务直接通过ThreadLocal获取就行了。
        UserContextHolder.holder.set(user);
        new Service2().service2();
    }
}

class Service2 {
    public void service2(){
        User user = UserContextHolder.holder.get();
        System.out.println("service2拿到的用户:"+user.name);
        new Service3().service3();
    }
}

class Service3 {
    public void service3(){
        User user = UserContextHolder.holder.get();
        System.out.println("service3拿到的用户:"+user.name);
        //在整个流程执行完毕后，一定要执行remove
        UserContextHolder.holder.remove();
    }
}

class UserContextHolder {
    //创建ThreadLocal保存User对象
    public static ThreadLocal<User> holder = new ThreadLocal<>();
}

class User {
    String name;
    public User(String name){
        this.name = name;
    }
}

执行的结果：

service2拿到的用户:jack
service3拿到的用户:jack

场景四、Spring使用ThreadLocal解决线程安全问题 

我们知道在一般情况下，只有无状态的Bean才可以在多线程环境下共享，在Spring中，绝大部分Bean都可以声明为singleton作用域。就是因为Spring对一些Bean（如RequestContextHolder、TransactionSynchronizationManager、LocaleContextHolder等）中非线程安全的“状态性对象”采用ThreadLocal进行封装，让它们也成为线程安全的“状态性对象”，因此有状态的Bean就能够以singleton的方式在多线程中正常工作了。 

一般的Web应用划分为展现层、服务层和持久层三个层次，在不同的层中编写对应的逻辑，下层通过接口向上层开放功能调用。在一般情况下，从接收请求到返回响应所经过的所有程序调用都同属于一个线程，如图9-2所示。 





 

这样用户就可以根据需要，将一些非线程安全的变量以ThreadLocal存放，在同一次请求响应的调用线程中，所有对象所访问的同一ThreadLocal变量都是当前线程所绑定的。

下面的实例能够体现Spring对有状态Bean的改造思路：




代码清单9-5  TopicDao：非线程安全


public class TopicDao {
   //①一个非线程安全的变量
   private Connection conn; 
   public void addTopic(){
        //②引用非线程安全变量
	   Statement stat = conn.createStatement();
	   …
   }
由于①处的conn是成员变量，因为addTopic()方法是非线程安全的，必须在使用时创建一个新TopicDao实例（非singleton）。下面使用ThreadLocal对conn这个非线程安全的“状态”进行改造： 

代码清单9-6  TopicDao：线程安全 


import java.sql.Connection;
import java.sql.Statement;
public class TopicDao {

  //①使用ThreadLocal保存Connection变量
private static ThreadLocal<Connection> connThreadLocal = new ThreadLocal<Connection>();
public static Connection getConnection(){
         
	    //②如果connThreadLocal没有本线程对应的Connection创建一个新的Connection，
	    //并将其保存到线程本地变量中。
if (connThreadLocal.get() == null) {
			Connection conn = ConnectionManager.getConnection();
			connThreadLocal.set(conn);
              return conn;
		}else{
              //③直接返回线程本地变量
			return connThreadLocal.get();
		}
	}
	public void addTopic() {

		//④从ThreadLocal中获取线程对应的
	     Statement stat = getConnection().createStatement();
	}

不同的线程在使用TopicDao时，先判断connThreadLocal.get()是否为null，如果为null，则说明当前线程还没有对应的Connection对象，这时创建一个Connection对象并添加到本地线程变量中；如果不为null，则说明当前的线程已经拥有了Connection对象，直接使用就可以了。这样，就保证了不同的线程使用线程相关的Connection，而不会使用其他线程的Connection。因此，这个TopicDao就可以做到singleton共享了。 

当然，这个例子本身很粗糙，将Connection的ThreadLocal直接放在Dao只能做到本Dao的多个方法共享Connection时不发生线程安全问题，但无法和其他Dao共用同一个Connection，要做到同一事务多Dao共享同一个Connection，必须在一个共同的外部类使用ThreadLocal保存Connection。但这个实例基本上说明了Spring对有状态类线程安全化的解决思路。在本章后面的内容中，我们将详细说明Spring如何通过ThreadLocal解决事务管理的问题。


## Volatile和内存屏障

- 1.volatile内存语义
  volatile修饰的变量拥有两大特性：可见性、有序性。

  当写一个volatile变量时，JMM会把该线程对应的本地内存中的共享变量立即刷新回到主内存中。

  当读一个volatile变量时，JMM会把该线程对应的工作内存设置为无效，直接从主内存中读取共享变量。

  所以volatile的写内存语义是直接刷新到主内存中，读的内存语义是直接从主内存中读取。

  一句话，volatile修饰的变量在某个线程的工作内存中修改之后会立刻刷新到主内存，并把其他线程的工作内存中的该变量设置为无效。

  那么为什么volatile能够保证上面所说的可见性、有序性呢？？？   答案：内存屏障（Memory Barrier）

- 2.内存屏障
  回顾volatile的两大特性：可见性：立即刷新回主内存+失效处理。        有序性：禁止指令重排，存在数据依赖关系的指令禁止重排。（也即重排之后的指令不能发改变原程序的串行语义）

内存屏障（也称内存栅栏，内存栅障，屏障指令等，是一类同步屏障指令，是CPU或编译器在对内存随机访问的操作中的一个同步点，使得此点之前的所有读写操作都执行后才可以开始执行此点之后的操作），避免代码重排序。内存屏障其实就是一种JVM指令，Java内存模型的重排规则会要求Java编译器在生成JVM指令时插入特定的内存屏障指令 ，通过这些内存屏障指令，volatile实现了Java内存模型中的可见性和有序性，但volatile无法保证原子性 。

内存屏障之前的所有写操作都要回写到主内存，内存屏障之后的所有读操作都能获得内存屏障之前的所有写操作的最新结果 (实现了可见性)。

写屏障(Store Memory Barrier) ：告诉处理器在写屏障之前将所有存储在缓存(store buffer es) 中的数据同步到主内存。也就是说当看到Store屏障指令， 就必须把该指令之前所有写入指令执行完毕才能继续往下执行。
读屏障(Load Memory Barrier) ：处理器在读屏障之后的读操作， 都在读屏障之后执行。也就是说在Load屏障指令之后就能够保证后面的读取数据指令一定能够读取到最新的数据。

1. 阻止屏障两边的指令重排序。

2. 写数据时加入屏障，强制将线程私有工作内存的数据刷回主物理内存。

3. 读数据时加入屏障，线程私有工作内存的数据失效，重新到主物理内存中获取最新数据。

内存屏障可以有以下几种分类：↓↓↓

2.1 粗分两种：写屏障
2.2 粗分两种：读屏障
写屏障：在写指令之后插入写屏障，强制把写缓冲区的数据刷回到主内存中。

读屏障：在读指令之前插入读屏障，让工作内存或CPU高速缓存当中的缓存数据失效，重新回到主内存中获取最新数据。

2.3 细分四种
针对第一种类型，一定要保证Load1读取在Load2读取之前，这也就保证了有序性，同时进行指令重排，Load2绝对不能排到Load1之前执行。

![img](https://img-blog.csdnimg.cn/b6c5f28397f548fc81f2354dedbef970.png)

1.  重排序有可能影响程序的执行和实现， 因此， 我们有时候希望告诉JVM你别“自作聪明”给我重排序， 我这里不需要排序， 听主人的。

2.  对于编译器的重排序， JMM会根据重排序的规则， 禁止特定类型的编译器重排序。

3. 对于处理器的重排序， Java编译器在生成指令序列的适当位置， 插入内存屏障指令， 来禁止特定类型的处理器排序。

   对volatile变量的读和写？

当第一个操作为volatile读时，不论第二个操作是什么，都不能重排序。这个操作保证了volatile读之后的操作不会被重排到volatile读之前。
当第二个操作为volatile写时，不论第一个操作是什么，都不能重排序。这个操作保证了volatile写之前的操作不会被重排到volatile写之后。
当第一个操作为volatile写时，第二个操作为volatile读时，不能重排。
在每个volatile读操作的后面插入一个LoadLoad屏障
在每个volatile读操作的后面插入一个LoadStore屏障。
在每个volatile写操作的前面插入一个StoreStore屏障：可以保证在volatile写之前，其前面的所有普通写操作都已经刷新到主内存中。
在每个volatile写操作的后面插入一个StoreLoad屏障：避免与后续可能出现的volatile读写操作发生重排序。



3.volatile可见性介绍 
保证不同线程对某个变量完成操作后结果及时可见，即该共享变量一旦改变所有线程立即可见。

package com.szh.demo.volatiles;

import java.util.concurrent.TimeUnit;

public class VolatileDemo1 {
    private static boolean flag = true;

    public static void main(String[] args) {
        new Thread(() ->{
            System.out.println(Thread.currentThread().getName() + " ---- come in");
            while (flag) {
     
            }
            System.out.println(Thread.currentThread().getName() + " ---- 检测到flag被修改为false，停止运行");
        }, "t1").start();
     
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
     
        flag = false;
     
        System.out.println(Thread.currentThread().getName() + " ---- 修改了flag为false");
    }
}


通过上面的案例，我们可以看到，在main主线程中修改了flag为false，但是t1线程并不知道，因为针对flag变量，t1线程在最初启动执行时，将它从主内存中拷贝一份到自己的工作内存，而它后续一直看到的都是自己工作内存中这个flag变量的副本，永远都只是true；main主线程修改了，t1并不知道。

解决：将flag改为volatile修饰，做到在main主线程修改了flag为false之后，会立马将这个变量刷新回主内存，而此时t1工作内存中的flag变量就失效了，它会重新到主内存中读取新值。 

private static volatile boolean flag = true;


read: 作用于主内存，将变量的值从主内存传输到工作内存，主内存到工作内存 （了解即可。。。）
load: 作用于工作内存，将read从主内存传输的变量值放入工作内存变量副本中，即数据加载
use: 作用于工作内存，将工作内存变量副本的值传递给执行引擎，每当JVM遇到需要该变量的字节码指令时会执行该操作
assign: 作用于工作内存，将从执行引擎接收到的值赋值给工作内存变量，每当JVM遇到一个给变量赋值字节码指令时会执行该操作
store: 作用于工作内存，将赋值完毕的工作变量的值写回给主内存
write: 作用于主内存，将store传输过来的变量值赋值给主内存中的变量
由于上述6条只能保证单条指令的原子性，针对多条指令的组合性原子保证，没有大面积加锁，所以，JVM提供了另外两个原子指令：

lock: 作用于主内存，将一个变量标记为一个线程独占的状态，只是写时候加锁，就只是锁了写变量的过程。
unlock: 作用于主内存，把一个处于锁定状态的变量释放，然后才能被其他线程占用
4.volatile无原子性介绍
volatile变量的复合操作不具有原子性，比如number++。

package com.szh.demo.volatiles;

import java.util.concurrent.TimeUnit;

class NumberDemo {
    int number;

    public synchronized void add() {
        number++;
    }
}

public class VolatileDemo2 {
    public static void main(String[] args) {
        NumberDemo numberDemo = new NumberDemo();
        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    numberDemo.add();
                }
            }, String.valueOf(i)).start();
        }

        //睡2秒，确保上面10个线程的add操作可以执行完毕
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
     
        System.out.println(numberDemo.number);
    }
}
上面的代码运行结果没啥疑问：就是 10000。

那么如果我们将number改为 volatile 修饰，就会出现问题。（程序运行结果就不会是10000了）

对于volatile变量具备可见性 ，JVM只是保证从主内存加载到线程工作内存的值是最新的，也仅是数据加载时是最新的。但是多线程环境下，“数据计算”和“数据赋值”操作可能多次出现，若数据在加载之后，若主内存volatile修饰变量发生修改之后，线程工作内存中的操作将会作废，去读主内存最新值，操作出现写丢失问题。即各线程私有内存和主内存公共内存中变量不同步 ，进而导致数据不一致。由此可见volatile解决的是变量读取时的可见性问题，但无法保证原子性，对于多线程修改主内存共享变量的场景必须使用加锁同步。

volatile不适合参与到依赖当前值的运算，如i=i+1，i++之类的
那么依靠可见性的特点volatile可以用在哪些地方呢？通常volatile用作保存某个状态的boolean值或int值。 （一旦布尔值被改变迅速被看到，就可以做其他操作）

由于volatile变量只能保证可见性，在不符合以下两种规则的运算场景中，我们仍然需要通过锁机制（synchronized、Lock、原子类）来保证原子性。

        ① 运算结果并不依赖变量的当前值，或者能够确保只有单一的线程修改变量的值。
    
       ② 变量不需要与其他的状态变量共同参与不变约束。

5.volatile有序性介绍
重排序是指编译器和处理器为了优化程序性能而对指令序列进行重新排序的一种手段，有时候会改变程序语句的先后顺序。不存在数据依赖关系，可以重排序；

但重排后的指令绝对不能改变原有的串行语义！这点在并发设计中必须要重点考虑！



编译器优化的重排序： 编译器在不改变单线程串行语义的前提下，可以重新调整指令的执行顺序。
指令级并行的重排序： 处理器使用指令级并行技术来讲多条指令重叠执行，若不存在数据依赖性，处理器可以改变语句对应机器指令的执行顺序。
内存系统的重排序： 由于处理器使用缓存和读/写缓冲区，这使得加载和存储操作看上去可能是乱序执行。

数据依赖性 ：若两个操作访问同一变量，且这两个操作中有一个为写操作，此时两操作间就存在数据依赖性。

public class VolatileTest {
    int i = 0;
    volatile boolean flag = false;

    public void write() {
        i = 2; //假如不加volatile，这两句话的顺序就有可能颠倒，影像最终结果
        flag = true;
    }
     
    public void read() {
        if (flag) {
            System.out.println("---i = " + i);
        }
    }
}

 



6.如何正确使用volatile？
6.1 单一赋值可以，但是含有符合运算赋值不可以（比如i++）
下面这两个单一赋值可以的

volatile int a = 10；

volatile boolean flag = false；  状态标志，判断业务是否结束。

6.2 开销较低的读，写锁策略
当读远多于写。最土的方法就是加两个synchronized，但是读用volatile，写用synchronized可以提高性能。

public class UseVolatileDemo {
    // 使用：当读远多于写，结合使用内部锁和 volatile 变量来减少同步的开销
    // 理由：利用volatile保证读取操作的可见性；利用synchronized保证复合操作的原子性
    private volatile int value;

    public int getValue() {
        return value;   //利用volatile保证读取操作的可见性
    }
     
    public synchronized int increment() {
        return value++; //利用synchronized保证复合操作的原子性
    }
}

6.3 DCL双锁案例
public class SafeDoubleCheckSingleton {
    //通过volatile声明，实现线程安全的延迟初始化。
    private static volatile SafeDoubleCheckSingleton singleton;

    //私有化构造方法
    private SafeDoubleCheckSingleton() {
    }
     
    //双重锁设计
    public static SafeDoubleCheckSingleton getInstance() {
        if (singleton == null) {
            //1.多线程并发创建对象时，会通过加锁保证只有一个线程能创建对象
            synchronized (SafeDoubleCheckSingleton.class) {
                if (singleton == null) {
                    //隐患：多线程环境下，由于重排序，该对象可能还未完成初始化就被其他线程读取
                    //原理:利用volatile，禁止 "初始化对象"(2) 和 "设置singleton指向内存空间"(3) 的重排序
                    singleton = new SafeDoubleCheckSingleton();
                }
            }
        }
        //2.对象创建完毕，执行getInstance()将不需要获取锁，直接返回创建对象
        return singleton;
    }
}

7.小总结
volatile写之前的的操作，都禁止重排到volatile之后
volatile读之后的操作，都禁止重排到volatile之前
volatile写之后volatile读，禁止重排序

## CAS

CAS是Compare And Set的一个简称，如下理解：

- 要配合volatile使用，cas保证了原子性，但不保证可见性

1，已知当前内存里面的值current和预期要修改成的值new传入

2，内存中AtomicInteger对象地址对应的真实值(因为有可能别修改)real与current对比，

   相等表示real未被修改过，是“安全”的，将new赋给real结束然后返回；不相等说明real已经被修改，结束并重新执行1直到修改成功 

CAS相比Synchronized，避免了锁的使用，总体性能比Synchronized高很多.

例子

```java
    public final int incrementAndGet() {
        for (;;) {
            //获取当前值
            int current = get();
            //设置期望值
            int next = current + 5;
            //调用Native方法compareAndSet，执行CAS操作
            if (compareAndSet(current, next))  //会拿current跟内存中的当前值比较
                //成功后才会返回期望值，否则无线循环
                return next;
        }
    }
```

## AtomicIntegerFieldUpdater

- AtomicReferenceFieldUpdater // 域 字段
- AtomicIntegerFieldUpdater
- AtomicLongFieldUpdate
- 假设现在有这样的一个场景： 一百个线程同时对一个int对象进行修改，要求只能有一个线程可以修改。

```java
    private static int a = 100;
    private static  volatile boolean ischanged = false;
    public static void main(String[] args){
        for(int i=0; i<100;i++){
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    if(!ischanged){
                        ischanged = true;
                        a = 120;
                    }
                }
            });
            t.start();
        }
    }
```

- 对于volatile变量，写的时候会将线程本地内存的数据刷新到主内存上，读的时候会将主内存的数据加载到本地内存里，所以可以保证可见行和单个读/写操作的原子性。但是上例中先 1. 判断!ischanged 2.ischanged=true 该组合操作就不能保证原子性了，也就是说线程A A1->A2 线程B B1->B2(第一个操作为volatile读或者第二个操作为volatile写的时候，编译器不会对两个语句重排序，所以最后的执行顺序满足顺序一致性模型的)，但是最后的执行结果可能是A1->B1->A2->B2。不满足需求
- AtomicIntegerFieldUpdater: 基于反射的工具，可用CompareAndSet对volatile int进行原子更新:

```java
public class Test{
    private static AtomicIntegerFieldUpdater<Test> update = AtomicIntegerFieldUpdater.newUpdater(Test.class, "a");
    private static Test test = new Test();
    public volatile int a = 100;
    public static void main(String[] args){
        for(int i=0; i<100;i++){
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    if(update.compareAndSet(test, 100, 120)){
                        System.out.print("已修改");
                    }
                }
            });
            t.start();
        }
    }
}
```



## LongAdder

- AtomicLong是JDK1.5开始出现的，里面主要使用了一个long类型的value作为成员变量。它的原理是依靠底层CAS方式来保障原子性的更新数据，在要增加或减少数据时，会使用死循环不断地CAS到特定的值，从而达到更新数据的目的，在并发很高的情况下，这将产生很多的无用空循环，浪费CPU资源。

- ongAdder继承了Striped64类，来实现累加功能的，它是实现高并发累加的工具类；

  Striped64的设计核心思路就是通过内部的分散计算来避免竞争。

  Striped64内部包含一个`base`数值和一个`Cell[]`类型的 cells数组，又叫hash表。没有竞争的情况下，要累加的数通过cas累加到base上；如果有竞争的话，会将要累加的数累加到Cells数组中的某个cell元素里面，在获取当前累加总计时，才会将base值加上cells数组中的各个元素值来计算出当前sum总值。

一、原子累加器
我们都知道，原子整型可以在线程安全的前提下做到累加功能，而今天介绍的LongAdder具有更好的性能

我们先来看原子累加器和原子整型做累加的对比使用：

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
    private static <T> void demo(Supplier<T> supplier, Consumer<T> action){
        T adder = supplier.get();
        long start = System.nanoTime();
        List<Thread> ts = new ArrayList<>();
        for (int i = 0;i<40;i++){
            ts.add(new Thread(()->{
                for (int j = 0;j<500000;j++){
                    action.accept(adder);
                }
            }));
        }
        ts.forEach(t->t.start());
        ts.forEach(t->{
            try {
                t.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        });
    }
 
    public static void main(String[] args) {
        for (int i = 0;i<5;i++){
            demo(()->new LongAdder(),longAdder -> longAdder.increment());
        }
        for (int i = 0;i<5;i++){
            demo(()->new AtomicLong(),atomicLong -> atomicLong.getAndIncrement());
        }
    }
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

 

通过上面的代码运行我们可以清晰地看到，两种方式都有效的产完成了累加的效果，但是明显使用累加器的效率要更好，甚至要高出原子类型累加好几倍。

现在，我们可以简单地理解为，**原子累加器就是JAVA在并发编程下提供的有保障的累加手段。**

二、LongAdder执行原理
**LongAdder之所以性能提升这么多，就是在有竞争时，设置多个累加单元，不同线程累加不同的累加单元，最后在将其汇总，减少了cas重试失败，从而提高了性能。**

简单来说，累加器就是将需要做累加的共享变量，分成许多部分，时多个线程只累加自己的部分（这样做既可以减少使用普通整型容易出现的线程不安全错误，也可以提高原子类型在累加时效率底下的问题），足以后再将结果汇总，得到累加结果。

就比如银行要清点大量钞票，一个人来清点效率低下，所以需要多个人来（多个线程），将大量钞票分给多个员工（分配累加单元），每个员工仅仅需要对自己的那部分钞票清点（每个线程累加自己的累加单元），最后将结果汇总起来，就是所有钞票的总数。

LongAdder中有几个关键域：

```
transient volatile Cell[] cells;//累加单元数组，懒惰初始化
transient volatile long base;//基础值，如果是单线程没有竞争，则用cas累加这个域
transient volatile int cellsBusy;//zaicell创建或扩容时，置位1，表示加锁
```


其中cells就是累加单元，用来给各个线程分配累加任务。

base用来做单线程的累加，同时还有汇总的作用，也是就是说base=cells[0]+cells[1]……+base

cellBusy则用来表示加锁置位，0表示无锁，1表示加锁。

注意：
此处cellsbusy所说的锁并不是真正的对象锁，而是底层用cas来模拟加锁。

cas模拟加锁：

当占用某资源需要模拟加锁时，cas会将某处标志位的初始态变为加锁态（如将0改为1）。此时当出现竞争时，其他线程同样会尝试cas操作，但均以失败告终，所以会不停尝试cas，起到了加锁的功效。

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
@sun.misc.Contended static final class Cell {
        volatile long value;
        Cell(long x) { value = x; }
        final boolean cas(long cmp, long val) {
            return UNSAFE.compareAndSwapLong(this, valueOffset, cmp, val);
        }
 
        // Unsafe mechanics
        private static final sun.misc.Unsafe UNSAFE;
        private static final long valueOffset;
        static {
            try {
                UNSAFE = sun.misc.Unsafe.getUnsafe();
                Class<?> ak = Cell.class;
                valueOffset = UNSAFE.objectFieldOffset
                    (ak.getDeclaredField("value"));
            } catch (Exception e) {
                throw new Error(e);
            }
        }
    }
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

 

在Cell类源码中我们能看出，cell底层也是采用cas来做累加计数。

三、伪共享
我们都知道**cpu内存模型**

**因为 CPU 与内存的速度差异很大,需要靠预读数据至缓存来提升效率。而缓存以缓存行为单位,每个缓存行对应着一块内存,一般是64 byte (8个 long )**

**缓存的加入会造成数据副本的产生,即同一份数据会缓存在不同核心的缓存行中。**

**CPU 要保证数据的一致性,如果某个 CPU 核心更改了数据,其它 CPU 核心对应的整个缓存行必须失效**

也就是说，在同一缓存行中的缓存的内容时时刻刻都是相同的，这样就违背了我们cells的初衷。

 ![img](https://img2023.cnblogs.com/blog/667853/202212/667853-20221225224029273-894849779.png)

 

 

如图，

**因为cells是数组类型，导致他们在内存中始终处于连续存储状态。**

**当任何一个线程改变cell中的值时，另一个线程中的缓存必然会失效（缓存是以行为单位进行更新），这样繁杂的操作使得效率大大降低。**

问题解决
@ sun . misc . Contended 用来解决这个问题,

**它的原理是在使用此注解的对象或字段的前后各增加128字节大小的 padding 从而让 CPU 将对象预读至缓存时占用不同的缓存行,这样,不会造成对方缓存行的失效。**

**也就是说，增加无用的内存空间是cells扩容，从而在缓存中，每一个cell能占据一个缓存行，也就解决了失效的问题。**

## ThreadFactory

- 一些常用的作用

1. 给线程命名，查看创建线程数

2. 给线程设置是否是后台运行

3. 设置线程优先级

   

在学习Java的JUC框架时，线程池是无法避免的话题，通过线程池的应用，我们能够很大程度上提升性能。那么，在线程池中，ThreadFactory的作用到底是什么呢？

我们来看下ThreadFactory的具体内容：

public interface ThreadFactory {
    Thread newThread(Runnable r);
}
可以看到，ThreadFactory中仅含一个方法newThread(Runnable r)，顾名思义，也就是依据指定的Runnable接口来创建并返回一个Thread。

也就是说，在线程池中，所有线程的创建都是通过ThreadFactory.newThread方法来完成的。

在创建线程池时，如果不指定ThreadFactory，则程序将为我们提供一个默认的ThreadFactory，也即DeafaultThreadFactory，其内容如下：

    static class DefaultThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;
     
        DefaultThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                                  Thread.currentThread().getThreadGroup();
            namePrefix = "pool-" +
                          poolNumber.getAndIncrement() +
                         "-thread-";
        }
     
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                                  namePrefix + threadNumber.getAndIncrement(),
                                  0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
## 关于异常

通过对ThreadPoolExecutor类分析，引发java.util.concurrent.RejectedExecutionException主要有两种原因：

线程池显示的调用了shutdown()之后，再向线程池提交任务的时候，如果你配置的拒绝策略是ThreadPoolExecutor.AbortPolicy的话，这个异常就被会抛出来。
当你的排队策略为有界队列，并且配置的拒绝策略是ThreadPoolExecutor.AbortPolicy，当线程池的线程数量已经达到了maximumPoolSize的时候，你再向它提交任务，就会抛出ThreadPoolExecutor.AbortPolicy异常。

- 分类
  - 有界队列：就是有固定大小的队列。比如设定了固定大小的 LinkedBlockingQueue，又或者大小为 0，只是在生产者和消费者中做中转用的 SynchronousQueue。
  - 无界队列：指的是没有设置固定大小的队列。这些队列的特点是可以直接入列，直到溢出。当然现实几乎不会有到这么大的容量（超过 Integer.MAX_VALUE），所以从使用者的体验上，就相当于 “无界”。比如没有设定固定大小的 LinkedBlockingQueue。

- ArrayBlockingQueue

  数组阻塞队列是一个使用数组实现的有界队列，数组根据FIFO原则对元素进行排序。它还支持将生产者线程和使用者线程排队的可选公平性策略，这不保证默认情况下对线程的公平访问，并且可以公平地构造。公平减少了吞吐量，但减少了可变性，并避免了“不平衡”。

- LinkedBlockingQueue

  这是一个使用链表实现的有界阻塞队列。默认长度和最大长度为整数最大值。队列还根据FIFO原则对元素进行排序，以确定线程执行的顺序。

- PriorityBlockingQueue

  这是一个支持优先级的无边界的阻塞队列。默认情况下，采用自然升序，也可以通过构造函数指定comparator对元素进行排序。但它不能保证相同优先元素的顺序。

  底层是采用二叉最大堆来实现优先级排序的。

- DelayQueue

  这是一个支持延迟获取元素的无边界阻塞队列，其队列使用优先级队列PriorityQueue实现。队列中的元素必须实现延迟的接口。创建元素时，可以指定从队列中检索元素所需的时间，并且只能在元素过期时指定。

  主要用于缓存，例如清除缓冲区中超时的数据。它还用于调度定时任务。

  创建元素时，首先初始化延迟的接口；然后实现getDelay（TimeUnit Unit）方法，返回的值是当前元素需要延迟多长时间；最后实现compareTo（delayed other）方法以指定元素的顺序。

  当使用者从队列中检索元素时，如果元素未达到延迟时间，则当前线程将被阻塞。此外，leader变量被设置为表示等待获取队列头元素的线程。如果前导不为空，则表示已经准备好等待获取队列头元素，并且使用wait（）方法使当前线程等待信号。如果leader为空，则将当前线程设置为leader，并使用waitnanos（）方法使当前线程等待接收到的信号或延迟时间。

- SynchronousQueue

  与其他阻塞队列不同，这是一个不存储元素的阻塞队列。每个Put操作必须等待Take操作，否则不能继续添加元素，反之亦然。它分为公平访问队列和不公平访问队列，默认情况下由不公平策略访问。

  队列本身不存储任何元素，并且适用于可传递的场景。它将生产者线程处理的数据直接传输到使用者线程。它的吞吐量高于链接阻塞队列和数组阻塞队列。

- LinkedTransferQueue

  这是由链表结构组成的FIFO的无边界阻塞传输队列。它采用先发制人的方式，即有先发制人的方式时，直接采取先发制人的方式，并占据该位置，直到得到该位置，超时或中断为止。与其他阻塞队列相比，有更多的TryTransfer方法和传输方法。

  - `transfer(e,[timeout,unit])` 方法: 如果当前有消费者正等待接收元素，该方法可以把生产者传入的元素立刻传输给消费者。如果没有消费者等待，该方法将元素存放在队列的 tail 节点，等到该元素被消费者消费了才返回。
  - `tryTransfer(e,[timeout,unit])`方法： 试探生产者传入的元素是否能直接传给消费者。如果没有消费者等待接收元素，返回false。该方法无论消费者是否接收都立即返回，而 transfer 方法必须等消费了才返回。

- LinkedBlockingDeque

  是一个由链表组成的双向阻塞队列。可以从队列两端插入和移除元素。

## 常用线程池

-  可通过ExecutorService来获取java提供的线程池

### CachedThreadPool

> CachedThreadPool 是通过 java.util.concurrent.Executors 创建的 ThreadPoolExecutor 实例。这个实例会根据需要，在线程可用时，重用之前构造好的池中线程。这个线程池在执行 **大量短生命周期的异步任务时（many short-lived asynchronous task）**，可以显著提高程序性能。调用 **execute** 时，可以重用之前已构造的可用线程，如果不存在可用线程，那么会重新创建一个新的线程并将其加入到线程池中。如果线程超过 60 秒还未被使用，就会被中止并从缓存中移除。因此，线程池在长时间空闲后不会消耗任何资源。

注意队列实例是：new SynchronousQueue()

```java
	/**
	 * Creates a thread pool that creates new threads as needed, but
	 * will reuse previously constructed threads when they are
	 * available.  These pools will typically improve the performance
	 * of programs that execute many short-lived asynchronous tasks.
	 * Calls to <tt>execute</tt> will reuse previously constructed
	 * threads if available. If no existing thread is available, a new
	 * thread will be created and added to the pool. Threads that have
	 * not been used for sixty seconds are terminated and removed from
	 * the cache. Thus, a pool that remains idle for long enough will
	 * not consume any resources. Note that pools with similar
	 * properties but different details (for example, timeout parameters)
	 * may be created using {@link ThreadPoolExecutor} constructors.
	 *
	 * @return the newly created thread pool
	 */
	public static ExecutorService newCachedThreadPool() {
	    return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
	                                  60L, TimeUnit.SECONDS,
	                                  new SynchronousQueue<Runnable>());
	}
```

### FixedThreadPool

> FixedThreadPool 是通过 java.util.concurrent.Executors 创建的 ThreadPoolExecutor 实例。这个实例会复用 **固定数量的线程** 处理一个 **共享的无边界队列** 。任何时间点，最多有 nThreads 个线程会处于活动状态执行任务。如果当所有线程都是活动时，有多的任务被提交过来，那么它会一致在队列中等待直到有线程可用。如果任何线程在执行过程中因为错误而中止，新的线程会替代它的位置来执行后续的任务。所有线程都会一致存于线程池中，直到显式的执行 ExecutorService.shutdown() 关闭。

注意队列实例是：new LinkedBlockingQueue()

```java
    /**
     * Creates a thread pool that reuses a fixed number of threads
     * operating off a shared unbounded queue.  At any point, at most
     * <tt>nThreads</tt> threads will be active processing tasks.
     * If additional tasks are submitted when all threads are active,
     * they will wait in the queue until a thread is available.
     * If any thread terminates due to a failure during execution
     * prior to shutdown, a new one will take its place if needed to
     * execute subsequent tasks.  The threads in the pool will exist
     * until it is explicitly {@link ExecutorService#shutdown shutdown}.
     *
     * @param nThreads the number of threads in the pool
     * @return the newly created thread pool
     * @throws IllegalArgumentException if {@code nThreads <= 0}
     */
    public static ExecutorService newFixedThreadPool(int nThreads) {
        return new ThreadPoolExecutor(nThreads, nThreads,
                                      0L, TimeUnit.MILLISECONDS,
                                      new LinkedBlockingQueue<Runnable>());
    }
```

### SingleThreadPool

> SingleThreadPool 是通过 java.util.concurrent.Executors 创建的 ThreadPoolExecutor 实例。这个实例只会使用单个工作线程来执行一个无边界的队列。（注意，如果单个线程在执行过程中因为某些错误中止，新的线程会替代它执行后续线程）。它可以保证认为是按顺序执行的，任何时候都不会有多于一个的任务处于活动状态。和 **newFixedThreadPool(1)** 的区别在于，如果线程遇到错误中止，它是无法使用替代线程的。

```java
    /**
     * Creates an Executor that uses a single worker thread operating
     * off an unbounded queue. (Note however that if this single
     * thread terminates due to a failure during execution prior to
     * shutdown, a new one will take its place if needed to execute
     * subsequent tasks.)  Tasks are guaranteed to execute
     * sequentially, and no more than one task will be active at any
     * given time. Unlike the otherwise equivalent
     * <tt>newFixedThreadPool(1)</tt> the returned executor is
     * guaranteed not to be reconfigurable to use additional threads.
     *
     * @return the newly created single-threaded Executor
     */
    public static ExecutorService newSingleThreadExecutor() {
        return new FinalizableDelegatedExecutorService
            (new ThreadPoolExecutor(1, 1,
                                    0L, TimeUnit.MILLISECONDS,
                                    new LinkedBlockingQueue<Runnable>()));
    }
```



## 线程池参数

```java
public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue,
                              ThreadFactory threadFactory,
                              RejectedExecutionHandler handler)
```

所有线程池最终都是通过这个方法来创建的。

corePoolSize : 核心线程数，一旦创建将不会再释放。如果创建的线程数还没有达到指定的核心线程数量，将会继续创建新的核心线程，直到达到最大核心线程数后，核心线程数将不在增加；如果没有空闲的核心线程，同时又未达到最大线程数，则将继续创建非核心线程；如果核心线程数等于最大线程数，则当核心线程都处于激活状态时，任务将被挂起，等待空闲线程来执行。

maximumPoolSize : 最大线程数，允许创建的最大线程数量。如果最大线程数等于核心线程数，则无法创建非核心线程；如果非核心线程处于空闲时，超过设置的空闲时间，则将被回收，释放占用的资源。

keepAliveTime : 也就是当线程空闲时，所允许保存的最大时间，超过这个时间，线程将被释放销毁，但只针对于非核心线程。

unit : 时间单位，TimeUnit.SECONDS等。

workQueue : 任务队列，存储暂时无法执行的任务，等待空闲线程来执行任务。

threadFactory :  线程工程，用于创建线程。

handler : 当线程边界和队列容量已经达到最大时，用于处理阻塞时的程序


## ThreadPoolExecutor 和ExecutorService

**Executor** 和 **ThreadPoolExecutor** 实现的是**线程池**，主要作用是**支持高并发**的访问处理。

Executor 是一个接口，与线程池有关的大部分类都实现了此接口。

ExecutorService 是 Executor 的子接口；AbstractExecutorService 是 ExecutorService 的实现类，但是是抽象类。

ThreadPoolExecutor 是 AbstractExecutorService 的子类，可实例化。

Executors 是一个工厂类，用于创建线程池。

(1) Executors 的 使用：

　　（1） **newCachedThreadPool**() 返回 ExecutorService 实例，**创建无界限线程池**，理论上线程最大个数是 Integer.MAX_VALUE，

（2） **newFixedThreadPool**(int) 返回 ExecutorService 实例，**创建的是有界线线程池，**即线程个数可以指定最大数量，如果超过最大数量，则后加入的线程需要等待

　（3） **newSingleThreadExecutor**() 创建**单一线程池**，实现**以队列的方式来执行**任务。

 2、ThreadPoolExecutor 的使用：

　　Executors 工厂类中，很多地方是调用的 ThreadPoolExecutor 来实现的，单独调用 ThreadPoolExecutor 要考虑的相对要多一些但也更灵活



不如直接用Executor好理解

```java
@Test
public void test03(){
    Executor executor=Executors.newCachedThreadPool();
    for (int i=0;i<40;i++){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        });
    }
}
```

当然ExecutorService有更多方法，如关闭的executorService.shutdown();