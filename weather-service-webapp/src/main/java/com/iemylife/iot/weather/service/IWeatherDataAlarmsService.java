package com.iemylife.iot.weather.service;

import com.iemylife.iot.weather.domain.po.WeatherDataAlarms;


public interface IWeatherDataAlarmsService {
    int deleteByPrimaryKey(Integer id);

    int insert(WeatherDataAlarms record);

    int insertSelective(WeatherDataAlarms record);

    WeatherDataAlarms selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeatherDataAlarms record);

    int updateByPrimaryKeyWithBLOBs(WeatherDataAlarms record);

    int updateByPrimaryKey(WeatherDataAlarms record);
}