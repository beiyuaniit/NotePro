package sql_server;

/**
 * @author: beiyuan
 * @className: solveSqlInjectionAttack
 * @date: 2022/1/17  9:02
 */

import java.sql.*;

/**
 * PreparedStatement和Statement对比优势
 *      解决了SQL注入
 *      只编译一次，运行多次，速度快点（SQL不变则不会再编译）
 *      在java编译阶段做类型的安全检查（如setString()参数只能是字符串
 * 不足:
 *      如设置字符串时会带''
 * 补充：
 *    增删改查也可以用传值的方式
 *    嫌麻烦可以写个工具类完成连接，释放资源等
 */


/*
JDBC的事务
    自动提交，只要执行一条DML语句，就提交一次
    如：
        ps.executeQuery();//第一次提交
        ps.executeQuery();//第二次提交
设置成手动
    不改变其他，加上这三句话
    conn.setAutoCommit(false);//拿到连接后
    conn.commit();//所有语句完成后
    conn.rollback();//其他情况（出现异常等
 */

public class solveSqlInjectionAttack {
    public static void main(String[] args) throws Exception {

        //从配置文件读取信息
        //配置文件路径是从src根目录出发


        /**
         * 预编译，然后再给SQL语句传值
         * 用户提供的信息不参与SQL语句的编译过程
         */
        PreparedStatement ps=null;//有d
        Connection conn = null;
        try {

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            conn = DriverManager.getConnection("jdbc:sqlserver://127.0.0.1:1433;DatabaseName=beiSql",
                    "sa", "beiyuan3721");


            //准备SQL语句模板.一个？表示占位符，用来传值（用户名，密码）
            String SqL="select *from Student where Sno= ? and Sage=?;";

            //DBMS进行预编译
            ps=conn.prepareStatement(SqL);//动词没有d

            //JDBC中下标从1开始
            ps.setString(1, "21003");
            ps.setString(2,"18");

            ResultSet result = ps.executeQuery();

            if(result.next()){
                System.out.println("登录成功！");
            }else {
                System.out.println("登录失败！");
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ps.close();
            conn.close();
        }
    }
}
