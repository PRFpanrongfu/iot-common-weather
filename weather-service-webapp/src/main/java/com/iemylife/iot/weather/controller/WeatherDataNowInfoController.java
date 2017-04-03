package com.iemylife.iot.weather.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iemylife.iot.weather.domain.exception.GetDataFromApiException;
import com.iemylife.iot.weather.domain.exception.SuchDataExistsException;
import com.iemylife.iot.weather.domain.po.CityInfo;
import com.iemylife.iot.weather.domain.po.WeatherDataNowInfo;
import com.iemylife.iot.weather.domain.vo.WeatherDataNowInfoForJson;
import com.iemylife.iot.weather.service.impl.WeatherDataNowInfoServiceImpl;
import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 实况天气
 * Created by prf on 2017/3/30.
 */
@RestController
public class WeatherDataNowInfoController extends BaseController {

    @Autowired
    private RedisTemplate<String, WeatherDataNowInfoForJson> redisTemplate;
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
            //1从redis取 2从db取 3从api
            WeatherDataNowInfoForJson weatherDataNowInfoForJson = new WeatherDataNowInfoForJson();
            //从redis缓存中取值
            WeatherDataNowInfoForJson weatherDataNowInfoForJson1 = redisTemplate.opsForValue().get(code);
            if (weatherDataNowInfoForJson1 != null) {
                weatherDataNowInfoForJson = weatherDataNowInfoForJson1;
            }
            //从数据库取
            WeatherDataNowInfo weatherDataNowInfo = weatherDataNowInfoService.selectByCode(code, weatherDataNowInfoForJson);
            if (weatherDataNowInfo != null) {
                weatherDataNowInfoForJson = weatherDataNowInfo.getWeatherDataNowInfoForJson();
            }
            String url = baseURL + "now" + code + key;
            //从api取
            String jsonString = restTemplate.getForObject(url, String.class);
            JsonNode rootNode = objectMapper.readTree(jsonString);

            JsonNode weatherNode = rootNode.get("HeWeather5");
            JsonNode firstNode = weatherNode.get(1);
            JsonNode basicNode = firstNode.get("basic");

            //Map<String,User> result = mapper.readValue(src, new TypeReference<Map<String,User>>() { });
            //获取city
            String cityString = basicNode.get("city").asText();
            //获取code
            String codeString = basicNode.get("id").asText();
            JsonNode nowNode = firstNode.get("now");

            JsonNode condNode = nowNode.get("cond");
            //获取condCode
            String condcode = condNode.get("code").asText();
            //获取condTxt
            String condTxt = condNode.get("txt").asText();
            //获取feel
            String feelString = firstNode.get("f1").asText();
            //获取humidity
            String humidity = firstNode.get("hum").asText();
            //获取pcpn
            String pcpnString = firstNode.get("pcpn").asText();
            //获取pres
            String presString = firstNode.get("pres").asText();
            //....
            Long updateTime = 1L;
            //第三个数据源仍然无法获取数据则抛出异常,停止服务
            if (jsonString == null) {
                throw new GetDataFromApiException("从第三方api获取数据失败");
            }
            //将对象存进数据库
            weatherDataNowInfoService.insert(weatherDataNowInfo, code, updateTime);
            //将对象缓存进redis
            redisTemplate.opsForValue().set(weatherDataNowInfoForJson.getCode(), weatherDataNowInfoForJson);


            if (weatherDataNowInfoForJson == null) {
                return new ResponseEntity<String>(EMPTY_RESPONSEBODY_VALUE, HttpStatus.NOT_FOUND);
            }
            Map resultMap = new HashMap();
            resultMap = getModelResponseMap(weatherDataNowInfoForJson);
            return new ResponseEntity<Map>(resultMap, HttpStatus.OK);
        } catch (SuchDataExistsException e) {
            return new ResponseEntity<Object>(EMPTY_RESPONSEBODY_VALUE, HttpStatus.BAD_REQUEST);
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
            WeatherDataNowInfo weatherDataNowInfo = weatherDataNowInfoService.selectByCity(city);

            if (weatherDataNowInfo == null) {
                return new ResponseEntity<String>(EMPTY_RESPONSEBODY_VALUE, HttpStatus.NOT_FOUND);
            }
            Map resultMap = new HashMap();
            //resultMap = getModelResponseMap(weatherDataNowInfo);
            return new ResponseEntity<Map>(resultMap, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<Object>(EMPTY_RESPONSEBODY_VALUE, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
