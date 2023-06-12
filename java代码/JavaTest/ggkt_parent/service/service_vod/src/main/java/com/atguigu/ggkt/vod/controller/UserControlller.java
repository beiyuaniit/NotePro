package com.atguigu.ggkt.vod.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: beiyuan
 * @date: 2022/11/1  10:55
 */
@RequestMapping("/admin")
@RestController
public class UserControlller {

    @GetMapping("/user")
    public String getUser(){
        return "user01";
    }
}
