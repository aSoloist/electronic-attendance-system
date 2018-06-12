package system.attendance.electronic.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

public class Application implements Serializable {
    private String id;

    private String userId;

    @JSONField(format = "yyyy-MM-dd")
    private Date beginDate;

    @JSONField(format = "yyyy-MM-dd")
    private Date endDate;

    /**
     * 0 通过 1 允许
     */
    private Byte result;

    /**
     * 1 请假 2 出差 3 加班
     */
    private Byte type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Byte getResult() {
        return result;
    }

    public void setResult(Byte result) {
        this.result = result;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Application{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", result=" + result +
                ", type=" + type +
                '}';
    }
}