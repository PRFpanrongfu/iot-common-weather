package com.iemylife.iot.weather.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import static org.apache.coyote.http11.Constants.a;

/**
 * Created by prf on 2017/3/29.
 */
public class BaseController {
    public static final String EMPTY_RESPONSEBODY_VALUE = "{}";//重复使用的String
    public RestTemplate restTemplate = new RestTemplate();
    public String baseURL = "https://free-api.heweather.com/v5/";//接口地址
    public String key = "&key=41f448ef42e54a36a892838fa98d71da";//接口key
    public ObjectMapper objectMapper = new ObjectMapper();
}
