package com.iemylife.iot.weather.service;

import com.iemylife.iot.weather.domain.po.WeatherDataDailyInfo;

import java.util.List;


public interface IWeatherDataDailyInfoService {
    int insertBatch(List<WeatherDataDailyInfo> list);
    List<WeatherDataDailyInfo> selectByCode(String code);

    List<WeatherDataDailyInfo> selectByCity(String city);

    WeatherDataDailyInfo selectByCodeAndDate(String code, String date);

    WeatherDataDailyInfo selectByCityAndDate(String code, String date);



}