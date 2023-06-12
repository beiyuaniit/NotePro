package org.comb.dao;

import org.comb.pojo.User;

/**
 * @author: beiyuan
 * @className: UserMapperImpl
 * @date: 2022/6/28  18:33
 */
public class UserMapperImpl implements UserMapper {
    @Override
    public int insert(User user) {
        System.out.println(user.getName()+"数据访问层mapper插入成功");
        return 1;
    }
}
