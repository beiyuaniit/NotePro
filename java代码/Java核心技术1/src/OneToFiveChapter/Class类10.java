package OneToFiveChapter;

import java.lang.reflect.InvocationTargetException;

/**
 * @author: beiyuan
 * @className: Class类10
 * @date: 2022/3/7  21:38
 */
public class Class类10 {
    public static void main(String[] args) throws Exception {
        Class c1=String.class;
        Object o=c1.getConstructor().newInstance();
        Class c2=大数04.class;
        String str="f";
        Class c3=str.getClass();

        Class c4=int.class;
    }
}
