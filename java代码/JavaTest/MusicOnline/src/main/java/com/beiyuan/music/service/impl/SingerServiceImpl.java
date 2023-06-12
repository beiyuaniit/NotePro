package com.beiyuan.music.service.impl;

import com.beiyuan.music.entity.Singer;
import com.beiyuan.music.mapper.SingerMapper;
import com.beiyuan.music.service.SingerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 歌手 服务实现类
 * </p>
 *
 * @author beiyuan
 * @since 2022-12-29
 */
@Service
public class SingerServiceImpl extends ServiceImpl<SingerMapper, Singer> implements SingerService {

    @Autowired
    SingerMapper singerMapper;
    @Override
    public Boolean updateAvator(String uploadUrl, Integer id) {
        if(singerMapper.updateAvator(uploadUrl,id)==1){
            return true;
        }
        return false;
    }
}
