package com.iemylife.iot.weather.service;

import com.iemylife.iot.weather.domain.po.WeatherDataNowInfo;


public interface IWeatherDataNowInfoService {
    WeatherDataNowInfo selectByCode(String code);

    WeatherDataNowInfo selectByCity(String city);
}