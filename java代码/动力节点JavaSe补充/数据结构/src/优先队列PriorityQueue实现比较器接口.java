
import java.util.PriorityQueue;


public class 优先队列PriorityQueue实现比较器接口 {



    public static void main(String[] args) {
        /*
        一般都在声明集合时确定引用类型
         */
        PriorityQueue<B> Que=new PriorityQueue<>();
        B b=new B();

        Que.add(b);
        Que.remove();

    }
}


class B implements Comparable<B> {
    /*
    在比较的类内实现之一都行
    比较规则复杂：Comparator
    规则简单且只有一个：Comparable接口
     */
    int a;
    @Override
    public int compareTo(B o) {
        return o.a-this.a;
    }
}

