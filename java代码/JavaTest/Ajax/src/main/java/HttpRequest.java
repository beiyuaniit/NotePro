import com.sun.net.httpserver.HttpServer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: beiyuan
 * @className: HttpRequest
 * @date: 2022/5/27  20:20
 */
@WebServlet("/httpReq")
public class HttpRequest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        PrintWriter out=resp.getWriter();
        out.println("<h1>there is a town<h1>");
        System.out.println(req.getParameter("username"));
        System.out.println(req.getParameter("password"));
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out=resp.getWriter();
        out.println("<h1>there is a town<h1>");
        System.out.println(req.getParameter("username"));
        System.out.println(req.getParameter("password"));

    }
}
