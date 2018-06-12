package system.attendance.electronic.common;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import system.attendance.electronic.BaseTestModel;

/**
 * @author Soloist
 * @version 1.0
 * @createtime 2018/6/13 0:51
 * @email ly@soloist.top
 * @description
 */
public class TestPunchTimer extends BaseTestModel {
    
    @Autowired
    private PunchTimer punchTimer;
    
    @Test
    public void testAddPunchRecord() {
        punchTimer.addPunchRecord();
    }
    
}
