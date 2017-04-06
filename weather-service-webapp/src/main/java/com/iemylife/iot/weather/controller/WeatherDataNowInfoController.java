package com.iemylife.iot.weather.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.iemylife.iot.weather.domain.exception.*;
import com.iemylife.iot.weather.domain.po.WeatherDataNowInfo;
import com.iemylife.iot.weather.domain.vo.WeatherDataNowInfoForJson;
import com.iemylife.iot.weather.service.impl.WeatherDataNowInfoServiceImpl;
import com.iemylife.iot.weather.util.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 实况天气
 * Created by prf on 2017/3/30.
 */
@RestController
public class WeatherDataNowInfoController extends BaseController {

    @Autowired
    private WeatherDataNowInfoServiceImpl weatherDataNowInfoService;

    public static Map getModelResponseMap(WeatherDataNowInfoForJson weatherDataNowInfo) {
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
            WeatherDataNowInfoForJson weatherDataNowInfoForJson = new WeatherDataNowInfoForJson();
            //从redis获取数据
            WeatherDataNowInfoForJson weatherDataNowInfoForJson1 = weatherDataNowInfoService.getWeatherDataNowFromRedisCacheByCode(code);
            if (weatherDataNowInfoForJson1 != null) {
                weatherDataNowInfoForJson = weatherDataNowInfoForJson1;
            }

            //从数据库获取数据
            WeatherDataNowInfo weatherDataNowInfo = weatherDataNowInfoService.selectByCode(code, weatherDataNowInfoForJson);
            if (weatherDataNowInfo != null) {
                weatherDataNowInfoForJson = weatherDataNowInfo.getWeatherDataNowInfoForJson();
            }

            //从api获取数据
            WeatherDataNowInfoForJson weatherDataNowInfoForJson2 = weatherDataNowInfoService.getWeathterDataNowFromThirdPartyAPI(code);
            if (weatherDataNowInfoForJson2 != null) {
                weatherDataNowInfoForJson = weatherDataNowInfoForJson2;
            }
            Map resultMap = new HashMap();
            resultMap = getModelResponseMap(weatherDataNowInfoForJson);
            return new ResponseEntity<Object>(resultMap, HttpStatus.OK);
            //1从redis获取数据 2从db获取数据 3从api获取数据
            //WeatherDataNowInfoForJson weatherDataNowInfoForJson = new WeatherDataNowInfoForJson();
            ////从redis缓存中取值
            //WeatherDataNowInfoForJson weatherDataNowInfoForJson1 = redisTemplate.opsForValue().get(code);
            //if (weatherDataNowInfoForJson1 != null) {
            //    weatherDataNowInfoForJson = weatherDataNowInfoForJson1;
            //}
            ////从数据库取
            //WeatherDataNowInfo weatherDataNowInfo = weatherDataNowInfoService.selectByCode(code, weatherDataNowInfoForJson);
            //if (weatherDataNowInfo != null) {
            //    weatherDataNowInfoForJson = weatherDataNowInfo.getWeatherDataNowInfoForJson();
            //}
            //String url = BASE_URL + "now" + code + KEY;
            ////从api取
            //String jsonString = restTemplate.getForObject(url, String.class);
            //JsonNode rootNode = objectMapper.readTree(jsonString);
            //
            //JsonNode weatherNode = rootNode.get("HeWeather5");
            //JsonNode firstNode = weatherNode.get(1);
            //JsonNode basicNode = firstNode.get("basic");
            //
            ////Map<String,User> result = mapper.readValue(src, new TypeReference<Map<String,User>>() { });
            ////获取city
            //String cityString = basicNode.get("city").asText();
            ////获取code
            //String codeString = basicNode.get("id").asText();
            //JsonNode nowNode = firstNode.get("now");
            //
            //JsonNode condNode = nowNode.get("cond");
            ////获取condCode
            //String condcode = condNode.get("code").asText();
            ////获取condTxt
            //String condTxt = condNode.get("txt").asText();
            ////获取feel
            //String feelString = firstNode.get("f1").asText();
            ////获取humidity
            //String humidity = firstNode.get("hum").asText();
            ////获取pcpn
            //String pcpnString = firstNode.get("pcpn").asText();
            ////获取pres
            //String presString = firstNode.get("pres").asText();
            ////....
            //String tempatureMax = firstNode.get("tmp").asText();
            //String tempatureMin = firstNode.get("tmp").asText();
            //String visibility = firstNode.get("vis").asText();
            //JsonNode windNode = firstNode.get("wind");
            //String windDeg = windNode.get("deg").asText();
            //String windDir = windNode.get("dir").asText();
            //String windSc = windNode.get("sc").asText();
            //String windSpd = windNode.get("sc").asText();
            //String extendData = "";
            //String weatherProvider = firstNode.asText();
            //JsonNode timeNode = firstNode.get("update");
            ////
            //String updateTimeString = timeNode.get("utc").asText();//更新时间utc时间戳,十位数时间戳
            //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            //Date updateTimeDate = dateFormat.parse(updateTimeString);
            //Long updateTime = ServiceUtils.getTenNumbersTimeStamp(updateTimeDate);
            ////
            //Date date = new Date();
            //Long createTime = ServiceUtils.getTenNumbersTimeStamp(date);//十位数时间戳
            //boolean isActive = true;
            //Long ts = ServiceUtils.getTenNumbersTimeStamp(date);
            //
            ////第三个数据源仍然无法获取数据则抛出异常,停止服务
            //if (jsonString == null) {
            //    throw new GetDataFromApiException("从第三方api获取数据失败");
            //}
            ////将对象存进数据库da
            //weatherDataNowInfoService.insert(weatherDataNowInfo, code, updateTime);
            ////将对象缓存进redis
            //redisTemplate.opsForValue().set(weatherDataNowInfoForJson.getCode(), weatherDataNowInfoForJson);

            //
            //if (weatherDataNowInfoForJson == null) {
            //    return new ResponseEntity<String>(EMPTY_RESPONSEBODY_VALUE, HttpStatus.NOT_FOUND);
            //}
            //Map resultMap = new HashMap();
            //resultMap = getModelResponseMap(weatherDataNowInfoForJson);
            //return new ResponseEntity<Map>(new HashMap(), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<Object>(EMPTY_RESPONSEBODY_VALUE, HttpStatus.INTERNAL_SERVER_ERROR);
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
            WeatherDataNowInfoForJson weatherDataNowInfoForJson = new WeatherDataNowInfoForJson();
            //根据code查询city,再根据city查询数据
            String code = weatherDataNowInfoService.selectByCity(city);
            //从redis获取数据
            WeatherDataNowInfoForJson weatherDataNowInfoForJson1 = weatherDataNowInfoService.getWeatherDataNowFromRedisCacheByCode(code);
            if (weatherDataNowInfoForJson1 != null) {
                weatherDataNowInfoForJson = weatherDataNowInfoForJson1;
            }
            //从数据库获取数据
            WeatherDataNowInfo weatherDataNowInfo = weatherDataNowInfoService.selectByCode(code, weatherDataNowInfoForJson);
            if (weatherDataNowInfo != null) {
                weatherDataNowInfoForJson = weatherDataNowInfo.getWeatherDataNowInfoForJson();
            }
            //从api获取数据
            WeatherDataNowInfoForJson weatherDataNowInfoForJson2 = weatherDataNowInfoService.getWeathterDataNowFromThirdPartyAPI(code);
            if (weatherDataNowInfoForJson2 != null) {
                weatherDataNowInfoForJson = weatherDataNowInfoForJson2;
            }

            Map resultMap = new HashMap();
            resultMap = getModelResponseMap(weatherDataNowInfoForJson);
            return new ResponseEntity<Map>(resultMap, HttpStatus.OK);
        } catch (GetDataFromRedisCacheException e) {
            return new ResponseEntity<Object>(EMPTY_RESPONSEBODY_VALUE, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (GetDataFromApiException e) {
            return new ResponseEntity<Object>(EMPTY_RESPONSEBODY_VALUE, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<Object>(EMPTY_RESPONSEBODY_VALUE, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
