package system.attendance.electronic.exception;

/**
 * @author Soloist
 * @version 1.0
 * @createtime 2018/5/26 23:50
 * @email ly@soloist.top
 * @description 授权超时
 */
public class AuthorizationTimeout extends BaseException {
    public AuthorizationTimeout() {
        super("授权超时", 403);
    }

    public AuthorizationTimeout(String message, Integer code) {
        super(message, code);
    }
}
