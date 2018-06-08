package system.attendance.electronic.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import system.attendance.electronic.common.AuthTokenUtil;
import system.attendance.electronic.exception.BaseException;
import system.attendance.electronic.model.BaseResponseBody;

/**
 * @author Soloist
 * @version 1.0
 * @createtime 2018/5/21 16:26
 * @email ly@soloist.top
 * @description
 */
@RestController
public abstract class BaseController {
    
    protected Long currentUserId;
    
    @Autowired
    protected AuthTokenUtil authTokenUtil;
    
    @ModelAttribute
    private void getCurrentUser(@RequestHeader String token) {
        currentUserId = authTokenUtil.checkToken(token);
    }

    @ExceptionHandler({BaseException.class})
    public BaseResponseBody exceptionHandler(BaseException ex) {
        BaseResponseBody baseResponseBody = new BaseResponseBody();
        baseResponseBody.setCode(ex.getCode());
        baseResponseBody.setMsg(ex.getMessage());
        return baseResponseBody;
    }
    
}
