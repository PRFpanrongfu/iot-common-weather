package com.iemylife.iot.weather.service;

import com.iemylife.iot.weather.domain.po.LifeSuggestionInfo;

public interface ILifeSuggestionInfoService {
    LifeSuggestionInfo selectByCode(String code);

    LifeSuggestionInfo selectByCity(String city);
}