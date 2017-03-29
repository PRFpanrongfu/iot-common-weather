package com.iemylife.iot.weather.mapper;

import com.iemylife.iot.weather.domain.po.WeatherDataAirInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WeatherDataAirInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WeatherDataAirInfo record);

    int insertSelective(WeatherDataAirInfo record);

    WeatherDataAirInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeatherDataAirInfo record);

    int updateByPrimaryKey(WeatherDataAirInfo record);
}