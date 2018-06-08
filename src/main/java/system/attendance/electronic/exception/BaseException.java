package system.attendance.electronic.exception;

/**
 * @author Soloist
 * @version 1.0
 * @createtime 2018/6/8 14:39
 * @email ly@soloist.top
 * @description
 */
public abstract class BaseException extends RuntimeException {
    
    private Integer code;

    public BaseException(String message) {
        super(message);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
