import java.util.Vector;

public class 数组Vector {
    public static void main(String[] args) {
        /*
        取出时是Object类型的引用，要强制类型转换为合适的类型
         */
        Vector ve=new Vector();
        A a=new A();
        ve.add(4);
        ve.add(a);
        A b=(A)ve.elementAt(1);


        System.out.println(ve.elementAt(0));
        System.out.println(b.a);
    }
}

class A{
    int a=3;
}