package com.iemylife.iot.weather.controller;

import com.iemylife.iot.weather.domain.po.WeatherDataDailyInfo;
import com.iemylife.iot.weather.service.impl.WeatherDataDailyInfoServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by prf on 2017/3/30.
 */
@RestController
public class HistoricWeatherController extends BaseController {

    @Autowired
    private WeatherDataDailyInfoServicesImpl weatherDataDailyInfoServices;

    public static Map getModelResponseMap(WeatherDataDailyInfo weatherDataDailyInfo) {
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("city", weatherDataDailyInfo.getCity());
        modelMap.put("code", weatherDataDailyInfo.getCode());
        modelMap.put("condDay", weatherDataDailyInfo.getCondDayTxt());
        modelMap.put("condNight", weatherDataDailyInfo.getCondNightTxt());
        modelMap.put("tempDay", weatherDataDailyInfo.getFeel());
        modelMap.put("tmpNight", weatherDataDailyInfo.getTemperatureMin());
        modelMap.put("humidity", weatherDataDailyInfo.getHumidity());
        modelMap.put("windDirection", weatherDataDailyInfo.getWindDir());
        modelMap.put("windScale", weatherDataDailyInfo.getWindSc());
        modelMap.put("date", weatherDataDailyInfo.getReportDate());
        return modelMap;
    }

    @GetMapping(value = "/weathers/historical", params = {"code", "date"})
    public ResponseEntity<?> searchByCode(@RequestParam String code, @RequestParam String date) {

        try {
            WeatherDataDailyInfo weatherDataDailyInfo = weatherDataDailyInfoServices.selectByCodeAndDate(code, date);

            if (weatherDataDailyInfo == null) {
                return new ResponseEntity<String>(EMPTY_RESPONSEBODY_VALUE, HttpStatus.NOT_FOUND);
            }
            Map resultMap = new HashMap();
            resultMap = getModelResponseMap(weatherDataDailyInfo);
            return new ResponseEntity<Map>(resultMap, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<Object>(EMPTY_RESPONSEBODY_VALUE, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/weathers/historical", params = {"city", "date"})
    public ResponseEntity<?> searchByCity(@RequestParam String city, @RequestParam String date) {
        try {
            WeatherDataDailyInfo weatherDataDailyInfo = weatherDataDailyInfoServices.selectByCityAndDate(city, date);

            if (weatherDataDailyInfo == null) {
                return new ResponseEntity<String>(EMPTY_RESPONSEBODY_VALUE, HttpStatus.NOT_FOUND);
            }
            Map resultMap = new HashMap();
            resultMap = getModelResponseMap(weatherDataDailyInfo);
            return new ResponseEntity<Map>(resultMap, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<Object>(EMPTY_RESPONSEBODY_VALUE, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
