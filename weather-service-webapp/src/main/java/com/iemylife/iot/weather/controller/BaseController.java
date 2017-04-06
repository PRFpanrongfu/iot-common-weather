package com.iemylife.iot.weather.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

/**
 * Created by prf on 2017/3/29.
 */
public class BaseController {

    public static final String EMPTY_RESPONSEBODY_VALUE = "{}";//重复使用的String

    //从配置文件(application.properties)读取相关配置信息
    @Value(value = ("${weather.api-url}"))
    public String BASE_URL;////接口地址
    @Value(value = ("${weather.api-key}"))
    public String KEY;//接口key
    @Value(value = "${weahter.provider}")
    public String weatherProvider;//天气数据供应商

    public RestTemplate restTemplate = new RestTemplate();
    public ObjectMapper objectMapper = new ObjectMapper();

}
