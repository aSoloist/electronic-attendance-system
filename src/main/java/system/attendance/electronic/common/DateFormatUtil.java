package system.attendance.electronic.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Soloist
 * @version 1.0
 * @createtime 2018/6/10 3:58
 * @email ly@soloist.top
 * @description
 */
public class DateFormatUtil {

    private static Calendar calendar = Calendar.getInstance();

    /**
     * 获取日期
     *
     * @param date
     * @return
     */
    public static Date getDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String format = simpleDateFormat.format(date);
        try {
            return simpleDateFormat.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date[] getMonth(Integer year, Integer month) {
        calendar.clear();
        calendar.set(year, month, 1);
        Date end = calendar.getTime();
        calendar.add(Calendar.MONTH, -1);
        Date begin = calendar.getTime();
        return new Date[]{begin, end};
    }

}
