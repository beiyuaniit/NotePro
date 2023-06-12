package Servlets;

import Utils.JDBC;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author: beiyuan
 * @className: DoEditServlet
 * @date: 2022/3/28  14:46
 */
public class DoEditServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String deptno=req.getParameter("deptno");
        String dname=req.getParameter("dname");
        String loc=req.getParameter("loc");

        Connection con=null;
        PreparedStatement ps=null;
        int count=0;

        try {
            con= JDBC.getConnection();
            String sql="Update dept set dname=?,loc=? where deptno=?";
            ps=con.prepareStatement(sql);
            ps.setString(1,dname);
            ps.setString(2,loc);
            ps.setString(3,deptno);
            count=ps.executeUpdate();
            if(count==1){
                req.getRequestDispatcher("/dept/list").forward(req,resp);
            }else {
                req.getRequestDispatcher("/dept/HTML/error.html").forward(req,resp);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBC.close(con,ps,null);
        }
    }
}
