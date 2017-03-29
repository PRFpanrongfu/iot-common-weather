package com.iemylife.iot.weather.mapper;

import com.iemylife.iot.weather.domain.po.LifeSuggestion;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LifeSuggestionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LifeSuggestion record);

    int insertSelective(LifeSuggestion record);

    LifeSuggestion selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LifeSuggestion record);

    int updateByPrimaryKey(LifeSuggestion record);
}