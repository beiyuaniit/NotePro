package com.beiyuan.music.mapper;

import com.beiyuan.music.entity.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 管理员 Mapper 接口
 * </p>
 *
 * @author beiyuan
 * @since 2022-12-29
 */
@Repository
public interface AdminMapper extends BaseMapper<Admin> {
    public int verify(String username,String password);
}
