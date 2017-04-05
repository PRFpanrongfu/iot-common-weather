package com.iemylife.iot.weather.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 描述信息：<br/>
 * 和风天气配置文件
 *
 * @author flsh
 * @version 1.0
 * @date 2017.04.05.
 * @since Jdk 1.8
 */
@Configuration
public class HeWeatherConfig {

    @Value(value = "${heweather.api-url}")
    private String apiUrl;

    public String getApiUrl() {
        return apiUrl;
    }
}
