package system.attendance.electronic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Soloist
 * @version 1.0
 * @createtime 2018/5/26 22:46
 * @email ly@soloist.top
 * @description 未授权
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class Unauthorized extends BaseException {
    public Unauthorized() {
        super("未授权");
        setCode(401);
    }

    public Unauthorized(String message, Integer code) {
        super(message);
        setCode(code);
    }
}
