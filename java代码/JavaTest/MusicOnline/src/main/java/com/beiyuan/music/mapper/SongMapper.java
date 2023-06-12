package com.beiyuan.music.mapper;

import com.beiyuan.music.entity.Song;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 歌曲 Mapper 接口
 * </p>
 *
 * @author beiyuan
 * @since 2022-12-29
 */
@Repository
public interface SongMapper extends BaseMapper<Song> {

    int updateCover(String uploadUrl, Integer id);

    int updateUrl(String uploadUrl, Integer id);
}
