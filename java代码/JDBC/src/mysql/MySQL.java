package mysql;

import java.sql.*;
import java.util.ResourceBundle;

/**
 * @author: beiyuan
 * @className: MySQL
 * @date: 2022/3/20  13:22
 */
public class MySQL {
    public static void main(String[] args) {


    //public void connection(){
        Connection con=null;//放在外面为了在finally中可以关闭
        Statement stmt=null;
        ResultSet rs=null;

        ResourceBundle bundle=ResourceBundle.getBundle("mysql");//src为根路径


        String dirver=bundle.getString("driver");
        String url=bundle.getString("url");
        String user=bundle.getString("user");
        String password=bundle.getString("password");


        try{
            //注册驱动
            Class.forName(dirver);
            //获取连接
            con=DriverManager.getConnection(url,user,password);
            //获取数据库操作对象
            stmt=con.createStatement();
            //执行SQL。可以不用分号结尾
            String sql="select * from student;";
            rs=stmt.executeQuery(sql);
            //处理结果集
            while (rs.next()){
                System.out.println(rs.getString("name"));
                System.out.println(rs.getInt("age"));
            }
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }finally {
            //释放资源

            if(rs!=null){//先释放小的
                try{
                    rs.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(stmt!=null){
                try{
                    stmt.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(con!=null){
                try{
                    con.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }

    }
}
