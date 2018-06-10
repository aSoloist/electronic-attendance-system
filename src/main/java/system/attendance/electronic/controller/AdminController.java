package system.attendance.electronic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import system.attendance.electronic.annotation.NeedAdmin;
import system.attendance.electronic.model.*;
import system.attendance.electronic.service.ApplicationService;
import system.attendance.electronic.service.AttendanceService;
import system.attendance.electronic.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Soloist
 * @version 1.0
 * @createtime 2018/6/10 16:13
 * @email ly@soloist.top
 * @description
 */
@NeedAdmin
@RequestMapping("/admin")
@RestController
public class AdminController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private AttendanceService attendanceService;

    /**
     * 所有用户
     *
     * @return
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public BaseResponseBody listUsers() {
        List<User> all = userService.getAll();
        all.forEach(user -> user.setPassword(null));
        BaseResponseBody responseBody = new BaseResponseBody();
        responseBody.setData(all);
        return responseBody;
    }

    /**
     * 所有申请
     *
     * @param year
     * @param month
     * @return
     */
    @RequestMapping(value = "/applications/{year}/{month}", method = RequestMethod.GET)
    public BaseResponseBody listApplications(@PathVariable Integer year,
                                             @PathVariable Integer month) {
        List<Application> applicationMonth = applicationService.getApplicationMonth(null, year, month);
        List<ApplicationAndUser> applicationAndUserList = new ArrayList<>();
        applicationMonth.forEach(application -> {
            ApplicationAndUser applicationAndUser = new ApplicationAndUser();
            User user = userService.get(application.getUserId());
            user.setPassword(null);
            applicationAndUser.setApplication(application);
            applicationAndUser.setUser(user);
            applicationAndUserList.add(applicationAndUser);
        });
        BaseResponseBody responseBody = new BaseResponseBody();
        responseBody.setData(applicationAndUserList);
        return responseBody;
    }

    /**
     * 审核
     *
     * @param id
     * @param result
     * @return
     */
    @RequestMapping(value = "/applications/{id}/{result}", method = RequestMethod.PUT)
    public BaseResponseBody updateApplication(@PathVariable Long id,
                                              @PathVariable Integer result) {
        Application application = applicationService.updateResult(id, result);
        User user = userService.get(application.getUserId());
        ApplicationAndUser applicationAndUser = new ApplicationAndUser();
        applicationAndUser.setUser(user);
        user.setPassword(null);
        applicationAndUser.setApplication(application);
        BaseResponseBody responseBody = new BaseResponseBody();
        responseBody.setData(applicationAndUser);
        return responseBody;
    }

    /**
     * 获取指定用户出勤
     *
     * @param userId
     * @param year
     * @param month
     * @return
     */
    @RequestMapping(value = "/attendances/{userId}/{year}/{month}", method = RequestMethod.GET)
    public BaseResponseBody listAttendances(@PathVariable Long userId,
                                            @PathVariable Integer year,
                                            @PathVariable Integer month) {
        List<Attendance> userAttendance = attendanceService.getUserAttendance(userId, year, month);
        BaseResponseBody responseBody = new BaseResponseBody();
        responseBody.setData(userAttendance);
        return responseBody;
    }

}
