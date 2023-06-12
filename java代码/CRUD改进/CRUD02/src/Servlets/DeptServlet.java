package Servlets;

import Utils.JDBC;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
@WebServlet({"/dept/list","/dept/edit","/dept/detail","/dept/del","/dept/add","/dept/doEdit"})
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
            case "/dept/edit":
                Edit(req,resp);
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



    private void doDetail(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException{
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

    private void Edit(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException{
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
            resp.sendRedirect(req.getContextPath()+"/HTML/error.html");
            //req.getRequestDispatcher("/HTML/error.html").forward(req,resp);
        }
    }

    private void doList(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException{
        String contextPath=req.getContextPath();//获取应用根路径。也就是项目名

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out=resp.getWriter();

        out.print("        <!DOCTYPE html>");
        out.print("<html lang='en'>");
        out.print("<head>");
        out.print("    <meta charset='UTF-8'>");
        out.print("    <title>部门列表</title>");


        out.print("<script type='text/javascript'>");
        out.print("       function del(deptno){");
        out.print("         var ok=window.confirm('删除了不可恢复');");
        out.print("             if(ok){");
        out.print("                 window.location='"+contextPath+"/dept/del?deptno='+deptno;");
        out.print("             }");
        out.print("         }");
        out.print("</script>");

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
            con= JDBC.getConnection();
            String sql="select deptno,dname,loc from dept";
            ps=con.prepareStatement(sql);
            rs= ps.executeQuery();
            while (rs.next()){
                String deptno=rs.getString("deptno");
                String dname=rs.getString("dname");
                String loc=rs.getString("loc");
                out.print("	<tr>");
                out.print("		<td>"+deptno+"</td>");
                out.print("		<td>"+dname+"</td>");
                out.print("		<td>"+loc+"</td>");
                out.print("		<td>");
                out.print("			<a href='javascript:void(0)' onclick='del("+deptno+")'>删除</a>");
                out.print("			<a href='"+contextPath+"/dept/edit?deptno="+deptno+"'>修改</a>");
                out.print("			<a href='"+contextPath+"/dept/detail?deptno="+deptno+"'>详情</a>");
                out.print("		</td>");
                out.print("	</tr>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {

            JDBC.close(con,ps,rs);
        }

        out.print("</table>");
        out.print("<a href='"+contextPath+"/HTML/add.html'>新增部门</a>");
        out.print("</body>");
        out.print("</html>");
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
            resp.sendRedirect(req.getContextPath()+"/dept/list");
            //转发回到部门列表
            //req.getRequestDispatcher("/dept/list").forward(req,resp);
        }else {
            resp.sendRedirect(req.getContextPath()+"HTML/error.html");
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
            resp.sendRedirect(req.getContextPath()+"/HTML/error.html");
            //req.getRequestDispatcher("/HTML/error.html").forward(req,resp);
        }
    }
}
