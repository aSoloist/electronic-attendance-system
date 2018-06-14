package system.attendance.electronic.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import system.attendance.electronic.annotation.NoNeedAuthorized;
import system.attendance.electronic.common.AuthTokenUtil;
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

    @ExceptionHandler({BaseException.class})
    public BaseResponseBody exceptionHandler(BaseException ex) {
        BaseResponseBody baseResponseBody = new BaseResponseBody();
        baseResponseBody.setCode(ex.getCode());
        baseResponseBody.setMsg(ex.getMessage());
        return baseResponseBody;
    }
    
}
