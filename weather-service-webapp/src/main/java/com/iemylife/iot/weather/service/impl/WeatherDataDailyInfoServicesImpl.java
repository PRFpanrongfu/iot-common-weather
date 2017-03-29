package com.iemylife.iot.weather.service.impl;

import com.iemylife.iot.weather.domain.po.WeatherDataDailyInfo;
import com.iemylife.iot.weather.service.IWeatherDataDailyInfoService;

/**
 * Created by prf on 2017/3/29.
 */
public class WeatherDataDailyInfoServicesImpl implements IWeatherDataDailyInfoService {
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(WeatherDataDailyInfo record) {
        return 0;
    }

    @Override
    public int insertSelective(WeatherDataDailyInfo record) {
        return 0;
    }

    @Override
    public WeatherDataDailyInfo selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(WeatherDataDailyInfo record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(WeatherDataDailyInfo record) {
        return 0;
    }
}
