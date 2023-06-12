package com.beiyuan.vod.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.beiyuan.model.vod.Course;
import com.beiyuan.vo.vod.CoursePublishVo;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author beiyuan
 * @since 2022-11-30
 */
@Repository
public interface CourseMapper extends BaseMapper<Course> {

    CoursePublishVo selectCoursePublishVoById(Long id);
}
