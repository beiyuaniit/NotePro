import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author: beiyuan
 * @className: XMLHttp
 * @date: 2022/5/29  23:46
 */
@WebServlet("/xml")//一定要以/开头
public class XMLHttp extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out=resp.getWriter();
        out.write("<students>" +
                "<student>" +
                "<name>libai</name>" +
                "<age>321</age>" +
                "</student>" +
                "<student>" +
                "<name>dufu</name>" +
                "<age>123</age>" +
                "</student>" +
                "</students>");
    }
}
