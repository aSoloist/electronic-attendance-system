package system.attendance.electronic.exception;

/**
 * @author Soloist
 * @version 1.0
 * @createtime 2018/6/8 15:22
 * @email ly@soloist.top
 * @description
 */
public class SystemErrorException extends BaseException {
    
    public SystemErrorException() {
        super("系统异常", 503);
    }
    
    public SystemErrorException(String message, Integer code) {
        super(message, code);
    }
}
