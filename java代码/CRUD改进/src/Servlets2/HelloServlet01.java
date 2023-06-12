package Servlets2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * @author: beiyuan
 * @className: HelloServlet
 * @date: 2022/3/28  23:58
 */

@WebServlet(name = "HelloName",urlPatterns = {"/hello"},loadOnStartup = 1,
initParams = {@WebInitParam(name="username",value="root"),@WebInitParam(name = "password",value = "312")})
public class HelloServlet01 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //获取注解中name
        String serName=getServletName();

        //获取当前url，也就是urlPatterns中一个
        String url=req.getServletPath();

        //通过枚举获取所有初始化参数
        Enumeration<String> names=getInitParameterNames();
        while (names.hasMoreElements()){
            String name=names.nextElement();
            String value=getInitParameter(name);
        }
        String name1=getInitParameter("username");//也可以直接获取

        resp.setContentType("text/html; charset=UTF-8");

        PrintWriter out=resp.getWriter();
        out.write(serName+"<br>");
        out.write(url+"<br>");
        out.write(name1+"<br>");
    }
}
