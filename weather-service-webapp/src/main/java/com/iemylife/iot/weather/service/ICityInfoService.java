package com.iemylife.iot.weather.service;

import com.iemylife.iot.weather.domain.po.CityInfo;

public interface ICityInfoService {
    int deleteByPrimaryKey(Integer id);

    int insert(CityInfo record);

    int insertSelective(CityInfo record);

    CityInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CityInfo record);

    int updateByPrimaryKey(CityInfo record);
}