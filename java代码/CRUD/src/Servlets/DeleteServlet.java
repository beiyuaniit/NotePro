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
 * @className: DeleteServlet
 * @date: 2022/3/27  20:45
 */
public class DeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String deptno=req.getParameter("deptno");

        Connection con=null;
        PreparedStatement ps=null;
        int count=0;//成功的数目

        try {
            con= JDBC.getConnection();

            con.setAutoCommit(false);

            String sql="delete from dept where deptno=?";
            ps=con.prepareStatement(sql);
            ps.setString(1,deptno);

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
            //转发回到部门列表
            req.getRequestDispatcher("/dept/list").forward(req,resp);
        }else {
            req.getRequestDispatcher("/HTML/error.html").forward(req,resp);
        }

    }
}
