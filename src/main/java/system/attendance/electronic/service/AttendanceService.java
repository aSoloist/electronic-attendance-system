package system.attendance.electronic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.attendance.electronic.exception.*;
import system.attendance.electronic.mapper.AttendanceMapper;
import system.attendance.electronic.model.Attendance;
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
     * 检查出勤
     * @param userId
     * @return 
     */
    public Attendance checkAttendance(String userId) {
        Attendance todayAttendance = getTodayAttendance(userId);
        if (todayAttendance != null) {
            return todayAttendance;
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
     * @deprecated 加班状态尚有问题
     */
    public Attendance updateAttendance(String userId) {
        Attendance attendance = getTodayAttendance(userId);
        if (attendance == null) {
            throw new SystemErrorException();
        }

        if (attendance.getStatus() != 0 && attendance.getStatus() != 4) { // 已签到
            throw new AttendanceException("已经签到", 403);
        }

        attendance.setBeginTime(new Date());
        attendance.setStatus((byte) (attendance.getStatus().intValue() == 0 ? 1 : 4));
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

        if (attendance.getStatus() == 0) { // 尚未签到
            throw new AttendanceException("尚未签到", 403);
        }

        if (attendance.getStatus() == 2 || attendance.getStatus() == 3 || attendance.getStatus() == 5) { // 不允许签退
            throw new AttendanceException("不允许重复签退", 403);
        }

        attendance.setEndTime(new Date());
        attendance.setStatus((byte) 5);
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
