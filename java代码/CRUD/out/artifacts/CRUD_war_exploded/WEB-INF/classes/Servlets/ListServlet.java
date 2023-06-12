package Servlets;

import Utils.DbUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author: beiyuan
 * @className: ListServlet
 * @date: 2022/3/27  16:10
 */
public class ListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out=resp.getWriter();

        out.print("        <!DOCTYPE html>");
        out.print("<html lang='en'>");
        out.print("<head>");
        out.print("    <meta charset='UTF-8'>");
        out.print("    <title>部门列表</title>");
        out.print("</head>");
        out.print("<body>");
        out.print("<h1>部门</h1><br >");
        out.print("<table border='1px' align='center' width='60%'>");
        out.print("	<tr>");
        out.print("		<th>编号</th>");
        out.print("		<th>名称</th>");
        out.print("		<th>地区</th>");
        out.print("		<th>操作</th>");
        out.print("	</tr>");


        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            con= DbUtil.getConnection();
            String sql="select deptno,dname,loc from dept";
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            System.out.println("124");
            while (rs.next()){
                System.out.println("321");
                String deptno=rs.getString("deptno");
                String dname=rs.getString("dname");
                String loc=rs.getString("loc");
                out.print("	<tr>");
                out.print("		<td>"+deptno+"</td>");
                out.print("		<td>"+dname+"</td>");
                out.print("		<td>"+loc+"</td>");
                out.print("		<td>");
                out.print("			<a href=''>删除</a>");
                out.print("			<a href='edit.html'>修改</a>");
                out.print("			<a href='detail.html'>详情</a>");
                out.print("		</td>");
                out.print("	</tr>");
            }

        } catch (SQLException throwables) {
            System.out.println("fafa231");
            throwables.printStackTrace();
        }finally {
            DbUtil.close(con,ps,rs);
        }

        out.print("</table>");
        out.print("<a href='add.html'>新增部门</a>");
        out.print("</body>");
        out.print("</html>");

    }
}
