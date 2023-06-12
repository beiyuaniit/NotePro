package org.comb.controller;

import org.comb.pojo.User;
import org.comb.service.UserService;

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
