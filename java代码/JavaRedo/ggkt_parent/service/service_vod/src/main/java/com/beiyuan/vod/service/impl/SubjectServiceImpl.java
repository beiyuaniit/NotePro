package com.beiyuan.vod.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.beiyuan.exception.MyException;
import com.beiyuan.model.vod.Subject;

import com.beiyuan.vo.vod.SubjectEeVo;
import com.beiyuan.vod.listener.SubjectListener;
import com.beiyuan.vod.mapper.SubjectMapper;
import com.beiyuan.vod.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author beiyuan
 * @since 2022-11-10
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Autowired
    SubjectMapper subjectMapper;

    @Autowired
    SubjectListener subjectListener;

    @Override
    public List<Subject> selectList(Integer id) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("parent_id",id);
        List<Subject> list=subjectMapper.selectList(wrapper);

        for(Subject sub:list){
            Long subId=sub.getId();
            sub.setHasChildren(hasChild(subId));
        }
        return list;
    }

    @Override
    public void importData(MultipartFile file) {
        try {
            //EasyExcel.read(file.getInputStream(), SubjectEeVo.class,new SubjectListener()).sheet()    不要自己new对象
            EasyExcel.read(file.getInputStream(),SubjectEeVo.class,subjectListener).sheet().doRead();
        } catch (IOException e) {
            throw new MyException(20001,"导入失败");
        }
    }

    private boolean hasChild(Long id){
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("parent_id",id);
        return subjectMapper.selectCount(wrapper)>0;
    }
}
