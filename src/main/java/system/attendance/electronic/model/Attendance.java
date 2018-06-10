package system.attendance.electronic.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

public class Attendance implements Serializable {
    private Long id;

    private Long userId;

    @JSONField(format = "HH:mm:ss")
    private Date beginTime;

    @JSONField(format = "HH:mm:ss")
    private Date endTime;

    private Byte status;

    private Byte month;

    private Byte isWorkday;

    private Byte day;

    private Integer year;

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

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getMonth() {
        return month;
    }

    public void setMonth(Byte month) {
        this.month = month;
    }

    public Byte getIsWorkday() {
        return isWorkday;
    }

    public void setIsWorkday(Byte isWorkday) {
        this.isWorkday = isWorkday;
    }

    public Byte getDay() {
        return day;
    }

    public void setDay(Byte day) {
        this.day = day;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "id=" + id +
                ", userId=" + userId +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", status=" + status +
                ", month=" + month +
                ", isWorkday=" + isWorkday +
                ", day=" + day +
                ", year=" + year +
                '}';
    }
}