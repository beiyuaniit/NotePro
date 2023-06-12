package com.beiyuan.vod.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beiyuan.enums.ResultCodeEnum;
import com.beiyuan.exception.MyException;
import com.beiyuan.model.vod.Teacher;
import com.beiyuan.result.Result;
import com.beiyuan.vod.queryCondition.TeacherQueryPage;
import com.beiyuan.vod.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: beiyuan
 * @date: 2022/11/1  23:33
 */
@RestController
@RequestMapping("/admin/vod/teacher")
@Api(tags="讲师管理接口")
@CrossOrigin
public class TeacherController {

    @Autowired
    TeacherService teacherService;

//    @GetMapping("list")
//    @ApiOperation("查询所有")
//    public List<Teacher> getAllTeachers(){
//        return teacherService.list();
//    }

    //返回值是boolean时的统一处理
    private Result boolResult(boolean isSuccess){
        if(isSuccess){
            return Result.ok(null);
        }
        return Result.fail(null);
    }

    //1
//    @GetMapping("list")
//    @ApiOperation("查询所有")
//    public Result getAllTeachers(){
//        return Result.ok(teacherService.list());
//    }

    @GetMapping("findAll")
    @ApiOperation("查询所有")
    public Result findAllTeachers(){
        return Result.ok(teacherService.list());
    }
//    @DeleteMapping("remove/{id}")
//    @ApiOperation("通过id删除")
//    public boolean removeById(@ApiParam(name = "id", value = "ID", required = true)@PathVariable String id){
//            return teacherService.removeById(id);
//    }



    //2
    @PostMapping("findQueryPage/{cur}/{limit}") //当前页及每页数
    @ApiOperation("条件分页查询")
    public Result findQueryPage(@PathVariable Integer cur,
                                @PathVariable Integer limit,
                                @RequestBody(required = false) TeacherQueryPage teacherQueryPage){
        //分页查询
        Page<Teacher> pageParam=new Page<>(cur,limit);
            if(teacherQueryPage==null){
                IPage<Teacher>pageModel=teacherService.page(pageParam,null);
                return Result.ok(pageModel);
            }else {
                String name=teacherQueryPage.getName();
                Integer level=teacherQueryPage.getLevel();
                String joinDateBegin=teacherQueryPage.getJoinDateBegin();
                String joinDateEnd=teacherQueryPage.getJoinDateEnd();
                //对条件进行封装
                QueryWrapper<Teacher>wrapper=new QueryWrapper<>();
                if(!StringUtils.isEmpty(name)){
                    wrapper.like("name",name);
                }
                if(!StringUtils.isEmpty(level)){
                    wrapper.eq("level",level);
                }
                if(!StringUtils.isEmpty(joinDateBegin)){
                    wrapper.ge("join_date",joinDateBegin);
                }
                if(!StringUtils.isEmpty(joinDateEnd)){
                    wrapper.le("join_date",joinDateEnd);
                }
                IPage<Teacher> pageModel=teacherService.page(pageParam,wrapper);
                return Result.ok(pageModel);
            }
    }

    //3
    @ApiOperation("添加讲师")
    @PostMapping("saveTeacher")
    public Result saveTeacher(@RequestBody Teacher teacher){
        return boolResult(teacherService.save(teacher));
    }

    //4
    @DeleteMapping("remove/{id}")
    @ApiOperation("通过id删除")
    public Result removeById(@ApiParam(name = "id", value = "ID", required = true)@PathVariable String id){
        return boolResult(teacherService.removeById(id));
    }

    //5
    @ApiOperation("通过id查询")
    @GetMapping("getTeacher/{id}")
    public Result getTeacherById(@PathVariable Integer id){
        //int a=10/0;‘
        //抛出自定义异常
//        try {
//            int a=10/0;
//        }catch (Exception e){
//            throw  new MyException(ResultCodeEnum.FAIL,"发生了自定义异常");
//        }

        return Result.ok(teacherService.getById(id));
    }

    //6
    @ApiOperation("修改")
    @PostMapping("updateTeacher")
    public Result updateTeacherById(@RequestBody Teacher teacher){
        return boolResult(teacherService.updateById(teacher));
    }

    //7
    @ApiOperation("批量删除")
    @DeleteMapping("removeBatch")
    public Result removeBatch(@RequestBody List<Integer>list){
        return boolResult(teacherService.removeByIds(list));
    }
}
