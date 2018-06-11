package system.attendance.electronic.model;

/**
 * @author Soloist
 * @version 1.0
 * @createtime 2018/6/12 0:26
 * @email ly@soloist.top
 * @description 考勤统计
 */
public class AttendanceCount {
    
    // 出勤天数
    private Integer attendanceDays = 0;
    // 缺勤天数
    private Integer absenceDays = 0;
    // 早退天数
    private Integer leaveEarlyDays = 0;
    // 请假天数
    private Integer leaveDays = 0;
    // 出差天数
    private Integer travelingDays = 0;
    
    public void increaseAttendanceDays() {
        this.attendanceDays++;
    }

    public void increaseAbsenceDays() {
        this.absenceDays++;
    }

    public void increaseLeaveEarlyDays() {
        this.leaveEarlyDays++;
    }

    public void increaseLeaveDays() {
        this.leaveDays++;
    }

    public void increaseTravelingDays() {
        this.travelingDays++;
    }

    public Integer getAttendanceDays() {
        return attendanceDays;
    }

    public void setAttendanceDays(Integer attendanceDays) {
        this.attendanceDays = attendanceDays;
    }

    public Integer getAbsenceDays() {
        return absenceDays;
    }

    public void setAbsenceDays(Integer absenceDays) {
        this.absenceDays = absenceDays;
    }

    public Integer getLeaveEarlyDays() {
        return leaveEarlyDays;
    }

    public void setLeaveEarlyDays(Integer leaveEarlyDays) {
        this.leaveEarlyDays = leaveEarlyDays;
    }

    public Integer getLeaveDays() {
        return leaveDays;
    }

    public void setLeaveDays(Integer leaveDays) {
        this.leaveDays = leaveDays;
    }

    public Integer getTravelingDays() {
        return travelingDays;
    }

    public void setTravelingDays(Integer travelingDays) {
        this.travelingDays = travelingDays;
    }
}
