import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

/**
 * @author: beiyuan
 * @className: bindbean
 * @date: 2022/4/6  13:28
 */
public class bindbean  implements HttpSessionBindingListener {
    private String name;

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        //该类的对象被绑定到session域时调用。session.setAttribute("bind",new bindbean());
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        //解绑时调用
    }

    public bindbean(){

    }
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
