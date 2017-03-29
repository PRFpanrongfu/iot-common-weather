package com.iemylife.iot.weather.mapper;

import com.iemylife.iot.weather.domain.po.WeatherDataNowInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper

public interface WeatherDataNowInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WeatherDataNowInfo record);

    int insertSelective(WeatherDataNowInfo record);

    WeatherDataNowInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeatherDataNowInfo record);

    int updateByPrimaryKey(WeatherDataNowInfo record);
}