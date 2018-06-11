package system.attendance.electronic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import system.attendance.electronic.model.Attendance;
import system.attendance.electronic.model.AttendanceCount;
import system.attendance.electronic.model.BaseResponseBody;
import system.attendance.electronic.service.AttendanceService;

import java.util.Calendar;
import java.util.List;

/**
 * @author Soloist
 * @version 1.0
 * @createtime 2018/6/8 14:58
 * @email ly@soloist.top
 * @description
 */
@RestController
@RequestMapping("/attendances")
public class AttendanceController extends BaseController {

    @Autowired
    private AttendanceService attendanceService;

    /**
     * 按年月获取出勤
     *
     * @param year
     * @param month
     * @return
     */
    @RequestMapping(value = "/{year}/{month}", method = RequestMethod.GET)
    public BaseResponseBody getAttendancesByUser(@PathVariable Integer year, @PathVariable Integer month) {
        List<Attendance> userAttendance = attendanceService.getUserAttendance(currentUserId, year, month);

        userAttendance.forEach(attendance -> {
            attendance.setId(null);
            attendance.setUserId(null);
        });

        BaseResponseBody responseBody = new BaseResponseBody();
        responseBody.setData(userAttendance);
        return responseBody;
    }

    /**
     * 签到
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public BaseResponseBody attendance() {
        BaseResponseBody responseBody = new BaseResponseBody();
        Attendance attendance = attendanceService.updateAttendance(currentUserId);

        // 清除敏感数据
        attendance.setId(null);
        attendance.setUserId(null);
        attendance.setEndTime(null);
        attendance.setIsWorkday(null);

        responseBody.setData(attendance);
        return responseBody;
    }

    /**
     * 签退
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public BaseResponseBody signOff() {
        BaseResponseBody responseBody = new BaseResponseBody();
        Attendance attendance = attendanceService.delAttendance(currentUserId);

        // 清除敏感数据
        attendance.setId(null);
        attendance.setUserId(null);
        attendance.setIsWorkday(null);

        responseBody.setData(attendance);
        return responseBody;
    }

    @RequestMapping(value = "/count/{year}/{month}", method = RequestMethod.GET)
    public BaseResponseBody attendanceCount(@PathVariable Integer year,
                                            @PathVariable Integer month) {
        Calendar calendar = Calendar.getInstance();
        int nowYear = calendar.getWeekYear();
        int nowMonth = calendar.get(Calendar.MONTH);
        int nowDay = calendar.get(Calendar.DAY_OF_MONTH);

        List<Attendance> userAttendance = attendanceService.getUserAttendance(currentUserId, year, month);
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
                    case 1:
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
        BaseResponseBody responseBody = new BaseResponseBody();
        responseBody.setData(attendanceCount);
        return responseBody;
    }

}
