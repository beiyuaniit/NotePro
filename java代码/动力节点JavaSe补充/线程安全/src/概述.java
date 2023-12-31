public class 概述 {
    /**
     * 存在线程安全问题
     *      多线程并发
     *      有共享数据（是实例变量或者静态变量，但不会是局部变量，常量
     *      共享数据有修改行为
     *
     * 2个线程使用了同一个对象
     *      可以用有参构造传递同一个对象
     */


    /**
     * 同步编程模型
     *      线程排队执行
     *
     * 异步编程模型
     *      多线程并发
     *      互不影响
     */

    /**
     * 对象锁和synchronized
     *      每个对象都有一把锁（其实是一个标记
     *      线程遇到synchronized 同步代码块时，会去锁池找该对象的锁
     *      找到了就去执行同步代码，执行完毕后归还对象锁
     *      没找到就在锁池中等待，直到有线程把对象锁归还
     */

    /**
     * 针对的是同一个对象，局部变量每次调用时都会new新的对象，所以不是同一个对象
     */

    /**
     * 使用
     * 01
     * synchronized (){
     *
     * }
     * 小括号中要填共享对象（只要是唯一的就能够锁住
     * 填有线程安全问题的线程共享的对象就可以了
     * 一般填this：对象锁
     * 也可以”abc“字符串常量：变成了类锁，所有线程所有该类对象都共享
     *
     * 02
     * synchronized出现在实例方法上(整个方法体同步
     * 锁的是this
     *
     * 03
     * 在静态方法上使用synchronized (不能锁住静态变量
     * 锁的是该类，是类锁
     * 类锁永远只有一把
     * 为了保证静态变量的安全
     */


    /**
     * 如果使用局部变量，常量
     *      建议使用java提供的线程不安全的类和方法（效率高
     *
     * 实例变量和静态变量
     *      建议使用线程安全的
     *
     *
     */

    /**
     * 死锁
     * 两个线程互相等待
     */
}
