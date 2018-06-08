package system.attendance.electronic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Soloist
 * @version 1.0
 * @createtime 2018/6/8 15:38
 * @email ly@soloist.top
 * @description
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class NotYetAttendanceException extends BaseException {

    public NotYetAttendanceException() {
        super("尚未签到", 403);
    }

    public NotYetAttendanceException(String message, Integer code) {
        super(message, code);
    }
}
