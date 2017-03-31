package com.iemylife.iot.weather.mapper;

import com.iemylife.iot.weather.domain.po.LifeSuggestionInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LifeSuggestionInfoMapper {
    LifeSuggestionInfo selectByCode(String code);

    LifeSuggestionInfo selectByCity(String city);
}