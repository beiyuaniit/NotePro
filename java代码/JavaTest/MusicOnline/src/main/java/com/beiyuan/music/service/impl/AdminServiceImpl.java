package com.beiyuan.music.service.impl;

import com.beiyuan.music.entity.Admin;
import com.beiyuan.music.mapper.AdminMapper;
import com.beiyuan.music.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员 服务实现类
 * </p>
 *
 * @author beiyuan
 * @since 2022-12-29
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Autowired
    AdminMapper adminMapper;

    @Override
    public boolean verify(String username, String password) {
        if(username==null || password==null){
            return false;
        }
        return adminMapper.verify(username,password)==1;
    }
}
