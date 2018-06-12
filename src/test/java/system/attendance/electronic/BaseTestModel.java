package system.attendance.electronic;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Soloist
 * @version 1.0
 * @createtime 2018/6/13 1:33
 * @email ly@soloist.top
 * @description 基本测试模型
 *              AbstractTransactionalJUnit4SpringContextTests 具有事务回滚特性
 *              AbstractJUnit4SpringContextTests 不具有事务回滚
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-context.xml")
public abstract class BaseTestModel extends AbstractTransactionalJUnit4SpringContextTests {
}
