import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: beiyuan
 * @className: myAnnotation
 * @date: 2022/3/18  22:48
 */

@Target({ElementType.TYPE,ElementType.METHOD,ElementType.FIELD})//可以被反射
@Retention(RetentionPolicy.RUNTIME)//只能出现在类，方法，属性上
public @interface MyAnnotation{
    /**
     * 注解中可以有属性
     * 使用时
     * 01.属性只有一个且名字是value，可以不指定名字
     * 02.属性是default，可以赋值，也可以不赋值
     * 03.其他情况，志明属性名字并赋值
     */
    String name();

    int num() default 2;//注解中才可以这样使用

    int value() default 4;
}