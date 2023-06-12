package com.beiyuan.vod.controller;

import com.beiyuan.enums.ResultCodeEnum;
import com.beiyuan.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: beiyuan
 * @date: 2022/11/7  14:10
 */

@RestController
@RequestMapping("/admin/vod/user")
@Api(tags = "用户登陆接口")
@CrossOrigin //解除跨域限制
public class UserLoginController {

    @PostMapping("login")
    @ApiOperation("登陆")
    public Result login(){
        //{"code":20000,"data":{"token":"admin-token"}}
        Map<String,Object>map=new HashMap<>();
        map.put("token","admin-token");
        return Result.ok(map);//改了状态码为20000
    }

    @GetMapping("info")
    @ApiOperation("登陆成功返回信息")
    public Result info(){
        /*
        {"code":20000,"data":{"roles":["admin"],"introduction":"I am a super administrator",
        "avatar":"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif",
        "name":"Super Admin"}}
         */
        Map <String,Object>map=new HashMap<>();
        map.put("roles","admin");
        map.put("introduction","I am a super administrator");
        map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        map.put("name","Super Admin");
        return Result.ok(map);
    }

    @PostMapping("logout")
    @ApiOperation("退出登陆")
    public Result logout(){
        return Result.ok(null);
    }
}
