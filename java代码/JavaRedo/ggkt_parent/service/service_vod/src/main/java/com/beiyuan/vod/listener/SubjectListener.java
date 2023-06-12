package com.beiyuan.vod.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.beiyuan.model.vod.Subject;
import com.beiyuan.vo.vod.SubjectEeVo;
import com.beiyuan.vod.mapper.SubjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: beiyuan
 * @date: 2022/11/30  21:27
 */
@Component
public class SubjectListener extends AnalysisEventListener<SubjectEeVo> {
    @Autowired
    private SubjectMapper subjectMapper;

    @Override
    public void invoke(SubjectEeVo data, AnalysisContext context) {
        Subject subject=new Subject();
        BeanUtils.copyProperties(data,subject);
        subjectMapper.insert(subject);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
