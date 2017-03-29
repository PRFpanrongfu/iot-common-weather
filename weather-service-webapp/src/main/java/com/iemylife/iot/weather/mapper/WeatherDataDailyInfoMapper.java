package com.iemylife.iot.weather.mapper;

import com.iemylife.iot.weather.domain.po.WeatherDataDailyInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper

public interface WeatherDataDailyInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WeatherDataDailyInfo record);

    int insertSelective(WeatherDataDailyInfo record);

    WeatherDataDailyInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeatherDataDailyInfo record);

    int updateByPrimaryKey(WeatherDataDailyInfo record);
}