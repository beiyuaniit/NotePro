package Servlets;

import Beans.Dept;
import Utils.JDBC;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: beiyuan
 * @className: DeptServlet
 * @date: 2022/3/29  15:16
 */

/**
 * 二版
 * 使用注解配置
 * 由于每个请求写一个类，导致类的数量太多，改为一个方法对应一个请求。（从类爆炸到方法爆炸
 *
 * 项目名不写死  window.location='"+contextPath+"/dept/delete?deptno='+deptno;
 *
 * 问题
 * 静态的页面还是写死了
 * 现在就不能使用doGet和doPost
 */
@WebServlet({"/dept/list","/dept/detail","/dept/del","/dept/add","/dept/doEdit"})
public class DeptServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path=req.getServletPath();
            //switch通过equal判断字符串
            switch (path){
                case "/dept/list":
                    doList(req,resp);
                    break;
                case "/dept/doEdit":
                    doEdit(req,resp);
                    break;
                case "/dept/detail":
                    doDetail(req,resp);
                    break;
                case "/dept/del":
                    doDel(req,resp);
                    break;
                case "/dept/add":
                    doAdd(req,resp);
                    break;
            }


    }




    private void doList(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException{
        List<Dept> deptList=new ArrayList<>();

        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;

        try {
            con= JDBC.getConnection();
            String sql="select deptno,dname,loc from dept";
            ps=con.prepareStatement(sql);
            rs= ps.executeQuery();
            while (rs.next()){
                //从结果集中取出
                String deptno=rs.getString("deptno");
                String dname=rs.getString("dname");
                String loc=rs.getString("loc");

                //封装到JavaBean中
                Dept dept=new Dept();
                dept.setDeptno(deptno);
                dept.setDname(dname);
                dept.setLoc(loc);

                //放到袋子里，方便转发
                deptList.add(dept);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {

            JDBC.close(con,ps,rs);
        }

        //存到请求域中
        req.setAttribute("deptList",deptList);

        //转发给Servlet展示
        req.getRequestDispatcher("/JSP/list.jsp").forward(req,resp);//内部转发不用项目名


    }

    //负责详情和修改
    private void doDetail(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException{
            String deptno=req.getParameter("deptno");
            String path=req.getParameter("path");//转向详情或修改

            Dept dept=new Dept();//只有一个豆子，就不用袋子了

            Connection con=null;
            PreparedStatement ps=null;
            ResultSet rs=null;

            try {
                con= JDBC.getConnection();
                String sql="select dname,loc from dept where deptno=?";
                ps=con.prepareStatement(sql);
                ps.setString(1,deptno);
                rs=ps.executeQuery();

                //只有一个数据，不用while循环
                if (rs.next()){
                    String dname=rs.getString("dname");
                    String loc=rs.getString("loc");

                    dept.setDeptno(deptno);
                    dept.setDname(dname);
                    dept.setLoc(loc);
                }


            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }finally {
                JDBC.close(con,ps,rs);
            }

            req.setAttribute("dept",dept);

            req.getRequestDispatcher("/JSP/"+path+".jsp").forward(req,resp);
    }

    private void doDel(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException{
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
            //没有存数据，则重定向即可
            resp.sendRedirect(req.getContextPath()+"/dept/list");
            //转发回到部门列表
            //req.getRequestDispatcher("/dept/list").forward(req,resp);
        }else {
            resp.sendRedirect(req.getContextPath()+"JSP/error.jsp");
            //req.getRequestDispatcher("/HTML/error.html").forward(req,resp);
        }
    }

    private void doAdd(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException{
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
            resp.sendRedirect(req.getContextPath()+"/JSP/error.jsp");
            //req.getRequestDispatcher("/HTML/error.html").forward(req,resp);
        }
    }


    //真正做修改
    private void doEdit(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException{
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

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBC.close(con,ps,null);
        }

        if(count==1){
            resp.sendRedirect(req.getContextPath()+"/dept/list");
            //req.getRequestDispatcher("/dept/list").forward(req,resp);
        }else {
            resp.sendRedirect(req.getContextPath()+"/JSP/error.jsp");
            //req.getRequestDispatcher("/HTML/error.html").forward(req,resp);
        }
    }






}
