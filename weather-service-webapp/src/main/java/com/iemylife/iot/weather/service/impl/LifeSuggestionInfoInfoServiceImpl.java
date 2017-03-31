package com.iemylife.iot.weather.service.impl;

import com.iemylife.iot.weather.domain.po.LifeSuggestionInfo;
import com.iemylife.iot.weather.mapper.LifeSuggestionInfoMapper;
import com.iemylife.iot.weather.service.ILifeSuggestionInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by prf on 2017/3/29.
 */
@Service
public class LifeSuggestionInfoInfoServiceImpl implements ILifeSuggestionInfoService {
    @Autowired
    private LifeSuggestionInfoMapper lifeSuggestionInfoMapper;

    @Override
    public LifeSuggestionInfo selectByCode(String code) {
        return lifeSuggestionInfoMapper.selectByCode(code);
    }

    @Override
    public LifeSuggestionInfo selectByCity(String city) {
        return lifeSuggestionInfoMapper.selectByCity(city);
    }
}
