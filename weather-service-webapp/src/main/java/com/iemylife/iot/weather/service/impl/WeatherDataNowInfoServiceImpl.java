package com.iemylife.iot.weather.service.impl;

import com.iemylife.iot.weather.domain.po.WeatherDataNowInfo;
import com.iemylife.iot.weather.mapper.WeatherDataNowInfoMapper;
import com.iemylife.iot.weather.service.IWeatherDataNowInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by prf on 2017/3/29.
 */
@Service
public class WeatherDataNowInfoServiceImpl implements IWeatherDataNowInfoService {

    @Autowired
    private WeatherDataNowInfoMapper weatherDataNowInfoMapper;

    @Override
    public WeatherDataNowInfo selectByCode(String code) {
        if (code.trim().length() == 0 || code.trim().length() > 100) {
            throw new IllegalArgumentException("参数错误");
        }
        return weatherDataNowInfoMapper.selectByCode(code);
    }

    @Override
    public WeatherDataNowInfo selectByCity(String city) {
        if (city.trim().length() == 0 || city.trim().length() > 100) {
            throw new IllegalArgumentException("参数错误");
        }
        return weatherDataNowInfoMapper.selectByCity(city);
    }
}
