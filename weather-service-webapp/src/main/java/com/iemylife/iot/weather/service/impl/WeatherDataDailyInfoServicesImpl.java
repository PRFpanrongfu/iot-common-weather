package com.iemylife.iot.weather.service.impl;

import com.iemylife.iot.weather.domain.po.WeatherDataDailyInfo;
import com.iemylife.iot.weather.mapper.WeatherDataDailyInfoMapper;
import com.iemylife.iot.weather.service.IWeatherDataDailyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by prf on 2017/3/29.
 */
@Service
public class WeatherDataDailyInfoServicesImpl implements IWeatherDataDailyInfoService {
    @Autowired
    private WeatherDataDailyInfoMapper weatherDataDailyInfoMapper;

    @Override
    public int insertBatch(List<WeatherDataDailyInfo> list) {

        return weatherDataDailyInfoMapper.insertBatch(list);
    }

    @Override
    public List<WeatherDataDailyInfo> selectByCode(String code) {
        if (code.trim().length() == 0 || code.trim().length() > 100) {
            throw new IllegalArgumentException("参数错误");
        }
        return weatherDataDailyInfoMapper.selectByCode(code);

    }

    @Override
    public List<WeatherDataDailyInfo> selectByCity(String city) {
        if (city.trim().length() == 0 || city.trim().length() > 100) {
            throw new IllegalArgumentException("参数错误");
        }
        return weatherDataDailyInfoMapper.selectByCity(city);
    }

    @Override
    public WeatherDataDailyInfo selectByCodeAndDate(String code, String date) {
        if (code.trim().length() == 0 || code.trim().length() > 100) {
            throw new IllegalArgumentException("参数错误");
        }
        return weatherDataDailyInfoMapper.selectByCodeAndDate(code, date);

    }

    @Override
    public WeatherDataDailyInfo selectByCityAndDate(String city, String date) {
        if (city.trim().length() == 0 || city.trim().length() > 100) {
            throw new IllegalArgumentException("参数错误");
        }
        return weatherDataDailyInfoMapper.selectByCityAndDate(city, date);
    }
}
