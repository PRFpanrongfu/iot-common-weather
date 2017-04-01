package com.iemylife.iot.weather.mapper;

import com.iemylife.iot.weather.domain.po.CityInfo;
import com.iemylife.iot.weather.domain.vo.RemanentCityInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CityInfoMapper {
    int updateRemanentField(@Param("code") String code, @Param("remanentCityInfo") RemanentCityInfo remanentCityInfo);

    int truncateCityInfo();

    int insertBatch(List<CityInfo> cityInfoList);

    List<CityInfo> selectByCodeAndPage(@Param("code") String code, @Param("size") Integer size, @Param("page") Integer page);

    int updateByCodeSelective(@Param("code") String code, @Param("cityInfo") CityInfo cityInfo);

    int deleteByCode(String code);

    CityInfo selectByCode(String code);

    int insertSelective(CityInfo record);


}