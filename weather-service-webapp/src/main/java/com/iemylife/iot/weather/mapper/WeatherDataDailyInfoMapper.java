package com.iemylife.iot.weather.mapper;

import com.iemylife.iot.weather.domain.po.WeatherDataDailyInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper

public interface WeatherDataDailyInfoMapper {
    List<WeatherDataDailyInfo> selectByCode(String code);

    List<WeatherDataDailyInfo> selectByCity(String city);

}