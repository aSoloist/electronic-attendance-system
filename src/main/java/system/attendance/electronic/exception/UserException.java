package system.attendance.electronic.exception;

/**
 * @author Soloist
 * @version 1.0
 * @createtime 2018/6/10 3:09
 * @email ly@soloist.top
 * @description
 */
public class UserException extends BaseException {

    public UserException() {
        super("用户异常", 500);
    }

    public UserException(String message, Integer code) {
        super(message, code);
    }
}
