package system.attendance.electronic.common;

import org.junit.Test;
import system.attendance.electronic.BaseTestModel;

/**
 * @author Soloist
 * @version 1.0
 * @createtime 2018/6/13 1:11
 * @email ly@soloist.top
 * @description
 */
public class TestDateDetectUtil extends BaseTestModel {
    
    @Test
    public void testCheckTodayIsHoliday() {
        Integer integer = DateDetectUtil.checkTodayIsHoliday();
        System.out.println(integer);
    }
    
}
