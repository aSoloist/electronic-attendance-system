package system.attendance.electronic.common;

/**
 * @author Soloist
 * @version 1.0
 * @createtime 2018/5/24 1:27
 * @email ly@soloist.top
 * @description
 */
public class SnowFlakeUtil {

    private final static long DATA_CENTER_ID = 1;
    private final static long MACHINE_ID = 1;
    private final static SnowFlake SNOW_FLAKE = new SnowFlake(DATA_CENTER_ID, MACHINE_ID);

    /**
     * 获取ID
     *
     * @return ID
     */
    public static String get() {
        return SNOW_FLAKE.nextId() + "";
    }

}
