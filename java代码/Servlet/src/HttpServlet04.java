import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author: beiyuan
 * @className: HttpServlet05
 * @date: 2022/3/25  11:59
 */
public class HttpServlet04 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name=req.getParameter("username");
        resp.setContentType("text/html;charset=UTF-8");//解决Tomcat9响应中文乱码
        PrintWriter out=resp.getWriter();

        out.write(name+"<br>");

        String addr=req.getRemoteAddr();
        out.println(addr+"<br>");

    }
}
