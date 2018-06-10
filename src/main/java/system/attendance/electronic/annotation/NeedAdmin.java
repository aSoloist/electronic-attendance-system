package system.attendance.electronic.annotation;

import java.lang.annotation.*;

/**
 * @author Soloist
 * @version 1.0
 * @createtime 2018/6/10 15:39
 * @email ly@soloist.top
 * @description
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NeedAdmin {
}
