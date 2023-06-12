package demo01.mapper;
import java.util.List;

import demo01.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Entity demo01.entity.User
 */
public interface UserMapper extends BaseMapper<User> {
    List<User> getUsernameAndPassword();
}




