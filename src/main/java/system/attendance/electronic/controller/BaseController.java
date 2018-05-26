package system.attendance.electronic.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;
import system.attendance.electronic.common.AuthTokenUtil;

import javax.servlet.http.HttpServletRequest;

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
    private void getCurrentUser(HttpServletRequest request) {
        String token = request.getHeader("token");
        currentUserId = authTokenUtil.checkToken(token);
    }
    
}
