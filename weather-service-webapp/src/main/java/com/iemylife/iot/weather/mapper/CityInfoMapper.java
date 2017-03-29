package com.iemylife.iot.weather.mapper;

import com.iemylife.iot.weather.domain.po.CityInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CityInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CityInfo record);

    int insertSelective(CityInfo record);

    CityInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CityInfo record);

    int updateByPrimaryKey(CityInfo record);
}