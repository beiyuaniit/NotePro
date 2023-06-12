import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: beiyuan
 * @className: AjaxServlet
 * @date: 2022/5/6  20:43
 */
//全局刷新
//此处不用写项目名
@WebServlet("/getData")
public class AjaxServlet  extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

//        //
//        String name=req.getParameter("name");
//        String w=req.getParameter("w");
//        String h=req.getParameter("h");
//
//        float height=Float.valueOf(h);
//        float weight=Float.valueOf(w);
//
//        float bim=weight/(height*height);
//
//        req.setAttribute("bim",bim);
//        req.getRequestDispatcher("/show.jsp").forward(req,resp);
        System.out.println("doAjax");

    }
}
