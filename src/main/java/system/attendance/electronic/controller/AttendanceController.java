package system.attendance.electronic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import system.attendance.electronic.model.Attendance;
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
@RequestMapping("/attendance")
public class AttendanceController extends BaseController {

    @Autowired
    private AttendanceService attendanceService;

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

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public BaseResponseBody test() {
        Calendar calendar = Calendar.getInstance();
        BaseResponseBody responseBody = new BaseResponseBody();

        Attendance attendance = new Attendance();
        attendance.setUserId(currentUserId);
        attendance.setIsWorkday((byte) 1);
        attendance.setYear(calendar.getWeekYear());
        attendance.setMonth((byte) (calendar.get(Calendar.MONTH) + 1));
        attendance.setDay((byte) calendar.get(Calendar.DAY_OF_MONTH));

        responseBody.setData(attendanceService.save(attendance));
        return responseBody;
    }

}
