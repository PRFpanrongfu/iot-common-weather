package com.iemylife.iot.weather.mapper;

import com.iemylife.iot.weather.domain.po.WeatherDataAirInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WeatherDataAirInfoMapper {
    WeatherDataAirInfo selectByCode(String code);

    WeatherDataAirInfo selectByCity(String city);
}