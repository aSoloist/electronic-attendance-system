package system.attendance.electronic.controller;

import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import system.attendance.electronic.exception.UserException;
import system.attendance.electronic.model.BaseResponseBody;
import system.attendance.electronic.model.User;
import system.attendance.electronic.service.UserService;

import java.util.Map;

/**
 * @author Soloist
 * @version 1.0
 * @createtime 2018/5/26 15:01
 * @email ly@soloist.top
 * @description
 */
@RestController
@RequestMapping("/users")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 获取用户信息
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public BaseResponseBody get() {
        BaseResponseBody baseResponseBody = new BaseResponseBody();
        User user = userService.get(currentUserId);
        user.setId(null);
        user.setPassword(null);
        baseResponseBody.setData(user);
        return baseResponseBody;
    }

    /**
     * 检查用户名
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public BaseResponseBody checkUsername(@PathVariable String username) {
        Boolean checkUsername = userService.checkUsername(username);
        BaseResponseBody responseBody = new BaseResponseBody();
        responseBody.setData(checkUsername);
        return responseBody;
    }

    /**
     * 更新用户信息
     *
     * @param map
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public BaseResponseBody update(@RequestBody Map<String, String> map) {
        User user = new User(currentUserId, map.get("username"), map.get("password"));
        BaseResponseBody baseResponseBody = new BaseResponseBody();
        if (!(StringUtils.isEmptyOrWhitespaceOnly(user.getUsername()) &&
                StringUtils.isEmptyOrWhitespaceOnly(user.getPassword()))) {
            User userByUsername = userService.getUserByUsername(user.getUsername());
            if (userByUsername == null || userByUsername.getId().equals(currentUserId)) {
                User update = userService.update(user);
                user.setId(null);
                user.setPassword(null);
                baseResponseBody.setData(update);
            } else {
                throw new UserException("用户已存在", 500);
            }
        } else {
            throw new UserException("无效的用户名或密码", 500);
        }
        return baseResponseBody;
    }

}
