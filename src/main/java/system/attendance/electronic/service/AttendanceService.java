package system.attendance.electronic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.attendance.electronic.exception.AlreadyAttendanceException;
import system.attendance.electronic.exception.NotAllowAttendanceException;
import system.attendance.electronic.exception.NotYetAttendanceException;
import system.attendance.electronic.exception.SystemErrorException;
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
public class AttendanceService implements IService<Attendance> {

    @Autowired
    private AttendanceMapper attendanceMapper;


    @Override
    public Attendance get(Long id) {
        return null;
    }

    /**
     * 获取指定用户某年某月签到记录
     *
     * @param userId
     * @param year
     * @param month
     * @return
     */
    public List<Attendance> getUserAttendance(Long userId, Integer year, Integer month) {
        AttendanceExample attendanceExample = new AttendanceExample();
        attendanceExample.createCriteria().andUserIdEqualTo(userId).andYearEqualTo(year)
                .andMonthEqualTo(month.byteValue());
        return attendanceMapper.selectByExample(attendanceExample);
    }

    private Attendance getTodayAttendance(Long userId) {
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
    public Attendance updateAttendance(Long userId) {
        Attendance attendance = getTodayAttendance(userId);
        if (attendance == null) {
            throw new SystemErrorException();
        }

        if (attendance.getStatus() != 0) { // 已签到
            throw new AlreadyAttendanceException();
        }

        attendance.setStatus((byte) (attendance.getIsWorkday().intValue() == 1 ? 1 : 4));
        attendance.setBeginTime(new Date());
        return update(attendance);
    }

    /**
     * 签退
     *
     * @param userId
     * @return
     */
    public Attendance delAttendance(Long userId) {
        Attendance attendance = getTodayAttendance(userId);
        if (attendance == null) {
            throw new SystemErrorException();
        }

        if (attendance.getStatus() == 0) { // 尚未签到
            throw new NotYetAttendanceException();
        }

        if (attendance.getStatus() == 2 || attendance.getStatus() == 3 || attendance.getStatus() == 5) { // 不允许签到
            throw new NotAllowAttendanceException();
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
    public int delete(Long id) {
        return attendanceMapper.deleteByPrimaryKey(id);
    }
}
