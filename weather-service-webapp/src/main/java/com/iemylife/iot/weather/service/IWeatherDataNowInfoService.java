package com.iemylife.iot.weather.service;

import com.iemylife.iot.weather.domain.po.WeatherDataNowInfo;


public interface IWeatherDataNowInfoService {
    int deleteByPrimaryKey(Integer id);

    int insert(WeatherDataNowInfo record);

    int insertSelective(WeatherDataNowInfo record);

    WeatherDataNowInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeatherDataNowInfo record);

    int updateByPrimaryKey(WeatherDataNowInfo record);
}