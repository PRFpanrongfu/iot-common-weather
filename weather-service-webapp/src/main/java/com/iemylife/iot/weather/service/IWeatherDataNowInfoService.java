package com.iemylife.iot.weather.service;

import com.iemylife.iot.weather.domain.exception.SuchDataExistsException;
import com.iemylife.iot.weather.domain.po.WeatherDataNowInfo;
import com.iemylife.iot.weather.domain.vo.WeatherDataNowInfoForJson;


public interface IWeatherDataNowInfoService {
    int insert(WeatherDataNowInfo weatherDataNowInfo, String code, Long nowTimeStamp) throws SuchDataExistsException;

    WeatherDataNowInfo selectByCode(String code, WeatherDataNowInfoForJson weatherDataNowInfo);

    String selectByCity(String city);
}