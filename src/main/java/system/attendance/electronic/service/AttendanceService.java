package system.attendance.electronic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.attendance.electronic.mapper.AttendanceMapper;
import system.attendance.electronic.model.Attendance;
import system.attendance.electronic.model.example.AttendanceExample;

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
     * @param userId
     * @param year
     * @param month
     * @return
     */
    public List<Attendance> getUserAttendance(Long userId, Integer year, Integer month) {
        AttendanceExample attendanceExample = new AttendanceExample();
        attendanceExample.createCriteria().andUserIdEqualTo(userId).andYearEqualTo(year.byteValue())
                .andMonthEqualTo(month.byteValue());
        return attendanceMapper.selectByExample(attendanceExample);
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
