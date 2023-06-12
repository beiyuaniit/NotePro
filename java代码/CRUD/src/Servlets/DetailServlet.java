package Servlets;

import Utils.JDBC;

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
 * @className: DetailServlet
 * @date: 2022/3/27  19:10
 */
public class DetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");

        PrintWriter out=resp.getWriter();

        String deptno=req.getParameter("deptno");

        out.print("<!DOCTYPE html>");
        out.print("<html>");
        out.print("<head>");
        out.print("<meta charset='utf-8'>");
        out.print("<title></title>");
        out.print("</head>");
        out.print("<body>");

        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;

        try {
            con= JDBC.getConnection();
            String sql="select dname,loc from dept where deptno=?";
            ps=con.prepareStatement(sql);
            ps.setString(1,deptno);
            rs=ps.executeQuery();

            while (rs.next()){
                String dname=rs.getString("dname");
                String loc=rs.getString("loc");
                out.print("<h1>部门详情</h1>");
                out.print("<hr >");
                out.print("		编号："+deptno+"   <br>");
                out.print("		名称："+dname+"  <br>");
                out.print("		位置："+loc+"   <br>");
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBC.close(con,ps,rs);
        }

        out.print("<input type='button' value='后退' onclick='window.history.back()' />");
        out.print("</body>");
        out.print("</html>");
    }
}





