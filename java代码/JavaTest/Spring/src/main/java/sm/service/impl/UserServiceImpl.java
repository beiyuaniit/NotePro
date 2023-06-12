package sm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sm.mapper.UserMapper;
import sm.pojo.User;
import sm.service.UserService;

/**
 * @author: beiyuan
 * @className: UserServiceImpl
 * @date: 2022/7/1  21:53
 */
@Service

//@Transactional(propagation = Propagation.REQUIRED,//事务隔离级别（增删改都用REQUIRED
//        noRollbackForClassName = "ArithmeticException",//发生该异常不回滚.使用异常名
//        noRollbackFor = ArithmeticException.class,//发生该异常不回滚，使用异常类型
//        rollbackForClassName = "",//发生了该异常一定回滚
//        //rollbackFor = ArithmeticException.class,//发生了该异常一定回滚
//        timeout = -1,//连接超时时间。默认-1，永不超时
//        readOnly = false,//修改数据权限，查询则要设置为true
//        isolation = Isolation.DEFAULT//设置数据库隔离级别。此处表示使用默认
//)
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper mapper;
    @Override
    public int insert(User user) {
        int res=mapper.insert(user);
        int error=1/0;
        return res;
    }
}
