package system.attendance.electronic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Soloist
 * @version 1.0
 * @createtime 2018/6/8 15:54
 * @email ly@soloist.top
 * @description
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class NotAllowAttendanceException extends BaseException {

    public NotAllowAttendanceException() {
        super("不允许重复签到", 403);
    }

    public NotAllowAttendanceException(String message, Integer code) {
        super(message, code);
    }
}
