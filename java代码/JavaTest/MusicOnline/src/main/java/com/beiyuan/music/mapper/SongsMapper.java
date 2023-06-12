package com.beiyuan.music.mapper;

import com.beiyuan.music.entity.Songs;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author beiyuan
 * @since 2022-12-29
 */
@Repository
public interface SongsMapper extends BaseMapper<Songs> {

    List<Integer> getSongIdBySongSheetId(Long songSheetId);
}
