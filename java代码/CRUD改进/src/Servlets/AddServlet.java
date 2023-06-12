package Servlets;

import Utils.JDBC;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author: beiyuan
 * @className: AddServlet
 * @date: 2022/3/27  21:59
 */
public class AddServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String deptno=req.getParameter("deptno");
        String dname=req.getParameter("dname");
        String loc=req.getParameter("loc");

        Connection con=null;
        PreparedStatement ps=null;
        int count=0;

        try {
            con= JDBC.getConnection();
            con.setAutoCommit(false);
            String sql="insert into dept values(?,?,?)";
            ps=con.prepareStatement(sql);
            ps.setString(1,deptno);
            ps.setString(2,dname);
            ps.setString(3,loc);

            count=ps.executeUpdate();
            con.commit();

        } catch (SQLException throwables) {
            if(con!=null){
                try {
                    con.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            throwables.printStackTrace();
        }finally {
            JDBC.close(con,ps,null);
        }

        if(count==1){
            resp.sendRedirect(req.getContextPath()+"/dept/list");
            //req.getRequestDispatcher("/dept/list").forward(req,resp);
        }else {
            resp.sendRedirect(req.getContextPath()+"/HTML/error.html");
            //req.getRequestDispatcher("/HTML/error.html").forward(req,resp);
        }
    }
}
