package org.beanmvc.controller;

import org.beanmvc.pojo.User;
import org.beanmvc.service.UserService;
import org.beanmvc.service.UserServiceImpl;

/**
 * @author: beiyuan
 * @className: UserController
 * @date: 2022/6/28  18:32
 */
//界面层
public class UserController {
    private UserService userService;//不用new了

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public int insert(User user){
        System.out.println("界面层");
        return userService.insert(user);
    }
}
