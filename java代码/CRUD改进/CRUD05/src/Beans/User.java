package Beans;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

/**
 * @author: beiyuan
 * @className: User
 * @date: 2022/4/6  13:57
 */
public class User  implements HttpSessionBindingListener {
    private String username;
    private String password;

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        ServletContext application=event.getSession().getServletContext();

        Object count=application.getAttribute("count");
        if(count==null){//第一个人上线，count为null
            application.setAttribute("count",1);
        }else {
            int i=(Integer)count;
            i++;
            application.setAttribute("count",i);
        }
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        ServletContext application=event.getSession().getServletContext();

        Integer count=(Integer)application.getAttribute("count");
        count--;
        application.setAttribute("count",count);
    }

    public User(){

    }
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
