package system.attendance.electronic.handle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import system.attendance.electronic.annotation.NoNeedAuthorized;
import system.attendance.electronic.common.AuthTokenUtil;
import system.attendance.electronic.exception.AuthorizationTimeout;
import system.attendance.electronic.exception.Unauthorized;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Soloist
 * @version 1.0
 * @createtime 2018/5/26 22:39
 * @email ly@soloist.top
 * @description 认证拦截
 */
public class AuthorizedHandlerInterceptor extends HandlerInterceptorAdapter {
    
    @Autowired
    private AuthTokenUtil authTokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        } else {
            HandlerMethod method = (HandlerMethod) handler;
            
            if (method.getBeanType().isAnnotationPresent(NoNeedAuthorized.class)) { // 当前方法有NoNeedAuthorized注解
                return true;
            } else if (method.getMethod().isAnnotationPresent(NoNeedAuthorized.class)) { // 当前类有NoNeedAuthorized注解
                return true;
            } else {
                String token = request.getHeader("token");
                if (token != null) {
                    Long userId = authTokenUtil.checkToken(token);
                    if (userId != null) {
                        return true;
                    } else {
                        throw new AuthorizationTimeout();
                    }
                } else {
                    throw new Unauthorized();
                }
            }
        }
    }
}
