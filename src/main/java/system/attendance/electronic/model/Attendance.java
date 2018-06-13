package system.attendance.electronic.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

public class Attendance implements Serializable {
    private String id;

    private String userId;

    @JSONField(format = "HH:mm:ss")
    private Date beginTime;

    @JSONField(format = "HH:mm:ss")
    private Date endTime;

    /**
     * 0 未出勤 1 出勤 2 请假 3 出差 4 加班 5 签退 6 加班签到 7 加班签退
     */
    private Byte status;

    private Byte month;

    /**
     * 0 否 1 是
     */
    private Byte isWorkday;

    private Byte day;

    private Integer year;

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
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
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