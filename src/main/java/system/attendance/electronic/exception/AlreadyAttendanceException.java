package system.attendance.electronic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Soloist
 * @version 1.0
 * @createtime 2018/6/8 15:28
 * @email ly@soloist.top
 * @description
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class AlreadyAttendanceException extends BaseException {

    public AlreadyAttendanceException() {
        super("已经签到", 403);
    }

    public AlreadyAttendanceException(String message, Integer code) {
        super(message, code);
    }
}
