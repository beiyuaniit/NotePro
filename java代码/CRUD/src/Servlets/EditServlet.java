package Servlets;

import Utils.JDBC;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author: beiyuan
 * @className: EditServlet
 * @date: 2022/3/28  14:36
 */
public class EditServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        String deptno=req.getParameter("deptno");
        PrintWriter out=resp.getWriter();
        String contextPath=req.getContextPath();


        out.print("        <!DOCTYPE html>");
        out.print("<html>");
        out.print("<head>");
        out.print("<meta charset='utf-8'>");
        out.print("<title>修改部门</title>");
        out.print("</head>");
        out.print("<body>");
        out.print("<body>");
        out.print("<form action='"+contextPath+"/dept/doEdit' method='post'>");




        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            con= JDBC.getConnection();
            String sql="select dname,loc from dept where deptno=?";
            ps=con.prepareStatement(sql);
            ps.setString(1,deptno);
            rs=ps.executeQuery();

            if(rs.next()){
                String dname=rs.getString("dname");
                String loc =rs.getString("loc");
                out.print("                编号<input type='text' name='deptno' value='"+deptno+"' readonly/><br>");
                out.print("                名称<input type='text' name='dname'  value='"+dname+"' /><br>");
                out.print("                位置<input type='text' name='loc'  value='"+loc+"' /><br>");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBC.close(con,ps,rs);
        }

        out.print("        <input type='submit' value='修改'/><br>");
        out.print("</form>");
        out.print("</body>");
        out.print("</html>");
    }
}


