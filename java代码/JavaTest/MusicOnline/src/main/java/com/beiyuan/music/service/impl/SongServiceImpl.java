package com.beiyuan.music.service.impl;

import com.beiyuan.music.entity.Song;
import com.beiyuan.music.mapper.SongMapper;
import com.beiyuan.music.service.SongService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 歌曲 服务实现类
 * </p>
 *
 * @author beiyuan
 * @since 2022-12-29
 */
@Service
public class SongServiceImpl extends ServiceImpl<SongMapper, Song> implements SongService {

    @Autowired
    SongMapper songMapper;
    @Override
    public boolean updateCover(String uploadUrl, Integer id) {

        return songMapper.updateCover(uploadUrl, id)==1;
    }

    @Override
    public boolean updateUrl(String uploadUrl, Integer id) {
        return songMapper.updateUrl(uploadUrl, id)==1;
    }
}
