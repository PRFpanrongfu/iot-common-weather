package com.iemylife.iot.weather.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.iemylife.iot.weather.domain.exception.GetDataFromApiException;
import com.iemylife.iot.weather.domain.po.WeatherDataDailyInfo;
import com.iemylife.iot.weather.service.impl.WeatherDataDailyInfoServicesImpl;
import com.iemylife.iot.weather.util.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static javafx.scene.input.KeyCode.J;

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
            String url = BASE_URL + "forecast" + code + KEY;
            String jsonString = restTemplate.getForObject(url, String.class);
            if (jsonString == null) {
                throw new GetDataFromApiException("从第三方api获取数据失败");
            }
            JsonNode rootNode = objectMapper.readTree(jsonString);
            JsonNode weatherNode = rootNode.get(1);
            JsonNode firstNode = weatherNode.get(1);
            JsonNode basicNode = firstNode.get("basic");

            String city = basicNode.get("city").asText();
            String codeStr = basicNode.get("id").asText();

            JsonNode dailyForecastNode = firstNode.get("daily_forecast");
            JsonNode firstNodeDFN = dailyForecastNode.get(1);
            JsonNode condNode = firstNodeDFN.get("cond");
            String condDayCode = condNode.get("code_d").asText();
            String condDayTxt = condNode.get("txt_d").asText();
            String condNightCode = condNode.get("code_n").asText();
            String condNightTxt = condNode.get("txt_n").asText();

            JsonNode tmpNode = firstNodeDFN.get("tmp");
            String feel = tmpNode.get("min").asText();
            String humidity = firstNodeDFN.get("hum").asText();
            String pcpn = firstNodeDFN.get("pcpn").asText();
            String pres = firstNodeDFN.get("pres").asText();
            String temperatureMax = tmpNode.get("max").asText();
            String temperatureMin = tmpNode.get("min").asText();
            String visibility = firstNodeDFN.get("vis").asText();

            JsonNode windNode = firstNodeDFN.get("wind");
            String windDeg = windNode.get("deg").asText();
            String windDir = windNode.get("dir").asText();
            String windSc = windNode.get("sc").asText();
            String windSpd = windNode.get("spd").asText();
            String extendData = "";
            String weatherProvider = weatherNode.asText();

            JsonNode timeNode = basicNode.get("update");
            String reportDate = timeNode.get("loc").asText().trim().substring(0, 9);//报告日期yyyy-MM-dd格式字符串
            Date date = new Date();
            Long createTime = ServiceUtils.getTenNumbersTimeStamp(date);
            boolean isActive = true;
            Long ts = date.getTime();
            WeatherDataDailyInfo weatherDataDailyInfo2 = new WeatherDataDailyInfo();
            //weatherDataDailyInfo2.set(...)


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
