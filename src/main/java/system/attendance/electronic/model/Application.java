package system.attendance.electronic.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

public class Application implements Serializable {
    private Long id;

    private Long userId;

    @JSONField(format = "yyyy-MM-dd")
    private Date beginDate;

    @JSONField(format = "yyyy-MM-dd")
    private Date endDate;

    private Byte result;

    private Byte type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
                "id=" + id +
                ", userId=" + userId +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", result=" + result +
                ", type=" + type +
                '}';
    }
}