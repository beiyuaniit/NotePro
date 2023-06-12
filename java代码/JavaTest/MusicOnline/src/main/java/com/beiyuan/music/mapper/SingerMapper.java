package com.beiyuan.music.mapper;

import com.beiyuan.music.entity.Singer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 歌手 Mapper 接口
 * </p>
 *
 * @author beiyuan
 * @since 2022-12-29
 */
@Repository
public interface SingerMapper extends BaseMapper<Singer> {

    int updateAvator(String uploadUrl, Integer id);
}
