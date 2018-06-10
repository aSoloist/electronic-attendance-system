package system.attendance.electronic.exception;

/**
 * @author Soloist
 * @version 1.0
 * @createtime 2018/6/10 2:31
 * @email ly@soloist.top
 * @description
 */
public class ApplicationException extends BaseException {

    public ApplicationException() {
        super("申请异常", 403);
    }

    public ApplicationException(String message, Integer code) {
        super(message, code);
    }
}
