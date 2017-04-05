package com.iemylife.iot.weather.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iemylife.iot.weather.config.HeWeatherConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import static org.apache.coyote.http11.Constants.a;

/**
 * Created by prf on 2017/3/29.
 */
public class BaseController {

    @Autowired
    private HeWeatherConfig weatherConfig;

    public static final String EMPTY_RESPONSEBODY_VALUE = "{}";//重复使用的String
    public static final String BASE_URL = "https://free-api.heweather.com/v5/";
    public static final String KEY = "&key=41f448ef42e54a36a892838fa98d71da";//接口key
    public RestTemplate restTemplate = new RestTemplate();
    public ObjectMapper objectMapper = new ObjectMapper();
}
