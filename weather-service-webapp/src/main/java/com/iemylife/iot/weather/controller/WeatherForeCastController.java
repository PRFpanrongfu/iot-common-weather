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
            JsonNode dailyForecastNode = firstNode.get("daily_forecast");
            JsonNode firstNodeDFN = dailyForecastNode.get(1);

            Date date = new Date();
            List<WeatherDataDailyInfo> weatherDataDailyInfo2 = new ArrayList<>();
            WeatherDataDailyInfo weatherDataDailyInfo3 = new WeatherDataDailyInfo();
            for (int i = 1, nodeLength = dailyForecastNode.size(); i < nodeLength; i++) {

                JsonNode condNode = dailyForecastNode.get(i).get("cond");
                String condDayCodeStr = condNode.get("code_d").asText();
                String condDayTxtStr = condNode.get("txt_d").asText();
                String condNightCodeStr = condNode.get("code_n").asText();
                String condNightTxtStr = condNode.get("txt_n").asText();

                JsonNode tmpNode = dailyForecastNode.get(i).get("tmp");
                String feelStr = tmpNode.get("min").asText();
                String humidityStr = dailyForecastNode.get(i).get("hum").asText();
                String pcpnStr = dailyForecastNode.get(i).get("pcpn").asText();
                String presStr = dailyForecastNode.get(i).get("pres").asText();
                String temperatureMaxStr = tmpNode.get("max").asText();
                String temperatureMinStr = tmpNode.get("min").asText();
                String visibilityStr = dailyForecastNode.get(i).get("vis").asText();

                JsonNode windNode = dailyForecastNode.get(i).get("wind");
                String windDegStr = windNode.get("deg").asText();
                String windDirStr = windNode.get("dir").asText();
                String windScStr = windNode.get("sc").asText();
                String windSpdStr = windNode.get("spd").asText();

                String reportDateStr = dailyForecastNode.get(i).get("date").asText().trim();//报告日期yyyy-MM-dd格式字符串
                String cityStr = basicNode.get("city").asText();
                String codeStr = basicNode.get("id").asText();


                String extendDataStr = "";
                String weatherProviderStr = weatherNode.asText();

                JsonNode timeNode = basicNode.get("update");
                Long createTime = ServiceUtils.getTenNumbersTimeStamp(date);
                boolean isActive = true;
                Long ts = date.getTime();

                //将获取的值赋给WeatherDataDailyInfo对象
                weatherDataDailyInfo3.setCity(cityStr);
                weatherDataDailyInfo3.setCode(codeStr);
                weatherDataDailyInfo3.setCondDayCode(condDayCodeStr);
                weatherDataDailyInfo3.setCondDayTxt(condDayTxtStr);
                weatherDataDailyInfo3.setCondNightCode(condNightCodeStr);
                weatherDataDailyInfo3.setCondNightTxt(condNightTxtStr);
                weatherDataDailyInfo3.setFeel(feelStr);
                weatherDataDailyInfo3.setHumidity(humidityStr);
                weatherDataDailyInfo3.setPcpn(pcpnStr);
                weatherDataDailyInfo3.setPres(presStr);
                weatherDataDailyInfo3.setTemperatureMax(temperatureMaxStr);
                weatherDataDailyInfo3.setTemperatureMin(temperatureMinStr);
                weatherDataDailyInfo3.setVisibility(visibilityStr);
                weatherDataDailyInfo3.setWindDeg(windDegStr);
                weatherDataDailyInfo3.setWindDir(windDirStr);
                weatherDataDailyInfo3.setWindSc(windScStr);
                weatherDataDailyInfo3.setWindSpd(windSpdStr);
                weatherDataDailyInfo3.setExtendData(extendDataStr);
                weatherDataDailyInfo3.setReportDate(reportDateStr);
                weatherDataDailyInfo3.setCreateTime(createTime);
                weatherDataDailyInfo3.setIsActive(isActive);
                weatherDataDailyInfo3.setTs(ts);

                weatherDataDailyInfo2.add(weatherDataDailyInfo3);
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
