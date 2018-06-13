package system.attendance.electronic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import system.attendance.electronic.common.SnowFlakeUtil;
import system.attendance.electronic.model.Application;
import system.attendance.electronic.model.BaseResponseBody;
import system.attendance.electronic.service.ApplicationService;

import java.util.List;

/**
 * @author Soloist
 * @version 1.0
 * @createtime 2018/6/10 2:24
 * @email ly@soloist.top
 * @description
 */
@CrossOrigin
@RestController
@RequestMapping("/applications")
public class ApplicationController extends BaseController {

    @Autowired
    private ApplicationService applicationService;

    /**
     * 提交申请
     *
     * @param application
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public BaseResponseBody insertApplication(@RequestBody Application application) {
        BaseResponseBody responseBody = new BaseResponseBody();
        application.setId(SnowFlakeUtil.get());
        application.setUserId(currentUserId);
        application.setResult((byte) 0);

        Application save = applicationService.save(application);
        save.setUserId(null);
        save.setId(null);
        responseBody.setData(save);
        return responseBody;
    }

    @RequestMapping(method = RequestMethod.GET)
    public BaseResponseBody getApplication() {
        BaseResponseBody responseBody = new BaseResponseBody();
        List<Application> applicationMonth = applicationService.getApplicationMonth(currentUserId, null, null);
        applicationMonth.forEach(application -> {
            application.setId(null);
            application.setUserId(null);
        });
        responseBody.setData(applicationMonth);
        return responseBody;
    }

    /**
     * 获取指定月份的申请
     *
     * @param year
     * @param month
     * @return
     */
    @RequestMapping(value = "/{year}/{month}", method = RequestMethod.GET)
    public BaseResponseBody getApplication(@PathVariable Integer year,
                                           @PathVariable Integer month) {
        BaseResponseBody responseBody = new BaseResponseBody();
        List<Application> applicationMonth = applicationService.getApplicationMonth(currentUserId, year, month);
        applicationMonth.forEach(application -> {
            application.setId(null);
            application.setUserId(null);
        });
        responseBody.setData(applicationMonth);
        return responseBody;
    }

}
