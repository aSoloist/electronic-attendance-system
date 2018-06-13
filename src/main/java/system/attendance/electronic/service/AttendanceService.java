package system.attendance.electronic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.attendance.electronic.exception.*;
import system.attendance.electronic.mapper.AttendanceMapper;
import system.attendance.electronic.model.Attendance;
import system.attendance.electronic.model.AttendanceCount;
import system.attendance.electronic.model.example.AttendanceExample;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Soloist
 * @version 1.0
 * @createtime 2018/6/7 17:35
 * @email ly@soloist.top
 * @description
 */
@Service
public class AttendanceService implements IService<Attendance, String> {

    @Autowired
    private AttendanceMapper attendanceMapper;


    @Override
    public Attendance get(String id) {
        return null;
    }

    /**
     * 出勤统计
     *
     * @param userId
     * @param year
     * @param month
     * @return
     */
    public AttendanceCount attendanceCount(String userId, Integer year, Integer month) {
        Calendar calendar = Calendar.getInstance();
        int nowYear = calendar.getWeekYear();
        int nowMonth = calendar.get(Calendar.MONTH) + 1;
        int nowDay = calendar.get(Calendar.DAY_OF_MONTH);

        List<Attendance> userAttendance = getUserAttendance(userId, year, month);
        AttendanceCount attendanceCount = new AttendanceCount();
        userAttendance.forEach(attendance -> {
            if (attendance.getYear() != nowYear || attendance.getMonth() != nowMonth || attendance.getDay() != nowDay) {
                long time = attendance.getEndTime().getTime() - attendance.getBeginTime().getTime();
                if (time < 8 * 60 * 60 * 1000) {
                    attendanceCount.increaseLeaveEarlyDays();
                }
                switch (attendance.getStatus()) {
                    case 0:
                        attendanceCount.increaseAbsenceDays();
                        break;
                    case 4:
                    case 5:
                        attendanceCount.increaseAttendanceDays();
                        break;
                    case 2:
                        attendanceCount.increaseLeaveDays();
                        break;
                    case 3:
                        attendanceCount.increaseTravelingDays();
                        break;
                }
            }
        });

        return attendanceCount;
    }

    /**
     * 检查出勤 0 允许签到 1 允许签退 2 都不允许
     *
     * @param userId
     * @return
     */
    public Integer checkAttendance(String userId) {
        Attendance attendance = getTodayAttendance(userId);
        Integer check = 0;
        if (attendance != null) {
            if (attendance.getIsWorkday().intValue() == 1) { // 工作日
                if (attendance.getStatus().intValue() == 0 || attendance.getStatus().intValue() == 4) { // 未出勤
                    check = 0;
                } else if (attendance.getStatus().intValue() == 1 || attendance.getStatus().intValue() == 6) { // 已签到
                    check = 1;
                } else if (attendance.getStatus().intValue() == 5 || attendance.getStatus().intValue() == 7) { // 已签退
                    check = 2;
                }
            } else { // 非工作日
                check = 2;
            }
            return check;
        } else {
            throw new AttendanceException("检查出勤失败", 500);
        }
    }

    /**
     * 获取指定日期的出勤记录
     *
     * @param userId
     * @param year
     * @param month
     * @param day
     * @return
     */
    public Attendance getAttendanceByDate(String userId, Integer year, Integer month, Integer day) {
        AttendanceExample attendanceExample = new AttendanceExample();
        attendanceExample.createCriteria().andUserIdEqualTo(userId).andYearEqualTo(year)
                .andMonthEqualTo(month.byteValue()).andDayEqualTo(day.byteValue());
        List<Attendance> attendances = attendanceMapper.selectByExample(attendanceExample);
        return attendances.size() == 1 ? attendances.get(0) : null;
    }

    /**
     * 获取指定用户某年某月签到记录
     *
     * @param userId
     * @param year
     * @param month
     * @return
     */
    public List<Attendance> getUserAttendance(String userId, Integer year, Integer month) {
        AttendanceExample attendanceExample = new AttendanceExample();
        attendanceExample.setOrderByClause("day desc");
        if (year == null && month == null) {
            Calendar calendar = Calendar.getInstance();
            year = calendar.getWeekYear();
            month = calendar.get(Calendar.MONTH) + 1;
        }
        attendanceExample.createCriteria().andUserIdEqualTo(userId).andYearEqualTo(year)
                .andMonthEqualTo(month.byteValue());
        return attendanceMapper.selectByExample(attendanceExample);
    }

    private Attendance getTodayAttendance(String userId) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.getWeekYear();
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        AttendanceExample attendanceExample = new AttendanceExample();
        attendanceExample.createCriteria().andUserIdEqualTo(userId).
                andYearEqualTo(year).andMonthEqualTo((byte) month).andDayEqualTo((byte) day);
        List<Attendance> attendances = attendanceMapper.selectByExample(attendanceExample);
        return attendances.size() == 1 ? attendances.get(0) : null;
    }

    /**
     * 签到
     *
     * @param userId
     * @return
     */
    public Attendance updateAttendance(String userId) {
        Attendance attendance = getTodayAttendance(userId);
        if (attendance == null) {
            throw new SystemErrorException();
        }

        if (attendance.getIsWorkday().intValue() == 0) {
            throw new AttendanceException("非工作日不允许签到", 403);
        }

        if (attendance.getStatus().intValue() == 1) { // 已签到
            throw new AttendanceException("已经签到", 403);
        } else if (attendance.getStatus().intValue() == 2 || attendance.getStatus().intValue() == 3) { // 不允许签到
            throw new AttendanceException("不允许签到", 403);
        } else if (attendance.getStatus().intValue() == 5) { // 已签退
            throw new AttendanceException("已经签退", 403);
        }

        attendance.setBeginTime(new Date());
        attendance.setStatus((byte) (attendance.getStatus().intValue() == 0 ? 1 : 6));
        return update(attendance);
    }

    /**
     * 签退
     *
     * @param userId
     * @return
     */
    public Attendance delAttendance(String userId) {
        Attendance attendance = getTodayAttendance(userId);
        if (attendance == null) {
            throw new SystemErrorException();
        }

        if (attendance.getIsWorkday().intValue() == 0) {
            throw new AttendanceException("非工作日不允许签退", 403);
        }

        if (attendance.getStatus().intValue() == 0 || attendance.getStatus().intValue() == 4) { // 尚未签到
            throw new AttendanceException("尚未签到", 403);
        } else if (attendance.getStatus().intValue() == 2 || attendance.getStatus().intValue() == 3) { // 不允许签退
            throw new AttendanceException("不允许签退", 403);
        } else if (attendance.getStatus().intValue() == 5 || attendance.getStatus().intValue() == 7) {
            throw new AttendanceException("不允许重复签退", 403);
        }

        attendance.setEndTime(new Date());
        attendance.setStatus((byte) (attendance.getStatus().intValue() == 1 ? 5 : 7));
        return update(attendance);
    }

    @Override
    public Attendance save(Attendance model) {
        int i = attendanceMapper.insertSelective(model);
        return i == 1 ? model : null;
    }

    @Override
    public Attendance update(Attendance model) {
        int i = attendanceMapper.updateByPrimaryKeySelective(model);
        return i == 1 ? model : null;
    }

    @Override
    public int delete(String id) {
        return attendanceMapper.deleteByPrimaryKey(id);
    }
}
