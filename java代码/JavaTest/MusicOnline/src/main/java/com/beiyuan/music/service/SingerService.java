package com.beiyuan.music.service;

import com.beiyuan.music.entity.Singer;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 歌手 服务类
 * </p>
 *
 * @author beiyuan
 * @since 2022-12-29
 */
public interface SingerService extends IService<Singer> {
    Boolean updateAvator(String uploadUrl, Integer id);
}
