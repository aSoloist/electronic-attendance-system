package system.attendance.electronic.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import system.attendance.electronic.model.User;

/**
 * 仅用于测试
 * Created by ly on 2017/10/8 18:06
 */
@Repository
public interface UserMapper {
    User getUserByName(@Param("username") String username, @Param("password") String password);
    
    void register(User user);
}
