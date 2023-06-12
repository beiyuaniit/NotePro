package sql_server;
import java.sql.*;
import java.util.Properties;
import java.io.InputStream;

//如果不成功则一般是驱动的版本不对

/**
 * 用到Statement的场景
 *      asc和desc等不带''的参数用字符串拼接，如淘宝网的升序和降序排列
 *      这些时候即使有SQL注入现象也无所谓
 */

public class main {
    public static void main(String[] args) throws Exception {

        //从配置文件读取信息
        //配置文件路径是从src根目录出发
        InputStream reader = Thread.currentThread().getContextClassLoader().
                getResourceAsStream("sql_server/informtion.properties");
        Properties pro = new Properties();
        pro.load(reader);
        reader.close();

        //注册驱动
        String Driver = pro.getProperty("Driver");

        //数据库地址
        String url = pro.getProperty("url");

        //用户名
        String user = pro.getProperty("user");

        //密码
        String password = pro.getProperty("password");

        //连接数据库try和finally中局部变量不相通
        Statement statement = null;
        Connection conn = null;
        try {

            //1.注册驱动（连接的数据库品牌
            //参数可以写到配置文件中
            Class.forName(Driver);//只要加载该类的动作,执行实现类的静态代码块

            //2.连接数据库（JVM和数据库进程的通道打开
            conn = DriverManager.getConnection(url, user, password);

            //获取sql
            sql Sql = new sql();
            String sqlcreate = Sql.getSqlCreate();
            String sqlquery = Sql.getSqlQuery();
            String sqldelete = Sql.getSqldelete();
            String sqlinsert = Sql.getSqlinsert();

            //3.获取Statement操作对象
            /**
             * 这个接口会有SQL注入问题，采用字符串拼接
             * Statement statement = null;
             */
            statement = conn.createStatement();

            //4.执行sql语句
            //创建表
            //statement.execute(sqlcreate);
            //删除表
            //statement.execute(sqldelete);

            //插入数据
            //statement.execute(sqlinsert);

            //5.查询并返回结果集
            ResultSet result = statement.executeQuery(sqlquery);
            //处理结果
            results.deal(result);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //6.释放资源
            statement.close();
            conn.close();
        }
    }


}

