package com.beiyuan.music.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beiyuan.music.common.result.Result;
import com.beiyuan.music.entity.User;
import com.beiyuan.music.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 普通用户 前端控制器
 * </p>
 *
 * @author beiyuan
 * @since 2022-12-29
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("getUser/{page}/{limit}")
    public Result getUser(@PathVariable Long page, @PathVariable Long limit){
        Page<User> pageParam=new Page<>(page,limit);
        IPage<User> pageModel=userService.page(pageParam);
        return Result.ok(pageModel);
    }

    @GetMapping("getUserByName/{page}/{limit}")
    public Result getUserByName(@PathVariable Long page, @PathVariable Long limit,String name){
        System.out.println(name);
        QueryWrapper<User>wrapper=new QueryWrapper<>();
        wrapper.like("username",name);
        Page<User> pageParam=new Page<>(page,limit);
        IPage<User> pageModel=userService.page(pageParam,wrapper);
        return Result.ok(pageModel);
    }

    @DeleteMapping("deleteUser/{id}")
    public Result deleteUser(@PathVariable Long id){
        return Result.bool(userService.removeById(id));
    }

    @PostMapping("updateUser")
    public Result updateUser(@RequestBody User user){
        return Result.bool(userService.updateById(user));
    }

    @PostMapping("addUser")
    public Result addUser (@RequestBody User user){
        //System.out.println(user);
        return Result.bool(userService.save(user));
    }
}

