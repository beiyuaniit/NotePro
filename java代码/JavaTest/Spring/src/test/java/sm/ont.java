package sm;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sm.pojo.User;
import sm.service.UserService;

/**
 * @author: beiyuan
 * @className: ont
 * @date: 2022/7/1  22:31
 */
public class ont {
    @Test
    public void one(){
        ApplicationContext ac=new ClassPathXmlApplicationContext("smtransaction/total.xml");
        UserService userService=(UserService) ac.getBean("userServiceImpl");
        userService.insert(new User("liuzongyuan","xiaoshitangji",8));
    }
}
