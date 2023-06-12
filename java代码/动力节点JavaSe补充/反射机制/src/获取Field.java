import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class 获取Field {
    public static void main(String[] args) {
        Class c1=Integer.class;

        //获取所有public属性
        Field[]fields1=c1.getFields();

        //获取所有属性
        Field[]fields2=c1.getDeclaredFields();

        //输出属性和修饰符
        for(Field fields:fields2){
            System.out.println(fields);
        }

        //只获取属性名
        for(Field fields:fields2){
            System.out.println(fields.getName());
        }

        //获取修饰符
        for(Field fields:fields2){
            //返回的是修饰符代号
            int i=fields.getModifiers();
            String Modi= Modifier.toString(i);

            System.out.println(Modi);
        }

    }
}
