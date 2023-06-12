package Filters;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author: beiyuan
 * @className: LoginFilter
 * @date: 2022/4/6  11:17
 */

public class LoginFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session=request.getSession(false);

        /*
        哪些页面不用拦截
            用户已经登录   session有值
            用户通过index.jsp  cookie免密登录
            下方else去登录界面时"/loginjsp"  因为要再次经过过滤器
            用户在登录界面访问"/user/login"
         */
        String path=request.getServletPath();
        if(path.equals("/index.jsp")||path.equals("/user/login")||
                path.equals("/login.jsp")||
                (session!=null && session.getAttribute("user")!=null)){
            chain.doFilter(request,response);
        }else {
            response.sendRedirect(request.getContextPath()+"/login.jsp");
        }
    }
}
