package system.attendance.electronic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.attendance.electronic.common.DateFormatUtil;
import system.attendance.electronic.exception.ApplicationException;
import system.attendance.electronic.mapper.ApplicationMapper;
import system.attendance.electronic.model.Application;
import system.attendance.electronic.model.example.ApplicationExample;

import java.util.Date;
import java.util.List;

/**
 * @author Soloist
 * @version 1.0
 * @createtime 2018/6/8 14:06
 * @email ly@soloist.top
 * @description
 */
@Service
public class ApplicationService implements IService<Application> {

    @Autowired
    private ApplicationMapper applicationMapper;

    @Override
    public Application get(Long id) {
        return applicationMapper.selectByPrimaryKey(id);
    }

    /**
     * 审核结果
     *
     * @param id
     * @param result
     * @return
     */
    public Application updateResult(Long id, Integer result) {
        Application application = applicationMapper.selectByPrimaryKey(id);
        application.setResult(result.byteValue());
        return applicationMapper.updateByPrimaryKeySelective(application) == 1 ? application : null;
    }

    /**
     * 申请
     *
     * @param model
     * @return
     */
    @Override
    public Application save(Application model) {
        if (checkApplication(model)) {
            // 获取当前日期
            Date time = DateFormatUtil.getDate(new Date());

            assert time != null;
            if (model.getBeginDate().getTime() < time.getTime()) { // 开始日期在当前日期之前
                throw new ApplicationException("申请的开始日期在当前日期之前", 403);
            }
            if (model.getBeginDate().getTime() > model.getEndDate().getTime()) {
                throw new ApplicationException("申请的开始日期在结束日期之后", 403);
            }
            int i = applicationMapper.insertSelective(model);
            return i == 1 ? model : null;
        } else {
            throw new ApplicationException("申请的日期段已有其他申请", 403);
        }
    }

    /**
     * 检查申请
     *
     * @param application
     * @return
     */
    private Boolean checkApplication(Application application) {
        ApplicationExample applicationExample = new ApplicationExample();
        applicationExample.createCriteria().andUserIdEqualTo(application.getUserId()).
                andBeginDateLessThanOrEqualTo(application.getEndDate()).andEndDateGreaterThanOrEqualTo(application.getBeginDate());
        List<Application> applications = applicationMapper.selectByExample(applicationExample);
        return applications.size() == 0;
    }

    @Override
    public Application update(Application model) {
        int i = applicationMapper.updateByPrimaryKeySelective(model);
        return i == 1 ? model : null;
    }

    @Override
    public int delete(Long id) {
        return applicationMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取指定日期的申请
     *
     * @param date
     * @return
     */
    public List<Application> getApplicationByDate(Date date) {
        date = DateFormatUtil.getDate(date);
        ApplicationExample example = new ApplicationExample();
        example.createCriteria().andBeginDateLessThanOrEqualTo(date).andEndDateGreaterThanOrEqualTo(date);
        return applicationMapper.selectByExample(example);
    }

    /**
     * 获取指定月份的申请
     *
     * @param userId
     * @param year
     * @param month
     * @return
     */
    public List<Application> getApplicationMonth(Long userId, Integer year, Integer month) {
        Date[] dates = DateFormatUtil.getMonth(year, month);
        ApplicationExample applicationExample = new ApplicationExample();
        if (userId != null) {
            applicationExample.createCriteria().andUserIdEqualTo(userId).andEndDateGreaterThanOrEqualTo(dates[0]).
                    andBeginDateLessThan(dates[1]);
        } else {
            applicationExample.createCriteria().andEndDateGreaterThanOrEqualTo(dates[0]).
                    andBeginDateLessThan(dates[1]);
        }
        return applicationMapper.selectByExample(applicationExample);
    }
}
