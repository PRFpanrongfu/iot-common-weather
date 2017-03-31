package com.iemylife.iot.weather.service;

import com.iemylife.iot.weather.domain.po.CityInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ICityInfoService {
    int insertBatch(String city, String code);
    List<CityInfo> selectBymodelIdAndPage(@Param("code") String code, @Param("size") Integer size, @Param("page") Integer page);

    int updateByCodeSelective(String code, CityInfo cityInfo);

    int deleteByCode(String code);

    CityInfo selectByCode(String code);

    int insertSelective(CityInfo record);


}