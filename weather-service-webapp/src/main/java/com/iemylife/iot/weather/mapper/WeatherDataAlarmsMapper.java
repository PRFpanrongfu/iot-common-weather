package com.iemylife.iot.weather.mapper;

import com.iemylife.iot.weather.domain.po.WeatherDataAlarms;
import org.apache.ibatis.annotations.Mapper;

@Mapper

public interface WeatherDataAlarmsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WeatherDataAlarms record);

    int insertSelective(WeatherDataAlarms record);

    WeatherDataAlarms selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeatherDataAlarms record);

    int updateByPrimaryKeyWithBLOBs(WeatherDataAlarms record);

    int updateByPrimaryKey(WeatherDataAlarms record);
}