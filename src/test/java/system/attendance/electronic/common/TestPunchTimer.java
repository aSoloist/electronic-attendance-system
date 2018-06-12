package system.attendance.electronic.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Soloist
 * @version 1.0
 * @createtime 2018/6/13 0:51
 * @email ly@soloist.top
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-context.xml")
public class TestPunchTimer extends AbstractTransactionalJUnit4SpringContextTests {
    
    @Autowired
    private PunchTimer punchTimer;
    
    @Test
    public void testAddPunchRecord() {
        punchTimer.addPunchRecord();
    }
    
}
