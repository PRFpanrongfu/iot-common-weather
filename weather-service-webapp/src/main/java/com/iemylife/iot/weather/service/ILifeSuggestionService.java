package com.iemylife.iot.weather.service;

import com.iemylife.iot.weather.domain.po.LifeSuggestion;

public interface ILifeSuggestionService {
    int deleteByPrimaryKey(Integer id);

    int insert(LifeSuggestion record);

    int insertSelective(LifeSuggestion record);

    LifeSuggestion selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LifeSuggestion record);

    int updateByPrimaryKey(LifeSuggestion record);
}