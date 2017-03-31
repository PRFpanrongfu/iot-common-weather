package com.iemylife.iot.weather.service;

import com.iemylife.iot.weather.domain.po.WeatherDataDailyInfo;

import java.util.List;


public interface IWeatherDataDailyInfoService {
    List<WeatherDataDailyInfo> selectByCode(String code);

    List<WeatherDataDailyInfo> selectByCity(String city);



}