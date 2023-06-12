import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class 通过反射访问属性和调用方法 {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchFieldException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException {
        /**
         * 访问属性
         *用属性上的方法进行操作
         */

        Class MYB=myB.class;
        //获取public属性
        Field num=MYB.getField("myNum");

        //获取private属性要打破封装
        Field name=MYB.getDeclaredField("myName");
        name.setAccessible(true);


        //
        Object b=MYB.getConstructor().newInstance();

        //设置属性
        num.set(b,2333);

        //渎属性值
        System.out.println(num.get(b));


        /**
         * 调用方法
         * 用到了可变长参数
         * 可以对Method进行操作
         * 如调用方法，获取名字、返回值、修饰符等
         */

        //获取public方法
        Method sayHi=MYB.getMethod("sayHi",int.class);

        //获取所有以声明的方法getDeclaredMethod()

        //invoke调用非常非常重要
        sayHi.invoke(b,3);

        //获取构造方法
        Constructor con=MYB.getConstructor();
        Constructor Con=MYB.getConstructor(String.class,int.class);

        //调用构造方法
        Object c=Con.newInstance("a",3);

    }
}


class myB{
    private String myName;
    public int myNum;

    public void sayHi(int a){
        System.out.println("HI");
    }

    public myB(String myName, int myNum) {
        this.myName = myName;
        this.myNum = myNum;
        System.out.println("Hello");
    }
    public myB(){

    }
}