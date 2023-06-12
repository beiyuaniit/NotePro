package com.beiyuan.music.service.impl;

import com.beiyuan.music.entity.User;
import com.beiyuan.music.mapper.UserMapper;
import com.beiyuan.music.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 普通用户 服务实现类
 * </p>
 *
 * @author beiyuan
 * @since 2022-12-29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
