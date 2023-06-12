import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class IO和Properties联合 {
    public static void main(String[] args) throws IOException {
        /*
        动态获取配置文件中的信息，=左边是key，=右边是value
        属性配置文件后缀一般是.properties
         */
        FileReader reader=new FileReader("17二进制IO/userinfo");
        Properties pro=new Properties();

        //将流加载进去
        pro.load(reader);

        //通过key取出value
        String username=pro.getProperty("username");
        String password=pro.getProperty("password");
        System.out.println(username);
    }
}
