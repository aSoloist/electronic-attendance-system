package system.attendance.electronic.exception;

/**
 * @author Soloist
 * @version 1.0
 * @createtime 2018/5/26 22:46
 * @email ly@soloist.top
 * @description 未授权
 */
public class Unauthorized extends BaseException {
    public Unauthorized() {
        super("未授权", 401);
    }

    public Unauthorized(String message, Integer code) {
        super(message, code);
    }
}
