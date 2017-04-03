package com.iemylife.iot.weather.controller;

import com.iemylife.iot.weather.domain.exception.GetDataFromApiException;
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
            //1从db获取数据 2从api获取数据
            List<WeatherDataDailyInfo> weatherDataDailyInfo = new ArrayList<>();
            List<WeatherDataDailyInfo> weatherDataDailyInfo1 = weatherDataDailyInfoServices.selectByCode(code);
            if (weatherDataDailyInfo1 != null) {
                weatherDataDailyInfo = weatherDataDailyInfo1;
            }
            //从api获取
            String url = baseURL + "forecast" + code + key;
            String jsonString = restTemplate.getForObject(url, String.class);
            if (jsonString == null) {
                throw new GetDataFromApiException("从第三方api获取数据失败");
            }
            //将获取的数据留存到数据库
            weatherDataDailyInfoServices.insertBatch(weatherDataDailyInfo);
            List<Object> modelResponseList = new ArrayList<>();
            if (weatherDataDailyInfo == null) {
                return new ResponseEntity<String>(EMPTY_RESPONSEBODY_VALUE, HttpStatus.NOT_FOUND);
            }

            for (WeatherDataDailyInfo item : weatherDataDailyInfo) {
                Map<String, Object> modelMapResponse = getModelResponseMap(item);
                modelResponseList.add(modelMapResponse);
            }
            return new ResponseEntity<List>(modelResponseList, HttpStatus.OK);
        } catch (GetDataFromApiException e) {
            return new ResponseEntity<Object>(EMPTY_RESPONSEBODY_VALUE, HttpStatus.BAD_REQUEST);
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
