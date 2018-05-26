package system.attendance.electronic.controller;

import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 获取用户信息
     *
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public BaseResponseBody get() {
        BaseResponseBody baseResponseBody = new BaseResponseBody();
        User user = userService.get(currentUserId);
        baseResponseBody.setData(user);
        return baseResponseBody;
    }

    /**
     * 更新用户信息
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.PUT)
    public BaseResponseBody update(@RequestBody Map<String, String> map) {
        User user = new User(currentUserId, map.get("username"), map.get("password"));
        BaseResponseBody baseResponseBody = new BaseResponseBody();
        if (!(StringUtils.isEmptyOrWhitespaceOnly(user.getUsername()) &&
                StringUtils.isEmptyOrWhitespaceOnly(user.getPassword()))) {
            User userByUsername = userService.getUserByUsername(user.getUsername());
            if (userByUsername == null || userByUsername.getId().equals(currentUserId)) {
                User update = userService.update(user);
                baseResponseBody.setData(update);
            } else {
                baseResponseBody.setCode(500);
                baseResponseBody.setMsg("User already exists");
            }
        } else {
            baseResponseBody.setCode(500);
            baseResponseBody.setMsg("invalid username or password");
        }
        return baseResponseBody;
    }

}