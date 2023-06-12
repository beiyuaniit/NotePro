import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/**
 * @author: beiyuan
 * @className: StudentList
 * @date: 2022/3/21  17:35
 */
public class StudentList01 implements Servlet{


    @Override
    public void service(ServletRequest request, ServletResponse response)
            throws ServletException, IOException {
        //设置响应内容类型
        response.setContentType("text/html");

        PrintWriter out=response.getWriter();
        out.print("<h1>hello servlet!</h1>");
        //连接数据库
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/beimysql","root",
                    "beiyuan3721");
            String sql="select * from student";
            ps=conn.prepareStatement(sql);
            rs= ps.executeQuery();
            out.println("fasa");
            while (rs.next()){
                //out.println("dh");
                out.println(rs.getString("id"));
                out.println(rs.getString("name"));
                out.println(rs.getString("age"));
                out.println(rs.getString("sno"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {

            if(rs!=null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if(ps!=null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }









    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }
    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
