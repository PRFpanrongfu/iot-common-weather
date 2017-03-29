package com.iemylife.iot.weather.service;

import com.iemylife.iot.weather.domain.po.WeatherDataDailyInfo;


public interface IWeatherDataDailyInfoService {
    int deleteByPrimaryKey(Integer id);

    int insert(WeatherDataDailyInfo record);

    int insertSelective(WeatherDataDailyInfo record);

    WeatherDataDailyInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeatherDataDailyInfo record);

    int updateByPrimaryKey(WeatherDataDailyInfo record);
}