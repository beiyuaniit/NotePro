package Servlets2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author: beiyuan
 * @className: WelcomeServlet
 * @date: 2022/3/29  10:15
 */

/**
 * 当注解中属性是一个数组，并且只有一个元素时，大括号可以shenglve
 * @WebServlet(urlPatterns="/welcome")
 *
 * 属性value和urlPatterns都是指定Servlet映射路径，也就是请求路径
 * @WebServlet(value={"/welcome1","/welcome2"}
 *
 * 如果注解的属性名是value,属性名也可以省略
 * @WebServlet("/wel"}          等价于@WebServlet=(value="/wel")
 *
 * 目前来看，只能指定一个，若同时指定value和urlPatterns会保存
 * @WebServlet(value = "/wel1",urlPatterns = {"/Wel"})//报错
 */
@WebServlet("/wel")
public class WelcomeServlet02 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out=resp.getWriter();

        out.write(req.getServletPath());
    }
}
