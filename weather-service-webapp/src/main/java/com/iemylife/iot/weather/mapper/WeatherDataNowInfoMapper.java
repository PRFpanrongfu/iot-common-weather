package com.iemylife.iot.weather.mapper;

import com.iemylife.iot.weather.domain.po.WeatherDataNowInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper

public interface WeatherDataNowInfoMapper {
    WeatherDataNowInfo selectByCode(String code);

    WeatherDataNowInfo selectByCity(String city);


}