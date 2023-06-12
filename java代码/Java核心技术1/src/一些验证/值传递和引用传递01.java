package 一些验证;

/**
 * @author: beiyuan
 * @className: 值传递和引用传递01
 * @date: 2022/3/8  23:04
 */
public class 值传递和引用传递01 {
    public static void main(String[] args) {
        int num=1;
        A.change(num);
        System.out.println(num);//1。值传递的是一个副本，不改变原值

        num=A.change1(num);
        System.out.println(num);//2。可以通过返回值获得新值

        A a=new A();
        System.out.println(a);
        B.changeA(a);
        System.out.println(a);
        //一些验证.A@b4c966a
        //一些验证.A@b4c966a        .引用本身也是复制一份。不会改变原值


    }


}

class A{
    public int a=3;
    public static void change(int a){
        a+=1;
    }

    public static int change1(int a){
        a+=1;
        return a;
    }
}

class B{
    public static void changeA(A a){
        a=new A();
    }
}