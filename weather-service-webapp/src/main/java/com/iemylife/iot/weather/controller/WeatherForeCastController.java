package com.iemylife.iot.weather.controller;

import com.iemylife.iot.weather.domain.po.WeatherDataDailyInfo;
import com.iemylife.iot.weather.service.impl.WeatherDataDailyInfoServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by prf on 2017/3/30.
 */
@RestController
public class WeatherForeCastController extends BaseController {
    private static final String EMPTY_RESPONSEBODY_VALUE = "{}";
    @Autowired
    private WeatherDataDailyInfoServicesImpl weatherDataDailyInfoServices;

    public static Map getModelResponseMap(WeatherDataDailyInfo weatherDataDailyInfo) {
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("city", weatherDataDailyInfo.getCity());
        modelMap.put("code", weatherDataDailyInfo.getCode());
        modelMap.put("condDay", weatherDataDailyInfo.getCondDayTxt());
        modelMap.put("condNight", weatherDataDailyInfo.getCondNightTxt());
        modelMap.put("tmpDay", weatherDataDailyInfo.getTemperatureMax());
        modelMap.put("tmpNight", weatherDataDailyInfo.getTemperatureMin());
        modelMap.put("humidity", weatherDataDailyInfo.getHumidity());
        modelMap.put("windDirection", weatherDataDailyInfo.getWindDir());
        modelMap.put("windScale", weatherDataDailyInfo.getWindSc());
        modelMap.put("date", weatherDataDailyInfo.getReportDate());
        return modelMap;
    }

    @GetMapping(value = "/weathers/now", params = {"code"})
    public ResponseEntity<?> searchByCode(@RequestParam String code) {
        try {
            List<WeatherDataDailyInfo> weatherDataNowInfo = weatherDataDailyInfoServices.selectByCode(code);
            List<Object> modelResponseList = new ArrayList<>();
            if (weatherDataNowInfo == null) {
                return new ResponseEntity<String>(EMPTY_RESPONSEBODY_VALUE, HttpStatus.NOT_FOUND);
            }

            for (WeatherDataDailyInfo item : weatherDataNowInfo) {
                Map<String, Object> modelMapResponse = getModelResponseMap(item);
                modelResponseList.add(modelMapResponse);
            }
            return new ResponseEntity<List>(modelResponseList, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<Object>(EMPTY_RESPONSEBODY_VALUE, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/weathers/now", params = {"city"})
    public ResponseEntity<?> searchByCity(@RequestParam String city) {
        try {
            List<WeatherDataDailyInfo> weatherDataNowInfo = weatherDataDailyInfoServices.selectByCity(city);
            List<Object> modelResponseList = new ArrayList<>();
            if (weatherDataNowInfo == null) {
                return new ResponseEntity<String>(EMPTY_RESPONSEBODY_VALUE, HttpStatus.NOT_FOUND);
            }

            for (WeatherDataDailyInfo item : weatherDataNowInfo) {
                Map<String, Object> modelMapResponse = getModelResponseMap(item);
                modelResponseList.add(modelMapResponse);
            }
            return new ResponseEntity<List>(modelResponseList, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<Object>(EMPTY_RESPONSEBODY_VALUE, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
