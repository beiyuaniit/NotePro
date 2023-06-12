package org.mvc.service;

import org.mvc.dao.UserMapper;
import org.mvc.dao.UserMapperImpl;
import org.mvc.pojo.User;

/**
 * @author: beiyuan
 * @className: UserServiceImpl
 * @date: 2022/6/28  18:58
 */
public class UserServiceImpl implements UserService {
    private UserMapper userMapper=new UserMapperImpl();
    @Override
    public int insert(User user) {
        System.out.println("业务逻辑层。。。");
        return userMapper.insert(user);
    }
}
