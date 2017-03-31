package com.iemylife.iot.weather.service;

import com.iemylife.iot.weather.domain.po.WeatherDataAirInfo;

public interface IWeatherDataAirInfoService {
    WeatherDataAirInfo selectByCode(String code);

    WeatherDataAirInfo selectByCity(String city);
}