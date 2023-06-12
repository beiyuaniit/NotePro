import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author: beiyuan
 * @className: ServletConfig02
 * @date: 2022/3/23  14:37
 */
public class ServletConfig02 extends GenericServlet {

    @Override
    public void service(ServletRequest request, ServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out=response.getWriter();

        ServletConfig conf=this.getServletConfig();
        out.println(conf+"<br>");

        String driver=conf.getInitParameter("driver");

        String driver1=this.getInitParameter("driver");
        out.println(driver+"<br>");
        out.println(driver1+"<br>");
    }
}
