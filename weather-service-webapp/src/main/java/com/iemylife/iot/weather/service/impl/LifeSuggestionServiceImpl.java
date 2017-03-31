package com.iemylife.iot.weather.service.impl;

import com.iemylife.iot.weather.domain.po.LifeSuggestion;
import com.iemylife.iot.weather.mapper.LifeSuggestionMapper;
import com.iemylife.iot.weather.service.ILifeSuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by prf on 2017/3/29.
 */
@Service
public class LifeSuggestionServiceImpl implements ILifeSuggestionService {
    @Autowired
    private LifeSuggestionMapper lifeSuggestionMapper;

    @Override
    public LifeSuggestion selectByCode(String code) {
        return lifeSuggestionMapper.selectByCode(code);
    }

    @Override
    public LifeSuggestion selectByCity(String city) {
        return lifeSuggestionMapper.selectByCity(city);
    }
}
