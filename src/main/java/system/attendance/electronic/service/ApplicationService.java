package system.attendance.electronic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.attendance.electronic.mapper.ApplicationMapper;
import system.attendance.electronic.model.Application;

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

    @Override
    public Application save(Application model) {
        int i = applicationMapper.insertSelective(model);
        return i == 1 ? model : null;
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
}
