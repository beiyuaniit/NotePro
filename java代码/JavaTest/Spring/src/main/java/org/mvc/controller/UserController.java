package org.mvc.controller;

import org.mvc.pojo.User;
import org.mvc.service.UserService;
import org.mvc.service.UserServiceImpl;

/**
 * @author: beiyuan
 * @className: UserController
 * @date: 2022/6/28  18:32
 */
//界面层
public class UserController {
    private UserService userService=new UserServiceImpl();
    public int insert(User user){
        System.out.println("界面层");
        return userService.insert(user);
    }
}
