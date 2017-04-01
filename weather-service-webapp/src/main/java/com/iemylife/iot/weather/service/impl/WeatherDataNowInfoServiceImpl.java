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

    /**
     * 查询数据源有三:
     * 首先从redis查询,查询不到,则查询数据库,查询不到,最后调用api,返回
     * 调用api的数据各放置一份到redis和数据库
     *
     * @param code
     * @return
     */
    @Override
    public WeatherDataNowInfo selectByCode(String code, WeatherDataNowInfo weatherDataNowInfo) {
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
