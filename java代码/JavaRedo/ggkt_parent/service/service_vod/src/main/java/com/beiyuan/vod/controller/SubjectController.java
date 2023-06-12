package com.beiyuan.vod.controller;


import com.alibaba.excel.EasyExcel;
import com.beiyuan.exception.MyException;
import com.beiyuan.model.vod.Subject;
import com.beiyuan.vo.vod.SubjectEeVo;
import com.beiyuan.result.Result;
import com.beiyuan.vod.mapper.SubjectMapper;
import com.beiyuan.vod.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author beiyuan
 * @since 2022-11-10
 */
@RestController
@RequestMapping("/admin/vod/subject")
@Api(tags = "课程接口")
@CrossOrigin
public class SubjectController {

    @Autowired
    SubjectService subjectService;

    @Autowired
    SubjectMapper baseMapper;

    @ApiOperation("获取课程")
    @GetMapping("getChildSubject/{id}")
    public Result getChildSubject(@PathVariable Integer id){
       return Result.ok(subjectService.selectList(id));
    }

    @ApiOperation("课程导出为Excel")
    @GetMapping("exportData")
    public void exportData(HttpServletResponse response){
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("课程分类", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename="+ fileName + ".xlsx");
            List<Subject> dictList = baseMapper.selectList(null);
            List<SubjectEeVo> dictVoList = new ArrayList<>(dictList.size());    //封装了要导出的列
            for(Subject dict : dictList) {
                SubjectEeVo dictVo = new SubjectEeVo();
                //复制bean，属性对应
                BeanUtils.copyProperties(dict,dictVo);
                dictVoList.add(dictVo);
            }
            EasyExcel.write(response.getOutputStream(), SubjectEeVo.class).sheet("课程分类").doWrite(dictVoList);
        } catch (IOException e) {
            throw new MyException(20001,"导出失败");
        }
    }

    @ApiOperation("课程从Excel导入")
    @PostMapping("importData")
    public Result importData(MultipartFile file){
        subjectService.importData(file);
        return Result.ok(null);
    }
}

