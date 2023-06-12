package com.beiyuan.music.service;

import com.beiyuan.music.entity.Songs;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author beiyuan
 * @since 2022-12-29
 */
public interface SongsService extends IService<Songs> {


    List<Integer> getSongId(Long songSheetId);
}
