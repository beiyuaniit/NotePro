public class super关键字 {
    //子类中调用父类的构造方法和方法,必须放在构造方法的第一句
    //如果没有显式调用，编译器会自动调用继承链所有父类的构造方法
    B bo=new B(3,4);
    int a=bo.getB();
}

class A{
    int a;

    //父类的构造方法
    public A(int a) {
        this.a = a;
    }

    //父类的普通方法
    public int doubleA(int a){
        return a*2;
    }
}

class B extends A {

    int b;

    //调用父类的构造方法
    public B(int a, int b) {
        super(a);
        this.b = b;
    }

    public int getB() {
        //调用父类的普通方法
        int a=super.doubleA(3);
        return b;
    }
}