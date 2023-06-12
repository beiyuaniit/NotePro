package com.beiyuan.music.service;

import com.beiyuan.music.entity.Song;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 歌曲 服务类
 * </p>
 *
 * @author beiyuan
 * @since 2022-12-29
 */
public interface SongService extends IService<Song> {
    boolean updateCover(String uploadUrl, Integer id);

    boolean updateUrl(String uploadUrl, Integer id);
}
