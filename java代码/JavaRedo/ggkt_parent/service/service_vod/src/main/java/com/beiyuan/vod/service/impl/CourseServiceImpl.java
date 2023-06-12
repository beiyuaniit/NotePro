package com.beiyuan.vod.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beiyuan.model.vod.Course;
import com.beiyuan.model.vod.CourseDescription;
import com.beiyuan.model.vod.Subject;
import com.beiyuan.model.vod.Teacher;
import com.beiyuan.vo.vod.CourseFormVo;
import com.beiyuan.vo.vod.CoursePublishVo;
import com.beiyuan.vo.vod.CourseQueryVo;
import com.beiyuan.vod.mapper.CourseMapper;
import com.beiyuan.vod.service.CourseDescriptionService;
import com.beiyuan.vod.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beiyuan.vod.service.SubjectService;
import com.beiyuan.vod.service.TeacherService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author beiyuan
 * @since 2022-11-30
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    TeacherService teacherService;

    @Autowired
    SubjectService subjectService;

    @Autowired
    CourseDescriptionService courseDescriptionService;



    @Autowired
    CourseMapper courseMapper;

    @Override
    public Map<String, Object> findPageCourse(Page<Course> pageParam, CourseQueryVo courseQueryVo) {
        String title=courseQueryVo.getTitle();
        Long subjectId = courseQueryVo.getSubjectId();
        Long subjectParentId = courseQueryVo.getSubjectParentId();
        Long teacherId = courseQueryVo.getTeacherId();

        QueryWrapper wrapper=new QueryWrapper<>();
        if(!StringUtils.isEmpty(title)){
            wrapper.eq("title",title);
        }

        if(!StringUtils.isEmpty(subjectId)) {
            wrapper.eq("subject_id",subjectId);
        }
        if(!StringUtils.isEmpty(subjectParentId)) {
            wrapper.eq("subject_parent_id",subjectParentId);
        }
        if(!StringUtils.isEmpty(teacherId)) {
            wrapper.eq("teacher_id",teacherId);
        }

        IPage<Course>pageModel=baseMapper.selectPage(pageParam,wrapper);
        Long totoalCount=pageModel.getTotal();
        Long totoalPage= pageModel.getPages();
        List<Course> list=pageModel.getRecords();

        //不是显示id
        list.stream().forEach(item->{
            this.getNameById(item);
        });

        Map<String,Object> map = new HashMap<>();
        map.put("totalCount",totoalCount);
        map.put("totalPage",totoalPage);
        map.put("records",list);
        return map;
    }



    private Course getNameById(Course course) {


        Teacher teacher = teacherService.getById(course.getTeacherId());
        if(teacher!=null){
            String name=teacher.getName();
            course.getParam().put("teacherName",name);
        }

        //查询分类名称
        Subject subjectOne = subjectService.getById(course.getSubjectParentId());
        if(subjectOne != null) {
            course.getParam().put("subjectParentTitle",subjectOne.getTitle());
        }
        Subject subjectTwo = subjectService.getById(course.getSubjectId());
        if(subjectTwo != null) {
            course.getParam().put("subjectTitle",subjectTwo.getTitle());
        }

        return course;
    }

    @Override
    public Long saveCourseInfo(CourseFormVo courseFormVo) {
        Course course=new Course();
        BeanUtils.copyProperties(courseFormVo,course);
        baseMapper.insert(course);
        //保存课程详情信息
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseFormVo.getDescription());
        courseDescription.setCourseId(course.getId());
        courseDescriptionService.save(courseDescription);

        //返回课程id
        return course.getId();

    }

    //根据id获取课程信息
    @Override
    public CourseFormVo getCourseFormVoById(Long id) {
        //从course表中取数据
        Course course = baseMapper.selectById(id);
        if(course == null){
            return null;
        }
        //从course_description表中取数据
        CourseDescription courseDescription = courseDescriptionService.getById(id);
        //创建courseInfoForm对象
        CourseFormVo courseFormVo = new CourseFormVo();
        BeanUtils.copyProperties(course, courseFormVo);
        if(courseDescription != null){
            courseFormVo.setDescription(courseDescription.getDescription());
        }
        return courseFormVo;
    }

    //根据id修改课程信息
    @Override
    public void updateCourseById(CourseFormVo courseFormVo) {
        //修改课程基本信息
        Course course = new Course();
        BeanUtils.copyProperties(courseFormVo, course);
        baseMapper.updateById(course);
        //修改课程详情信息
        CourseDescription courseDescription = courseDescriptionService.getById(course.getId());
        courseDescription.setDescription(courseFormVo.getDescription());
        courseDescription.setId(course.getId());
        courseDescriptionService.updateById(courseDescription);
    }

    //根据id获取课程发布信息
    @Override
    public CoursePublishVo getCoursePublishVo(Long id) {
        return courseMapper.selectCoursePublishVoById(id);
    }

    //根据id发布课程
    @Override
    public boolean publishCourseById(Long id) {
        Course course = new Course();
        course.setId(id);
        course.setPublishTime(new Date());
        course.setStatus(1);
        return this.updateById(course);
    }
}
