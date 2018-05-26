package system.attendance.electronic.model;

/**
 * @author Soloist
 * @version 1.0
 * @createtime 2018/5/26 15:02
 * @email ly@soloist.top
 * @description
 */
public class BaseResponseBody {
    
    private Integer code = 200;
    private String msg = "success";
    private Object data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
