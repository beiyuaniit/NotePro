import java.lang.annotation.*;

public class 注解的定义和使用 {
    public static void main(String[] args) throws ClassNotFoundException {
        Class c=Class.forName("A");

        //判断该类是否有@myAnnotation
        boolean ishave=c.isAnnotationPresent(MyAnnotation.class);
        System.out.println(ishave);

        //取得注解上的信息
        MyAnnotation myAnno= (MyAnnotation) c.getAnnotation(MyAnnotation.class);

        System.out.println(myAnno.name());


        //其他用法：判断被注解内容是否合理，
        //如某个类上是否有name这个变量：通过反射来判断1.是否有注解2.是否有该变量


    }
}

@MyAnnotation(name = "admin")
class A{
}