package com.iemylife.iot.weather.mapper;

import com.iemylife.iot.weather.domain.po.WeatherDataDailyInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper

public interface WeatherDataDailyInfoMapper {
    int insertBatch(List<WeatherDataDailyInfo> list);

    List<WeatherDataDailyInfo> selectByCode(String code);

    List<WeatherDataDailyInfo> selectByCity(String city);

    WeatherDataDailyInfo selectByCodeAndDate(String code, String date);

    WeatherDataDailyInfo selectByCityAndDate(String city, String date);

}