import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author: beiyuan
 * @className: HSessionL
 * @date: 2022/4/6  13:19
 */
public class HSessionL implements HttpSessionAttributeListener {
    @Override
    public void attributeAdded(HttpSessionBindingEvent se) {
        //往session域中存数据时调用
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent se) {
        //移除数据
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent se) {
        //通过key更新value
    }
}
