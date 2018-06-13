package system.attendance.electronic.controller;

import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import system.attendance.electronic.annotation.NoNeedAuthorized;
import system.attendance.electronic.common.AuthTokenUtil;
import system.attendance.electronic.common.SnowFlakeUtil;
import system.attendance.electronic.exception.BaseException;
import system.attendance.electronic.exception.UserException;
import system.attendance.electronic.model.AuthToken;
import system.attendance.electronic.model.BaseResponseBody;
import system.attendance.electronic.model.User;
import system.attendance.electronic.service.UserService;

/**
 * @author Soloist
 * @version 1.0
 * @createtime 2018/5/26 22:56
 * @email ly@soloist.top
 * @description
 */
@CrossOrigin
@NoNeedAuthorized
@RestController
public class PublicController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private AuthTokenUtil authTokenUtil;

    /**
     * 用户登陆
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public BaseResponseBody login(@RequestParam String username,
                                  @RequestParam String password) {
        BaseResponseBody baseResponseBody = new BaseResponseBody();
        User userByUsername = userService.getUserByUsername(username);
        if (userByUsername != null) {
            if (userByUsername.getPassword().equals(password)) {
                AuthToken authToken = authTokenUtil.getToken(userByUsername.getId());
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("token", authToken.getToken());
                jsonObject.put("root", userByUsername.getRoot());
                baseResponseBody.setData(jsonObject);
            } else {
                throw new UserException("密码错误", 403);
            }
        } else {
            throw new UserException("用户不存在", 403);
        }
        return baseResponseBody;
    }

    /**
     * 用户注册
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public BaseResponseBody register(@RequestParam String username,
                                     @RequestParam String password) {
        BaseResponseBody baseResponseBody = new BaseResponseBody();
        if (!(StringUtils.isEmptyOrWhitespaceOnly(username) && 
                StringUtils.isEmptyOrWhitespaceOnly(password))) {
            User user = userService.getUserByUsername(username);
            if (user == null) {
                user = new User();
                user.setId(SnowFlakeUtil.get());
                user.setUsername(username);
                user.setPassword(password);
                user.setRoot((byte) 0);
                userService.save(user);
                AuthToken authToken = authTokenUtil.getToken(user.getId());
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("token", authToken.getToken());
                jsonObject.put("root", user.getRoot());
                baseResponseBody.setData(jsonObject);
            } else {
                throw new UserException("用户已存在", 500);
            }
        } else {
            throw new UserException("无效的用户名或密码", 500);
        }
        return baseResponseBody;
    }

    /**
     * 检查用户名
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public BaseResponseBody checkUsername(@RequestParam String username) {
        Boolean checkUsername = userService.checkUsername(username);
        BaseResponseBody responseBody = new BaseResponseBody();
        responseBody.setData(checkUsername);
        return responseBody;
    }

    @ExceptionHandler({BaseException.class})
    public BaseResponseBody exceptionHandler(BaseException ex) {
        BaseResponseBody baseResponseBody = new BaseResponseBody();
        baseResponseBody.setCode(ex.getCode());
        baseResponseBody.setMsg(ex.getMessage());
        return baseResponseBody;
    }
    
}
