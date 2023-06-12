package Servlets;

import Utils.JDBC;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author: beiyuan
 * @className: ListServlet
 * @date: 2022/3/27  16:10
 */
public class ListServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String contextPath=req.getContextPath();//获取应用根路径。也就是项目名

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out=resp.getWriter();

        out.print("        <!DOCTYPE html>");
        out.print("<html lang='en'>");
        out.print("<head>");
        out.print("    <meta charset='UTF-8'>");
        out.print("    <title>部门列表</title>");


        out.print("<script type='text/javascript'>");
        out.print("       function del(deptno){");
        out.print("         var ok=window.confirm('删除了不可恢复');");
        out.print("             if(ok){");
        out.print("                 window.location='/CRUD/dept/delete?deptno='+deptno;");
        out.print("             }");
        out.print("         }");
	    out.print("</script>");

        out.print("</head>");
        out.print("<body>");
        out.print("<h1>部门</h1><br >");
        out.print("<table border='1px' align='center' width='60%'>");
        out.print("	<tr>");
        out.print("		<th>编号</th>");
        out.print("		<th>名称</th>");
        out.print("		<th>地区</th>");
        out.print("		<th>操作</th>");
        out.print("	</tr>");

        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;

        try {
            con= JDBC.getConnection();
            String sql="select deptno,dname,loc from dept";
            ps=con.prepareStatement(sql);
            rs= ps.executeQuery();
            while (rs.next()){
                String deptno=rs.getString("deptno");
                String dname=rs.getString("dname");
                String loc=rs.getString("loc");
                out.print("	<tr>");
                out.print("		<td>"+deptno+"</td>");
                out.print("		<td>"+dname+"</td>");
                out.print("		<td>"+loc+"</td>");
                out.print("		<td>");
                out.print("			<a href='javascript:void(0)' onclick='del("+deptno+")'>删除</a>");
                out.print("			<a href='/CRUD/dept/edit?deptno="+deptno+"'>修改</a>");
                out.print("			<a href='"+contextPath+"/dept/detail?deptno="+deptno+"'>详情</a>");
                out.print("		</td>");
                out.print("	</tr>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {

            JDBC.close(con,ps,rs);
        }

        out.print("</table>");
        out.print("<a href='"+contextPath+"/HTML/add.html'>新增部门</a>");
        out.print("</body>");
        out.print("</html>");
    }
}
