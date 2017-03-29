package com.iemylife.iot.weather.service;

import com.iemylife.iot.weather.domain.po.WeatherDataAirInfo;

public interface IWeatherDataAirInfoService {
    int deleteByPrimaryKey(Integer id);

    int insert(WeatherDataAirInfo record);

    int insertSelective(WeatherDataAirInfo record);

    WeatherDataAirInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeatherDataAirInfo record);

    int updateByPrimaryKey(WeatherDataAirInfo record);
}