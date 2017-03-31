package com.iemylife.iot.weather.service;

import com.iemylife.iot.weather.domain.po.WeatherDataAlarmsInfo;


public interface IWeatherDataAlarmsInfoService {
    int deleteByPrimaryKey(Integer id);

    int insert(WeatherDataAlarmsInfo record);

    int insertSelective(WeatherDataAlarmsInfo record);

    WeatherDataAlarmsInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeatherDataAlarmsInfo record);

    int updateByPrimaryKeyWithBLOBs(WeatherDataAlarmsInfo record);

    int updateByPrimaryKey(WeatherDataAlarmsInfo record);
}