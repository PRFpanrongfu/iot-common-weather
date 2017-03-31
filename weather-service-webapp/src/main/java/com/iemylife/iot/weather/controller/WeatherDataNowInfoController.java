package com.iemylife.iot.weather.controller;

import com.iemylife.iot.weather.domain.po.CityInfo;
import com.iemylife.iot.weather.domain.po.WeatherDataNowInfo;
import com.iemylife.iot.weather.service.impl.WeatherDataNowInfoServiceImpl;
import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.AsyncRestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 实况天气
 * Created by prf on 2017/3/30.
 */
@RestController
public class WeatherDataNowInfoController extends BaseController {
    private static final String EMPTY_RESPONSEBODY_VALUE = "{}";
    @Autowired
    private WeatherDataNowInfoServiceImpl weatherDataNowInfoService;

    public static Map getModelResponseMap(WeatherDataNowInfo weatherDataNowInfo) {
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("city", weatherDataNowInfo.getCity());
        modelMap.put("code", weatherDataNowInfo.getCode());
        modelMap.put("updateTime", weatherDataNowInfo.getUpdateTime());
        modelMap.put("cond", weatherDataNowInfo.getCondTxt());
        modelMap.put("temperature", weatherDataNowInfo.getFeel());
        modelMap.put("tmpDay", weatherDataNowInfo.getTemperatureMax());
        modelMap.put("tmpNight", weatherDataNowInfo.getTemperatureMin());
        modelMap.put("humidity", weatherDataNowInfo.getHumidity());
        modelMap.put("windDirection", weatherDataNowInfo.getWindDir());
        modelMap.put("windScale", weatherDataNowInfo.getWinDsc());
        return modelMap;
    }

    @GetMapping(value = "/weathers/now", params = {"code"})
    public ResponseEntity<?> searchByCode(@RequestParam String code) {
        try {
            WeatherDataNowInfo weatherDataNowInfo = weatherDataNowInfoService.selectByCode(code);

            if (weatherDataNowInfo == null) {
                return new ResponseEntity<String>(EMPTY_RESPONSEBODY_VALUE, HttpStatus.NOT_FOUND);
            }
            Map resultMap = new HashMap();
            resultMap = getModelResponseMap(weatherDataNowInfo);
            return new ResponseEntity<Map>(resultMap, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<Object>(EMPTY_RESPONSEBODY_VALUE, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/weathers/now", params = {"city"})
    public ResponseEntity<?> searchByCity(@RequestParam String city) {
        try {
            WeatherDataNowInfo weatherDataNowInfo = weatherDataNowInfoService.selectByCity(city);

            if (weatherDataNowInfo == null) {
                return new ResponseEntity<String>(EMPTY_RESPONSEBODY_VALUE, HttpStatus.NOT_FOUND);
            }
            Map resultMap = new HashMap();
            resultMap = getModelResponseMap(weatherDataNowInfo);
            return new ResponseEntity<Map>(resultMap, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<Object>(EMPTY_RESPONSEBODY_VALUE, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
