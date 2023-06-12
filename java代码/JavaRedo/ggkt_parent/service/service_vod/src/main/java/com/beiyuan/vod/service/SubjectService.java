package com.beiyuan.vod.service;

import com.beiyuan.model.vod.Subject;

import com.baomidou.mybatisplus.extension.service.IService;
import com.beiyuan.result.Result;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author beiyuan
 * @since 2022-11-10
 */
public interface SubjectService extends IService<Subject> {

    List<Subject> selectList(Integer id);

    void importData(MultipartFile file);
}
