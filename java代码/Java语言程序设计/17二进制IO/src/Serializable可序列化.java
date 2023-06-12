import java.io.Serializable;

public class Serializable可序列化 implements Serializable {
    /*
    实现了Serializable接口的类才能序列化（Java API的类都是可序列化的，主要是给自己写的类实现这个接口
     */
    /*
    可序列化的：可以写入输出流的对象,对对象进行闭包时，不存储对象静态变量的值(写入dat配置文件中
              在输出流ObjectOutputStream中实现了对象序列化过程
    反序列化：读取对象
            在ObjectInputStream中实现了对象反序列化
     */

    /*
    可序列化数组：数组中所有元素都是是可序列化的(基本数据类型是可序列化的
     */

    /*
    序列化版本号serialVersionUID：JVM区分同名的类，用于新旧版本的兼容
     */
}
