package system.attendance.electronic.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import system.attendance.electronic.model.Attendance;
import system.attendance.electronic.model.User;
import system.attendance.electronic.service.AttendanceService;
import system.attendance.electronic.service.UserService;

import java.util.Calendar;
import java.util.List;

/**
 * @author Soloist
 * @version 1.0
 * @createtime 2018/6/7 17:12
 * @email ly@soloist.top
 * @description 签到记录生成定时器
 */
@Component
public class PunchTimer {
    
    private Calendar calendar = Calendar.getInstance();
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private AttendanceService attendanceService;
    
    @Scheduled(cron = "0 0 0 * * ?")
    public void addPunchRecord() {
        int year = calendar.getWeekYear();
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        List<User> users = userService.getAll();
        users.forEach(user -> {
            Attendance attendance = new Attendance();
            attendance.setUserId(user.getId());
            attendance.setStatus((byte) 0);
            attendance.setYear(year);
            attendance.setMonth((byte) month);
            attendance.setDay((byte) day);
            attendanceService.save(attendance);
        });
    }
    
}
