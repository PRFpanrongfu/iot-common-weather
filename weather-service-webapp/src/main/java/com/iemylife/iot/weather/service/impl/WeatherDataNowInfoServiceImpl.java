package com.iemylife.iot.weather.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iemylife.iot.weather.domain.exception.*;
import com.iemylife.iot.weather.domain.po.WeatherDataNowInfo;
import com.iemylife.iot.weather.domain.vo.WeatherDataNowInfoForJson;
import com.iemylife.iot.weather.mapper.WeatherDataNowInfoMapper;
import com.iemylife.iot.weather.service.IWeatherDataNowInfoService;
import com.iemylife.iot.weather.util.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by prf on 2017/3/29.
 */
@Service
public class WeatherDataNowInfoServiceImpl implements IWeatherDataNowInfoService {
    //从配置文件(application.properties)读取相关配置信息
    @Value(value = ("${weather.api-url}"))
    public String BASE_URL;////接口地址
    @Value(value = ("${weather.api-key}"))
    public String KEY;//接口key
    @Value(value = "${weahter.provider}")
    public String weatherProvider;//天气数据供应商

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private WeatherDataNowInfoMapper weatherDataNowInfoMapper;
    @Autowired
    private RedisTemplate<String, WeatherDataNowInfoForJson> redisTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 查询数据源有三:
     * 首先从redis查询,查询不到,则查询数据库,查询不到,最后调用api,返回给调用方
     * 调用api的数据各放置一份到redis和数据库
     *
     * @param code
     * @return
     */
    public WeatherDataNowInfoForJson getWeatherDataNowFromRedisCache(String code) throws GetDataFromRedisCacheException {
        WeatherDataNowInfoForJson weatherDataNowInfoForJson1 = redisTemplate.opsForValue().get(code);
        if (weatherDataNowInfoForJson1 == null) {
            throw new GetDataFromRedisCacheException("从第三方api获取数据失败");
        }
        return weatherDataNowInfoForJson1;
    }

    @Override
    public WeatherDataNowInfo selectByCode(String code, WeatherDataNowInfoForJson weatherDataNowInfo) {
        if (code.trim().length() == 0 || code.trim().length() > 100) {
            throw new IllegalArgumentException("参数错误");
        }
        return weatherDataNowInfoMapper.selectByCode(code);
    }


    public WeatherDataNowInfoForJson getWeathterDataNowFromThirdPartyAPI(String code) throws GetDataFromApiException, IOException, ParseException {
        String url = BASE_URL + "wweather" + code + KEY;
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
        String tempatureMax = firstNode.get("tmp").asText();
        String tempatureMin = firstNode.get("tmp").asText();
        String visibility = firstNode.get("vis").asText();
        JsonNode windNode = firstNode.get("wind");
        String windDeg = windNode.get("deg").asText();
        String windDir = windNode.get("dir").asText();
        String windSc = windNode.get("sc").asText();
        String windSpd = windNode.get("sc").asText();
        String extendData = "";
        String weatherProvider = firstNode.asText();
        JsonNode timeNode = firstNode.get("update");
        //
        String updateTimeString = timeNode.get("utc").asText();//更新时间utc时间戳,十位数时间戳
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date updateTimeDate = dateFormat.parse(updateTimeString);
        Long updateTime = ServiceUtils.getTenNumbersTimeStamp(updateTimeDate);
        //
        Date date = new Date();
        Long createTime = ServiceUtils.getTenNumbersTimeStamp(date);//十位数时间戳
        boolean isActive = true;
        Long ts = ServiceUtils.getTenNumbersTimeStamp(date);
        WeatherDataNowInfoForJson weatherDataNowInfoForJson = new WeatherDataNowInfoForJson();
        if (weatherDataNowInfoForJson == null) {
            throw new GetDataFromApiException("从第三方api获取数据失败");
        }
        //将对象缓存进redis
        redisTemplate.opsForValue().set(weatherDataNowInfoForJson.getCode(), weatherDataNowInfoForJson);
        return weatherDataNowInfoForJson;
    }

    @Override
    public WeatherDataNowInfo selectByCity(String city) {
        if (city.trim().length() == 0 || city.trim().length() > 100) {
            throw new IllegalArgumentException("参数错误");
        }
        return weatherDataNowInfoMapper.selectByCity(city);
    }


    /**
     * 这个insert的被操作表只存储最新一条记录
     *
     * @param
     * @return
     */
    @Override
    public int insert(WeatherDataNowInfo weatherDataNowInfo, String code, Long nowTimeStamp) throws SuchDataExistsException {
        Long oldTimeStamp = weatherDataNowInfoMapper.selectByCode(code).getUpdateTime();
        Long result = nowTimeStamp - oldTimeStamp;
        if (result < 0) {
            throw new SuchDataExistsException("最新实况天气已存进数据库");
        }
        return weatherDataNowInfoMapper.insert(weatherDataNowInfo);
    }
}
