package system.attendance.electronic.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import system.attendance.electronic.model.Application;
import system.attendance.electronic.model.Attendance;
import system.attendance.electronic.model.User;
import system.attendance.electronic.service.ApplicationService;
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
    
    @Autowired
    private ApplicationService applicationService;

    /**
     * 若在 00:00:00 生成记录，日期将会出现仍为昨天的错误
     */
    @Scheduled(cron = "0 1 0 * * ?")
    public void addPunchRecord() {
        final int year = calendar.getWeekYear();
        final int month = calendar.get(Calendar.MONTH) + 1;
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        // 生成记录
        List<User> users = userService.getAll();
        users.forEach(user -> {
            System.out.println(user);
            Attendance attendance = new Attendance();
            attendance.setId(SnowFlakeUtil.get());
            attendance.setUserId(user.getId());
            attendance.setStatus((byte) 0);
            attendance.setYear(year);
            attendance.setMonth((byte) month);
            attendance.setDay((byte) day);
            int i = calendar.get(Calendar.DAY_OF_WEEK);
            Integer isHoliday = DateDetectUtil.checkTodayIsHoliday();
            if (isHoliday == 2) { // 节假日
                attendance.setIsWorkday((byte) 0);
            } else if (isHoliday == 3) { // 调休
                attendance.setIsWorkday((byte) 1);
            } else if (isHoliday == 1) { // 非节假日
                if (i == Calendar.SATURDAY || i == Calendar.SUNDAY) { // 周末
                    attendance.setIsWorkday((byte) 0);
                } else {
                    attendance.setIsWorkday((byte) 1);
                }
            }
            Attendance save = attendanceService.save(attendance);
            System.out.println(save);
        });
        // 根据申请表更新记录
        List<Application> todayApplication = applicationService.getApplicationByDate(calendar.getTime());
        todayApplication.forEach(application -> {
            System.out.println(application);
            if (application.getResult().intValue() == 1) {
                String userId = application.getUserId();
                Attendance attendanceByDate = attendanceService.getAttendanceByDate(userId, year, month, day);
                if (application.getType().intValue() == 1) { // 请假
                    attendanceByDate.setStatus((byte) 2);
                } else if (application.getType().intValue() == 2) { // 出差
                    attendanceByDate.setStatus((byte) 3);
                } else if (application.getType().intValue() == 3) { // 加班
                    attendanceByDate.setStatus((byte) 4);
                }
                Attendance update = attendanceService.update(attendanceByDate);
                System.out.println(update);
            }
        });
    }
    
}
