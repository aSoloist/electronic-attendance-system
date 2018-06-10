package system.attendance.electronic.handle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import system.attendance.electronic.annotation.NeedAdmin;
import system.attendance.electronic.common.AuthTokenUtil;
import system.attendance.electronic.exception.UserException;
import system.attendance.electronic.model.User;
import system.attendance.electronic.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Soloist
 * @version 1.0
 * @createtime 2018/6/10 15:46
 * @email ly@soloist.top
 * @description
 */
public class AdminHandlerInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserService userService;
    
    @Autowired
    private AuthTokenUtil authTokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        } else {
            HandlerMethod method = (HandlerMethod) handler;
            // 当前方法有NeedAdmin注解
            if (method.getBeanType().isAnnotationPresent(NeedAdmin.class) || method.getMethod().isAnnotationPresent(NeedAdmin.class)) {
                String token = request.getHeader("token");
                Long userId = authTokenUtil.checkToken(token);
                User user = userService.get(userId);
                if (user.getRoot() >= 10) {
                    return true;
                } else {
                    throw new UserException("权限不足", 403);
                }
            } else {
                return true;
            }
        }
    }
}
