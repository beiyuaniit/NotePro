package com.beiyuan.music.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beiyuan.music.common.result.Result;
import com.beiyuan.music.entity.Comment;
import com.beiyuan.music.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author beiyuan
 * @since 2022-12-29
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @GetMapping("getComment/{page]/{limit}")
    public Result getCommentList(@PathVariable Long page, @PathVariable Long limit){
        Page<Comment> pageParam=new Page<>(page,limit);
        IPage<Comment>pageModel=commentService.page(pageParam);

        return Result.ok(pageModel);
    }

    @DeleteMapping("delete/{id}")
    public Result deleteComment(@PathVariable Long id){
        return Result.bool(commentService.removeById(id));
    }
//
//    @PostMapping("update")   //不能修改评论
//    public Result updateComment(@RequestBody Comment comment){
//        return Result.bool(commentService.updateById(comment));
//    }

    @PostMapping("add")
    public Result addComment(@RequestBody Comment comment){
        return Result.bool(commentService.save(comment));
    }


}

