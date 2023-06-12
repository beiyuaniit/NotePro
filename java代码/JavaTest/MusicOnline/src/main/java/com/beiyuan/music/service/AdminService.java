package com.beiyuan.music.service;

import com.beiyuan.music.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 管理员 服务类
 * </p>
 *
 * @author beiyuan
 * @since 2022-12-29
 */
public interface AdminService extends IService<Admin> {
    public boolean verify(String username,String password);
}
