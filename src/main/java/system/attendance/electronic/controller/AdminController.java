package system.attendance.electronic.controller;

import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import system.attendance.electronic.annotation.NeedAdmin;
import system.attendance.electronic.common.SnowFlakeUtil;
import system.attendance.electronic.exception.ApplicationException;
import system.attendance.electronic.exception.UserException;
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
@CrossOrigin
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
     * 检查用户名
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "/users/{username}", method = RequestMethod.GET)
    public BaseResponseBody checkUsername(@PathVariable String username) {
        Boolean checkUsername = userService.checkUsername(username);
        BaseResponseBody responseBody = new BaseResponseBody();
        responseBody.setData(checkUsername);
        return responseBody;
    }

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public BaseResponseBody register(@RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        BaseResponseBody baseResponseBody = new BaseResponseBody();
        if (!(StringUtils.isEmptyOrWhitespaceOnly(username) &&
                StringUtils.isEmptyOrWhitespaceOnly(password))) {
            User isExist = userService.getUserByUsername(username);
            if (isExist == null) {
                user.setId(SnowFlakeUtil.get());
                user.setRoot((byte) 0);
                userService.save(user);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("root", user.getRoot());
                baseResponseBody.setData(jsonObject);
            } else {
                throw new UserException("用户已存在", 500);
            }
        } else {
            throw new UserException("无效的用户名或密码", 500);
        }
        return baseResponseBody;
    }

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
    
    @RequestMapping(value = "/applications", method = RequestMethod.GET)
    public BaseResponseBody listApplications() {
        List<Application> applicationMonth = applicationService.getApplicationMonth(null, null, null);
        List<ApplicationAndUser> applicationAndUserList = new ArrayList<>();
        applicationMonth.forEach(application -> {
            ApplicationAndUser applicationAndUser = new ApplicationAndUser(application);
            User user = userService.get(application.getUserId());
            applicationAndUser.setUsername(user.getUsername());
            applicationAndUserList.add(applicationAndUser);
        });
        BaseResponseBody responseBody = new BaseResponseBody();
        responseBody.setData(applicationAndUserList);
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
            ApplicationAndUser applicationAndUser = new ApplicationAndUser(application);
            User user = userService.get(application.getUserId());
            applicationAndUser.setUsername(user.getUsername());
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
    public BaseResponseBody updateApplication(@PathVariable String id,
                                              @PathVariable Integer result) {
        Application application = applicationService.updateResult(id, result);
        if (application == null) {
            throw new ApplicationException("更新申请失败", 500);
        }
        User user = userService.get(application.getUserId());
        ApplicationAndUser applicationAndUser = new ApplicationAndUser(application);
        applicationAndUser.setUsername(user.getUsername());
        BaseResponseBody responseBody = new BaseResponseBody();
        responseBody.setData(applicationAndUser);
        return responseBody;
    }
    
    @RequestMapping(value = "/attendances/{userId}", method = RequestMethod.GET)
    public BaseResponseBody listAttendances(@PathVariable String userId) {
        List<Attendance> userAttendance = attendanceService.getUserAttendance(userId, null, null);
        BaseResponseBody responseBody = new BaseResponseBody();
        responseBody.setData(userAttendance);
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
    public BaseResponseBody listAttendances(@PathVariable String userId,
                                            @PathVariable Integer year,
                                            @PathVariable Integer month) {
        List<Attendance> userAttendance = attendanceService.getUserAttendance(userId, year, month);
        BaseResponseBody responseBody = new BaseResponseBody();
        responseBody.setData(userAttendance);
        return responseBody;
    }

    @RequestMapping(value = "/count/{userId}", method = RequestMethod.GET)
    public BaseResponseBody attendanceCount(@PathVariable String userId) {
        AttendanceCount attendanceCount = attendanceService.attendanceCount(userId, null, null);
        BaseResponseBody responseBody = new BaseResponseBody();
        responseBody.setData(attendanceCount);
        return responseBody;
    }

    /**
     * 统计指定用户出勤记录
     *
     * @param userId
     * @param year
     * @param month
     * @return
     */
    @RequestMapping(value = "/count/{userId}/{year}/{month}", method = RequestMethod.GET)
    public BaseResponseBody attendanceCount(@PathVariable String userId,
                                            @PathVariable Integer year,
                                            @PathVariable Integer month) {
        AttendanceCount attendanceCount = attendanceService.attendanceCount(userId, year, month);
        BaseResponseBody responseBody = new BaseResponseBody();
        responseBody.setData(attendanceCount);
        return responseBody;
    }

}
