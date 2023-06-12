import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author: beiyuan
 * @className: ServletContext03
 * @date: 2022/3/23  15:50
 */
public class ServletContext03 extends GenericServlet {
    @Override
    public void service(ServletRequest request, ServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out=response.getWriter();

        //配置信息
        ServletContext application=this.getServletContext();
        String pageSize=application.getInitParameter("pageSize");
        out.println(pageSize+"<br>");

        //日志
        application.log("this is Tomcat");

        //存储数据
        application.setAttribute("name","zhangsan");
        String str=(String)application.getAttribute("name");
        out.write(str+"<br>");

        application.removeAttribute("name");
    }
}
