import java.util.Collection;


public class 集合 {
    public static void main(String[] args) {
        /*
        Collection接口
        集合是一个容器，存放对象的引用(基本类型会自动装箱
        集合用数据结构实现
        存放的是Object类型引用且同一个集合可以存放不同的引用类型
         */

        /*
        Vector：动态括容数据，线程安全，效率低点
        ArrayList：动态扩容数组，线程不安全，效率高点
        LinkedList：双向链表，线程不安全

        Stack：栈，Vector子类
        Queue:队列
        PriorityQueue:优先队列
                      存放引用数据类型要自定义比较器Comparator

        Map接口
        Hashtable:哈希表，线程安全
        HashMap：哈希表，线程不安全
                不允许重复的key
                链地址法处理哈希冲突：位桶+链表+红黑树

        HashSet：存储元素等于HashMap的key部分，无序不重复(只有key，没有value
                不允许重复的值(所以要重写equals和hashCode方法

        TreeMap：二叉排序树，按照key排序
        TreeSet：存储了TreeMap的key部分，无序不重复（只有key，没有value

        Properties：HashMap，线程安全，key和value只能是String

         */
    }
}
