package mysql;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;

public class main {
    public static void main(String[] args) {
        //jdbc:mysql        协议
        String url="jdbc:mysql://127.0.0.1:3306/beimysql";
        String user="root";
        String password="beiyuan3721";
        try {
            //注册驱动
            //类加载，静态代码块执行，参数是字符串，可以写到配置文件中。
            Class.forName("com.mysql.cj.jdbc.Driver");

            //连接
            //在try里定义就无法在finally使用、关闭
            Connection conn = DriverManager.getConnection(url, user, password);

            String SQl = "select *from student;";

            Statement statement = conn.createStatement();

            ResultSet resultSet = statement.executeQuery(SQl);


            while (resultSet.next()) {
                for (int i = 1; i <= 4; i++){
                    System.out.print(resultSet.getString(i) + " ");
                }
                System.out.println();
            }

            System.out.println(conn);
            conn.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }finally {

        }



    }
}
