import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class 通过反射创建对象 {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException {
        /**
         * 实例化对象
         * JVM加载的时.class字节码文件
         */

        //此处调用的是无参构造方法
        Class c1=String.class;
        Object obj=c1.newInstance();

        //通过配置文件创建对象
        /*
        low点的
         String path=Thread.currentThread().getContextClassLoader().
                getResource("class.properties").getPath();
        FileReader reader=new FileReader(path);

        下面高级点以流形式直接返回
         */

        InputStream reader=Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("class.properties");//路径是相对于Module下的src为根路径

        //FileReader reader=new FileReader("class.properties");
        Properties pro=new Properties();
        pro.load(reader);
        reader.close();

        Class c2=Class.forName(pro.getProperty("className"));
        //创建对象的类本身要存在
        Constructor con=c2.getConstructor(String.class);
        Object obj1 =con.newInstance("Hello World");

        System.out.println(obj1.toString());
        System.out.println(obj1 instanceof String);//true。但是吧，本质还是Object

        if(obj1 instanceof  String){
            String obj2=(String)obj1;
            System.out.println(obj2.charAt(0));
        }




    }
}

