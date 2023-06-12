package org.annomvc.service;

import org.annomvc.dao.UserMapper;
import org.annomvc.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: beiyuan
 * @className: UserServiceImpl
 * @date: 2022/6/28  18:58
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;//不用手动创建了
    @Override
    public int insert(User user) {
        System.out.println("业务逻辑层。。。");
        return userMapper.insert(user);
    }

}
