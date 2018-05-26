package system.attendance.electronic.service;

import org.springframework.stereotype.Service;

/**
 * @author Soloist
 * @version 1.0
 * @createtime 2018/5/21 16:27
 * @email ly@soloist.top
 * @description
 */
@Service
public interface IService<T> {
    
    T get(Long id);
    
    T save(T model);
    
    T update(T model);
    
    int delete(Long id);
    
}
