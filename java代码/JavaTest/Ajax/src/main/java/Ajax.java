import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author: beiyuan
 * @className: Ajax
 * @date: 2022/5/31  23:58
 */
@WebServlet("/ajax")
public class Ajax extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out=resp.getWriter();

        String name=req.getParameter("name");
        int age= Integer.parseInt(req.getParameter("age"));
        Student stu=new Student(name,age);
        String json= JSON.toJSONString(stu);
        out.write(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out=resp.getWriter();
        String name=req.getParameter("name");
        int age= Integer.parseInt(req.getParameter("age"));
        Student stu=new Student(name,age);
        String json= JSON.toJSONString(stu);
        out.write(json);
    }
}
