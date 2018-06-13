package system.attendance.electronic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import system.attendance.electronic.model.Attendance;
import system.attendance.electronic.model.AttendanceCount;
import system.attendance.electronic.model.BaseResponseBody;
import system.attendance.electronic.service.AttendanceService;

import java.util.List;

/**
 * @author Soloist
 * @version 1.0
 * @createtime 2018/6/8 14:58
 * @email ly@soloist.top
 * @description
 */
@CrossOrigin
@RestController
@RequestMapping("/attendances")
public class AttendanceController extends BaseController {

    @Autowired
    private AttendanceService attendanceService;

    /**
     * 0 允许签到 1 允许签退 2 都不允许
     *
     * @return
     */
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public BaseResponseBody checkAttendance() {
        BaseResponseBody responseBody = new BaseResponseBody();
        Integer check = attendanceService.checkAttendance(currentUserId);
        responseBody.setData(check);
        return responseBody;
    }

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
     * 获取当月出勤
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public BaseResponseBody getAttendancesByUser() {
        List<Attendance> userAttendance = attendanceService.getUserAttendance(currentUserId, null, null);

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

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public BaseResponseBody attendanceCount() {
        AttendanceCount attendanceCount = attendanceService.attendanceCount(currentUserId, null, null);
        BaseResponseBody responseBody = new BaseResponseBody();
        responseBody.setData(attendanceCount);
        return responseBody;
    }

    /**
     * 统计出勤记录
     *
     * @param year
     * @param month
     * @return
     */
    @RequestMapping(value = "/count/{year}/{month}", method = RequestMethod.GET)
    public BaseResponseBody attendanceCount(@PathVariable Integer year,
                                            @PathVariable Integer month) {
        AttendanceCount attendanceCount = attendanceService.attendanceCount(currentUserId, year, month);
        BaseResponseBody responseBody = new BaseResponseBody();
        responseBody.setData(attendanceCount);
        return responseBody;
    }

}
