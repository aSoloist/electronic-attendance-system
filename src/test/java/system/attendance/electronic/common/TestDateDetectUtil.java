package system.attendance.electronic.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Soloist
 * @version 1.0
 * @createtime 2018/6/13 1:11
 * @email ly@soloist.top
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-context.xml")
public class TestDateDetectUtil extends AbstractJUnit4SpringContextTests {
    
    @Test
    public void testCheckTodayIsHoliday() {
        Integer integer = DateDetectUtil.checkTodayIsHoliday();
        System.out.println(integer);
    }
    
}
