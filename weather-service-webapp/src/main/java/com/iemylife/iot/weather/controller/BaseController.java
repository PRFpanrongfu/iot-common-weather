package com.iemylife.iot.weather.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

/**
 * Created by prf on 2017/3/29.
 */
public class BaseController {

    public static final String EMPTY_RESPONSEBODY_VALUE = "{}";//重复使用的String


    public RestTemplate restTemplate = new RestTemplate();
    public ObjectMapper objectMapper = new ObjectMapper();

}
