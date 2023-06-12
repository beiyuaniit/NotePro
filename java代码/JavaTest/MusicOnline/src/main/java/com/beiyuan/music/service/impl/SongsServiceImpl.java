package com.beiyuan.music.service.impl;

import com.beiyuan.music.entity.Songs;
import com.beiyuan.music.mapper.SongsMapper;
import com.beiyuan.music.service.SongsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author beiyuan
 * @since 2022-12-29
 */
@Service
public class SongsServiceImpl extends ServiceImpl<SongsMapper, Songs> implements SongsService {


    @Autowired
    SongsMapper songsMapper;
    @Override
    public List<Integer> getSongId(Long songSheetId) {

        return songsMapper.getSongIdBySongSheetId(songSheetId);
    }
}
