package com.iemylife.iot.weather.mapper;

import com.iemylife.iot.weather.domain.po.LifeSuggestion;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LifeSuggestionMapper {
    LifeSuggestion selectByCode(String code);

    LifeSuggestion selectByCity(String city);
}