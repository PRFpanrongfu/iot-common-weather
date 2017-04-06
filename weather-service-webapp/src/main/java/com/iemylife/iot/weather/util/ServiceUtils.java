package com.iemylife.iot.weather.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by prf on 2017/3/31.
 */
public class ServiceUtils {

    static final String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";

    public static Date GetUTCdatetimeAsDate() {
        //note: doesn't check for null
        return StringDateToDate(GetUTCdatetimeAsString());
    }

    public static String GetUTCdatetimeAsString() {
        final SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        final String utcTime = sdf.format(new Date());
        return utcTime;
    }

    public static Date StringDateToDate(String StrDate) {
        Date dateToReturn = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATEFORMAT);

        try {
            dateToReturn = (Date) dateFormat.parse(StrDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateToReturn;
    }

    /**
     * 生成10位时间戳
     */
    public static Long getTenNumbersTimeStamp(Date date) {
        Long timeStamp = Long.parseLong(String.valueOf(date.getTime()).toString().substring(0, 10));
        return timeStamp;
    }

    /**
     * 获取当前时间的UTC时间
     * 入参为10则返回十位数时间戳;入参为
     */
    public static Long getUTCTimeStamp(int timeNumbers) {
        //1、取得本地时间：
        final java.util.Calendar cal = java.util.Calendar.getInstance();
        System.out.println(cal.getTime());
        //2、取得时间偏移量：
        final int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
        System.out.println(zoneOffset);
        //3、取得夏令时差：
        final int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
        System.out.println(dstOffset);
        //4、从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        System.err.println(cal.getTime());
        System.out.println(cal.getTimeInMillis());
        String time1 = String.valueOf(cal.getTimeInMillis());
        String time2 = time1.substring(0, 10);//十位utc时间戳
        Long UTCTimeTenNumbers = Long.valueOf(time2);
        Long UTCTimeThirteenNumbers = Long.valueOf(time1);
        if (timeNumbers == 10) {
            return UTCTimeTenNumbers;
        } else if (timeNumbers == 13) {
            return UTCTimeThirteenNumbers;
        }
        return 1L;
    }
}
