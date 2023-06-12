package com.beiyuan.vod.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beiyuan.model.vod.Course;
import com.beiyuan.result.Result;
import com.beiyuan.vo.vod.CourseFormVo;
import com.beiyuan.vo.vod.CoursePublishVo;
import com.beiyuan.vo.vod.CourseQueryVo;
import com.beiyuan.vod.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author beiyuan
 * @since 2022-11-30
 */
@Api("课程管理接口")
@RestController
@RequestMapping("/admin/vod/course")
@CrossOrigin
public class CourseController {

    @Autowired
    CourseService courseService;

    @ApiOperation("分页列表")
    @GetMapping("{page}/{limit}")
    public Result getList(@PathVariable  Long page, @PathVariable  Long limit,
                          CourseQueryVo courseQueryVo){
        Page<Course>pageParam=new Page<>(page,limit);
        Map<String,Object> map=courseService.findPageCourse(pageParam,courseQueryVo);
        return Result.ok(map);
    }

    @ApiOperation("添加课程基本信息")
    @PostMapping("save")
    public Result save(@RequestBody CourseFormVo courseFormVo){
        Long courseId=courseService.saveCourseInfo(courseFormVo);
        return Result.ok(courseId);
    }

    @ApiOperation(value = "获取")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        CourseFormVo course = courseService.getCourseFormVoById(id);
        return Result.ok(course);
    }

    @ApiOperation(value = "修改")
    @PostMapping("update")
    public Result updateById(@RequestBody CourseFormVo courseFormVo) {
        courseService.updateCourseById(courseFormVo);
        return Result.ok(null);
    }

    @ApiOperation("根据id获取课程发布信息")
    @GetMapping("getCoursePublishVo/{id}")
    public Result getCoursePublishVoById(
            //@ApiParam(value = "课程ID", required = true)
            @PathVariable Long id){

        CoursePublishVo coursePublishVo = courseService.getCoursePublishVo(id);
        return Result.ok(coursePublishVo);
    }

    @ApiOperation("根据id发布课程")
    @PutMapping("publishCourseById/{id}")
    public Result publishCourseById(
            @ApiParam(value = "课程ID", required = true)
            @PathVariable Long id){

        boolean result = courseService.publishCourseById(id);
        return Result.ok(null);
    }
}

