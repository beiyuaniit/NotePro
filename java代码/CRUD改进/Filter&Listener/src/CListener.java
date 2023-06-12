import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author: beiyuan
 * @className: CListener
 * @date: 2022/4/6  12:06
 */
@WebListener
public class CListener implements ServletContextListener {

    //都有默认实现。 default void contextInitialized(ServletContextEvent sce) {}
    //    可以选择性实现
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //ServletContext被创建时调用。服务器启动时
        System.out.println("启动");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //ServletContext被销毁时调用。服务器关闭时
        System.out.println("关闭");
    }
}
