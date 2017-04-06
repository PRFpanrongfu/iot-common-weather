package com.iemylife.iot.weather.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
    @Value(value = "${weather.api-url}")
    private String apiUrl;

    @Value(value = "${weather.api-key}")
    private String key;

    @Value(value = "${weather.provider}")
    private String provider;

    @Value(value = "${weather.citylist-url}")
    private String cityInfoUrl;

    public String getApiUrl() {
        return apiUrl;
    }

    public String getKey() {
        return key;
    }

    public String getProvider() {
        return provider;
    }

    public String getCityInfoUrl() {
        return cityInfoUrl;
    }
}
