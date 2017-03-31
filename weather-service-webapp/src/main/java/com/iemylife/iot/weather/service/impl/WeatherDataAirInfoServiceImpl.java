package com.iemylife.iot.weather.service.impl;

import com.iemylife.iot.weather.domain.po.WeatherDataAirInfo;
import com.iemylife.iot.weather.mapper.WeatherDataAirInfoMapper;
import com.iemylife.iot.weather.service.IWeatherDataAirInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by prf on 2017/3/29.
 */
@Service
public class WeatherDataAirInfoServiceImpl implements IWeatherDataAirInfoService {
    @Autowired
    private WeatherDataAirInfoMapper weatherDataAirInfoMapper;

    @Override
    public WeatherDataAirInfo selectByCode(String code) {
        return weatherDataAirInfoMapper.selectByCode(code);
    }

    @Override
    public WeatherDataAirInfo selectByCity(String city) {
        return weatherDataAirInfoMapper.selectByCity(city);
    }
}
