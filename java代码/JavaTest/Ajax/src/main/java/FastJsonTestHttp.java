import com.alibaba.fastjson.JSON;

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
 * @className: FastJsonTestHttp
 * @date: 2022/5/29  22:37
 */
@WebServlet("/jsontest")
public class FastJsonTestHttp extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out=resp.getWriter();

        String name="libai";
        int age=321;
        Student stu=new Student(name,age);
        Student stu1=new Student(name,age);
        List<Student> list=new ArrayList<>();
        list.add(stu);
        list.add(stu1);
        String res= JSON.toJSONString(list);
        out.write(res);
    }
}
