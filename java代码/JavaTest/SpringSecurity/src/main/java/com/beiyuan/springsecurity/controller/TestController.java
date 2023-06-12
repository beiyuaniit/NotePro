package com.beiyuan.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: beiyuan
 * @date: 2022/12/12  15:00
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("success")
    public String getSuccess(){
        return "hello";
    }
}
