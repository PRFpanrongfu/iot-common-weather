package com.iemylife.iot.weather.service;

import com.iemylife.iot.weather.domain.po.WeatherDataNowInfo;


public interface IWeatherDataNowInfoService {
    WeatherDataNowInfo selectByCode(String code, WeatherDataNowInfo weatherDataNowInfo);

    WeatherDataNowInfo selectByCity(String city);
}