package Servlets;

import Beans.User;
import Utils.JDBC;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author: beiyuan
 * @className: LoginServlet
 * @date: 2022/3/31  21:32
 */
@WebServlet(urlPatterns = {"/user/login","/user/logout","/index.jsp"})
//只给"/index.jsp"设置了免密登录
public class LoginServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path=req.getServletPath();
        switch (path){
            case "/user/login":
                doLogin(req,resp);
                break;
            case"/user/logout":
                doLogout(req,resp);
                break;
            case"/index.jsp":
                freeLogin(req,resp);
                break;
        }
    }


    private void freeLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //默认路径中实现判断是否十天免密登录
        Cookie[]cookies= req.getCookies();
        String username=null;
        String password=null;

        //有则拿到用户名和密码
        if (cookies != null) {
            for(Cookie cookie :cookies){
                String name=cookie.getName();
                if(name.equals("username")){
                    username=cookie.getValue();
                }else if(name.equals("password")){
                    password=cookie.getValue();
                }
            }
        }

        //判断是否为用户名和密码正确
        if(username!=null&&password!=null){
            Connection con=null;
            PreparedStatement ps=null;
            ResultSet rs=null;
            boolean isSuccess=false;
            try {
                con=JDBC.getConnection();
                String sql="select * from t_user where username=? and password=?";
                ps=con.prepareStatement(sql);
                ps.setString(1,username);
                ps.setString(2,password);
                rs=ps.executeQuery();

                if(rs.next()){
                    isSuccess=true;
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }finally {
                JDBC.close(con,ps,rs);
            }

            if(isSuccess){
                //记得加上session
                HttpSession session=req.getSession();
                session.setAttribute("username",username);

                resp.sendRedirect(req.getContextPath()+"/dept/list");

            }else {
                resp.sendRedirect(req.getContextPath()+"/login.jsp");

            }
        }
        else {
            resp.sendRedirect(req.getContextPath()+"/login.jsp");
        }
    }


    private void doLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean isSuccess=false;
        String username=req.getParameter("username");
        String password=req.getParameter("password");

        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;

        try {
            con= JDBC.getConnection();
            String sql="select * from t_user where username=? and password= ?";
            ps=con.prepareStatement(sql);
            ps.setString(1,username);
            ps.setString(2,password);
            rs=ps.executeQuery();
            if(rs.next()){
                isSuccess=true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBC.close(con,ps,rs);
        }

        if(isSuccess){
            HttpSession session=req.getSession();
            //session.setAttribute("username",username);

            User user=new User();
            user.setUsername(username);
            user.setPassword(password);
            session.setAttribute("user",user);

            //10天免密登录
            String isOk=req.getParameter("isOk");
            if(isOk!=null&&isOk.equals("ok")){
                //创建Cookie
                Cookie cookie1=new Cookie("username",username);
                Cookie cookie2=new Cookie("password",password);//实际开发中是加密

                //设置有效期
                cookie1.setMaxAge(60*60*24*10);
                cookie2.setMaxAge(60*60*24*10);

                //设置关联路径。只要访问项目下路径都携带
                cookie1.setPath(req.getContextPath());
                cookie2.setPath(req.getContextPath());

                //响应给浏览器
                resp.addCookie(cookie1);
                resp.addCookie(cookie2);
            }

            resp.sendRedirect(req.getContextPath()+"/dept/list");
        }else {
            resp.sendRedirect(req.getContextPath()+"/JSP/error.jsp");
        }
    }

    private void doLogout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session=req.getSession(false);
        if(session!=null){
//            session.removeAttribute("user");不用手动移除也行
            session.invalidate();
        }
        //也要把cookie删除
        Cookie [] cookies = req.getCookies();
        if (cookies != null) {
            for(Cookie cookie:cookies){
                /*
                不设置的话会存在路径问题
                    可能原cookie是/CRUD,现在的cookie是/CRUD/logout。即使名字啥的相同也不能覆盖
                 所以要重新设置路径，来覆盖该路径下所有cookie
                 */
                cookie.setMaxAge(0);
                cookie.setPath(req.getContextPath());
                resp.addCookie(cookie);

            }
        }
        //session和cookie都销毁后才跳转
        resp.sendRedirect(req.getContextPath()+"/login.jsp");
    }


}
