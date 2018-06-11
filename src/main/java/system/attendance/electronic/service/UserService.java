package system.attendance.electronic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.attendance.electronic.mapper.UserMapper;
import system.attendance.electronic.model.User;
import system.attendance.electronic.model.example.UserExample;

import java.util.List;

/**
 * @author Soloist
 * @version 1.0
 * @createtime 2018/5/23 22:28
 * @email ly@soloist.top
 * @description
 */
@Service
public class UserService implements IService<User, String> {

    @Autowired
    private UserMapper userMapper;

    /**
     * 获取所有员工
     *
     * @return
     */
    public List<User> getAll() {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andRootEqualTo((byte) 0);
        return userMapper.selectByExample(userExample);
    }

    /**
     * 通过id获取用户
     *
     * @param id 用户id
     * @return 用户类
     */
    @Override
    public User get(String id) {
        return userMapper.selectByPrimaryKey(id);
    }

    /**
     * 保存用户
     *
     * @param model 用户类
     * @return 用户类
     */
    @Override
    public User save(User model) {
        int insert = userMapper.insert(model);
        return insert == 1 ? model : null;
    }

    /**
     * 更新用户
     *
     * @param model 用户类 部分值可为null
     * @return 更新后用户
     */
    @Override
    public User update(User model) {
        userMapper.updateByPrimaryKeySelective(model);
        return userMapper.selectByPrimaryKey(model.getId());
    }

    /**
     * 删除用户
     *
     * @param id 用户id
     * @return 删除结果
     */
    @Override
    public int delete(String id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    /**
     * 登陆
     *
     * @param username 用户名
     * @return 用户
     */
    public User getUserByUsername(String username) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size() == 1) {
            return users.get(0);
        } else {
            return null;
        }
    }

    /**
     * 检查用户是否存在
     *
     * @param username
     * @return
     */
    public Boolean checkUsername(String username) {
        User userByUsername = getUserByUsername(username);
        return userByUsername != null;
    }
}
