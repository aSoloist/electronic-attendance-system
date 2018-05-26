package system.attendance.electronic.exception;

/**
 * @author Soloist
 * @version 1.0
 * @createtime 2018/5/26 23:50
 * @email ly@soloist.top
 * @description 授权超时
 */
public class AuthorizationTimeout extends RuntimeException {
    public AuthorizationTimeout() {
        super("Authorization timeout");
    }

    public AuthorizationTimeout(String message) {
        super(message);
    }
}
