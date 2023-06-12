package com.beiyuan.music.controller;


import com.beiyuan.music.common.result.Result;
import com.beiyuan.music.entity.Admin;
import com.beiyuan.music.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 管理员 前端控制器
 * </p>
 *
 * @author beiyuan
 * @since 2022-12-29
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @PostMapping("login")
    public Result login(@RequestBody Admin admin){

        if(admin!=null && adminService.verify(admin.getUsername(),admin.getPassword())){
            Map <String ,Object> map=new HashMap<>();
            map.put("token","admin");
            return Result.ok(map);
        }
        return Result.fail(null).message("用户名或密码错误");
    }
}

