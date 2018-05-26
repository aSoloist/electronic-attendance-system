package system.attendance.electronic.model;

import system.attendance.electronic.common.SnowFlakeUtil;

import java.io.Serializable;
import java.util.Date;

public class Attendance implements Serializable {
    private Long id = SnowFlakeUtil.get();

    private Long userId;

    private Date beginTime;

    private Date endTime;

    private Byte status;

    private Date nowDate;

    private Byte isWorkday;

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

    public Date getNowDate() {
        return nowDate;
    }

    public void setNowDate(Date nowDate) {
        this.nowDate = nowDate;
    }

    public Byte getIsWorkday() {
        return isWorkday;
    }

    public void setIsWorkday(Byte isWorkday) {
        this.isWorkday = isWorkday;
    }
}