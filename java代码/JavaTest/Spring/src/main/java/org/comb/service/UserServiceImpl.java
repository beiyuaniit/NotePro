package org.comb.service;

import org.comb.dao.UserMapper;
import org.comb.pojo.User;

/**
 * @author: beiyuan
 * @className: UserServiceImpl
 * @date: 2022/6/28  18:58
 */
public class UserServiceImpl implements UserService {
    private UserMapper userMapper;//不用手动创建了
    @Override
    public int insert(User user) {
        System.out.println("业务逻辑层。。。");
        return userMapper.insert(user);
    }

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
