public class 静态变量常量和方法 {

    public static void main(String[] args) {

    }
}

//同一包下不能有相同的类
class Circle1{
    //所有对象共享
    static int a=2;

    //常量共享且不可以更改(事先赋值
    final static int b=3;

    /*
    共用的方法，不被对象所特有
    只能访问类中的静态数据
    无法访问类中的非静态成员变量和方法 （实例变量和实例方法）
    因为没有this，也就是没有对象存在（不能用this，因为静态变量不属于对象
    要想访问得先创建该类的对象（实例化），通过对象名访问
    不是对象特有的一般定义为静态，如工具类
    */
    static int getA(){
        return a;
    }

}
