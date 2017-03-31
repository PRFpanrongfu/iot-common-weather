package com.iemylife.iot.weather.service;

import com.iemylife.iot.weather.domain.po.LifeSuggestion;

public interface ILifeSuggestionService {
    LifeSuggestion selectByCode(String code);

    LifeSuggestion selectByCity(String city);
}