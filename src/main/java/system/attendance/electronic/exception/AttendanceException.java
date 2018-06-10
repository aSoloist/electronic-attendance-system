package system.attendance.electronic.exception;

/**
 * @author Soloist
 * @version 1.0
 * @createtime 2018/6/10 2:35
 * @email ly@soloist.top
 * @description
 */
public class AttendanceException extends BaseException {

    public AttendanceException() {
        super("出勤记录异常", 403);
    }

    public AttendanceException(String message, Integer code) {
        super(message, code);
    }
}
