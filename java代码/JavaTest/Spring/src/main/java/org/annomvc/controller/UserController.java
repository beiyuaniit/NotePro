package org.annomvc.controller;

import org.annomvc.pojo.User;
import org.annomvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author: beiyuan
 * @className: UserController
 * @date: 2022/6/28  18:32
 */
@Controller
//界面层
public class UserController {
    @Autowired
    private UserService userService;//不用new了
    public int insert(User user){
        System.out.println("界面层");
        return userService.insert(user);
    }
}
