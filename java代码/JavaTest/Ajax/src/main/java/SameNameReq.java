import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author: beiyuan
 * @className: SameNameReq
 * @date: 2022/5/28  23:58
 */
@WebServlet("/same")
public class SameNameReq extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name=req.getParameter("username");
        resp.setContentType("text/html");
        PrintWriter out=resp.getWriter();
        if(name.equals("lisi")){
            out.write("<font color='red'>same name</font>");
        }
        System.out.println(name);
    }
}
