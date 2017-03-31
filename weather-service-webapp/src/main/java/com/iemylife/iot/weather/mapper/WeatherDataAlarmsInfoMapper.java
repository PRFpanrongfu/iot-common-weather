package com.iemylife.iot.weather.mapper;

import com.iemylife.iot.weather.domain.po.WeatherDataAlarmsInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper

public interface WeatherDataAlarmsInfoMapper {

    WeatherDataAlarmsInfo selectByCode(String code);

    WeatherDataAlarmsInfo selectByCity(String city);
}