package com.iemylife.iot.weather.util;

import java.util.Date;

/**
 * Created by prf on 2017/3/31.
 */
public class ServiceUtils {

    /**
     * 生成10位时间戳
     */
    public static Long getTenNumbersTimeStamp(Date date) {
        Long timeStamp = Long.parseLong(String.valueOf(date.getTime()).toString().substring(0, 10));
        return timeStamp;
    }
}
