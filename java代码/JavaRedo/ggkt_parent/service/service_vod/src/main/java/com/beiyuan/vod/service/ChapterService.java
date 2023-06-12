package com.beiyuan.vod.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.beiyuan.model.vod.Chapter;
import com.beiyuan.vo.vod.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author beiyuan
 * @since 2022-11-30
 */
public interface ChapterService extends IService<Chapter> {
    //章节小结列表
    List<ChapterVo> getNestedTreeList(Long courseId);
}
