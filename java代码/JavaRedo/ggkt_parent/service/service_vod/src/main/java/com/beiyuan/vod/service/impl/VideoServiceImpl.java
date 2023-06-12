package com.beiyuan.vod.service.impl;


import com.beiyuan.model.vod.Video;
import com.beiyuan.vod.mapper.VideoMapper;
import com.beiyuan.vod.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author beiyuan
 * @since 2022-11-30
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

}
