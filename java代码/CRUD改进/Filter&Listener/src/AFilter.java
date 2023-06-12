import javax.servlet.*;
import javax.servlet.http.HttpFilter;
import java.io.IOException;

/**
 * @author: beiyuan
 * @className: AFilter
 * @date: 2022/4/6  9:23
 */
public class AFilter extends HttpFilter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        System.out.println("执行前过滤");

        /*
        执行下一个过滤器，若是最后一个过滤器则执行Servlet
         */
        chain.doFilter(request,response);

        System.out.println("执行后过滤");
    }

    @Override
    public void destroy() {

    }
}
