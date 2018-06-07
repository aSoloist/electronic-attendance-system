package system.attendance.electronic.common;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Soloist
 * @version 1.0
 * @createtime 2018/6/7 19:04
 * @email ly@soloist.top
 * @description 日期检查工具
 */
public class DateDetectUtil {

    private static final String REQUEST_URL = "http://lanfly.vicp.io/api/holiday/info/";

    /**
     * 检查今天是否是节假日
     *
     * @return 0：错误 1：非节假日 2：节假日 3：调休
     */
    public static Integer checkTodayIsHoliday() {
        JSONObject json;
        BufferedReader reader;
        HttpURLConnection urlConnection = null;
        StringBuilder str = new StringBuilder();
        try {
            urlConnection = (HttpURLConnection) new URL(REQUEST_URL).openConnection();
            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                str.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        json = JSONObject.parseObject(str.toString());
        
        if (json.get("code").equals(1)) { // 服务错误
            return 0;
        }
        
        if (json.get("code").equals(0)) { // 服务正常
            JSONObject holiday = json.getJSONObject("holiday");
            
            if (holiday == null) { // 非节假日
                return 1;
            }
            
            if (holiday.get("holiday").equals(false)) { //是否调休
                return 3;
            } else {
                return 2;
            }
        }

        return 0;
    }

    /*public static void main(String[] args) {
        Integer integer = checkTodayIsHoliday();
        System.out.println(integer);
    }*/

}
