package org.mvc;

import org.junit.Test;
import org.mvc.controller.UserController;
import org.mvc.pojo.User;

/**
 * @author: beiyuan
 * @className: test
 * @date: 2022/6/28  20:55
 */
public class normvc {
    @Test
    public void testNormvc(){
        org.mvc.controller.UserController userController=new UserController();
        org.mvc.pojo.User user=new User();
        user.setAge(21);
        user.setName("lisi");

        userController.insert(user);
    }
}
