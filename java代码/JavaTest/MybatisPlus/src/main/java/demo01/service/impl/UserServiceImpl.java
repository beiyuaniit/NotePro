package demo01.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import demo01.entity.User;
import demo01.service.UserService;
import demo01.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




