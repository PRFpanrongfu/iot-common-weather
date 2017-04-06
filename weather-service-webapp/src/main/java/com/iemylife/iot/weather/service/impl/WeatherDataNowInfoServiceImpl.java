package com.iemylife.iot.weather.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iemylife.iot.weather.config.HeWeatherConfig;
import com.iemylife.iot.weather.domain.exception.*;
import com.iemylife.iot.weather.domain.po.WeatherDataNowInfo;
import com.iemylife.iot.weather.domain.vo.WeatherDataNowInfoForJson;
import com.iemylife.iot.weather.mapper.CityInfoMapper;
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

    @Autowired
    private HeWeatherConfig weatherConfig;
    //@Autowired
    //private RestTemplate restTemplate;
    @Autowired
    private WeatherDataNowInfoMapper weatherDataNowInfoMapper;
    @Autowired
    private CityInfoMapper cityInfoMapper;
    @Autowired
    private RedisTemplate<String, WeatherDataNowInfoForJson> redisTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 查询数据源有三:
     * 首先从redis查询,查询不到,则查询数据库,查询不到,最后调用api,返回给调用方
     * 调用api的数据各放置一份到redis和数据库
     *
     * @param code 城市代码
     * @return
     */
    public WeatherDataNowInfoForJson getWeatherDataNowFromRedisCacheByCode(String code) throws GetDataFromRedisCacheException {
        WeatherDataNowInfoForJson weatherDataNowInfoForJson1 = redisTemplate.opsForValue().get(code);
        if (weatherDataNowInfoForJson1 == null) {
            throw new GetDataFromRedisCacheException("从redis和缓存获取数据失败");
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
        RestTemplate restTemplate = new RestTemplate();
        String url = weatherConfig.getApiUrl() + "wweather" + code + weatherConfig.getKey();
        //从api获取数据,通过JsonNode以树形解析json字符串
        String jsonString = restTemplate.getForObject(url, String.class);
        //将json串以树形状读入内存
        JsonNode rootNode = objectMapper.readTree(jsonString);
        //得到HeWeather5这个节点下的所有信息
        JsonNode weatherNode = rootNode.get("HeWeather5");
        //解析json数组,注意下标
        JsonNode firstNode = weatherNode.get(0);//!下标必须从零开始! !我的时间啊,花在这么低菊的错误上!
        JsonNode basicNode = firstNode.get("basic");

        //Map<String,User> result = mapper.readValue(src, new TypeReference<Map<String,User>>() { });

        String cityString = basicNode.get("city").asText();//获取city
        String codeString = basicNode.get("id").asText();
        //依次解析字符串取值
        JsonNode nowNode = firstNode.get("now");
        JsonNode condNode = nowNode.get("cond");
        String condcode = condNode.get("code").asText();
        String condTxt = condNode.get("txt").asText();
        String feelString = nowNode.get("fl").asText();
        String humidity = nowNode.get("hum").asText();
        String pcpnString = nowNode.get("pcpn").asText();
        String presString = nowNode.get("pres").asText();

        //最高气温和最低气温取天气预报节点第一天的数据
        JsonNode forecastNode = firstNode.get("daily_forecast");
        JsonNode fcfirstNode = forecastNode.get(0);
        JsonNode tmpNode = fcfirstNode.get("tmp");
        String tempatureMax = tmpNode.get("max").asText();
        String tempatureMin = tmpNode.get("min").asText();
        String visibility = firstNode.get("vis").asText();

        JsonNode windNode = nowNode.get("wind");
        String windDeg = windNode.get("deg").asText();
        String windDir = windNode.get("dir").asText();
        String windSc = windNode.get("sc").asText();
        String windSpd = windNode.get("spd").asText();
        String extendData = "";
        String weatherProvider = weatherConfig.getProvider();

        String reportDate = "";//报告时间yyyy-MM-dd HH:mm:ss格式字符串

        JsonNode updateNode = basicNode.get("update");
        String updateTimeString = updateNode.get("utc").asText();//更新时间utc时间戳,十位数时间戳
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date updateTimeDate = dateFormat.parse(updateTimeString);
        Long updateTime = ServiceUtils.getTenNumbersTimeStamp(updateTimeDate);
        //
        Long createTime = ServiceUtils.getUTCTimeStamp(10);//十位数时间戳
        boolean isActive = true;
        Long ts = ServiceUtils.getUTCTimeStamp(13);//最后修改UTC时间戳,13位
        WeatherDataNowInfoForJson weatherDataNowInfoForJson = new WeatherDataNowInfoForJson();

        if (weatherDataNowInfoForJson == null) {
            throw new GetDataFromApiException("从第三方api获取数据失败");
        }
        //将对象缓存进redis
        redisTemplate.opsForValue().set(weatherDataNowInfoForJson.getCode(), weatherDataNowInfoForJson);
        return weatherDataNowInfoForJson;
    }

    @Override
    public String selectByCity(String city) {
        if (city.trim().length() == 0 || city.trim().length() > 100) {
            throw new IllegalArgumentException("参数错误");
        }
        String code = cityInfoMapper.selectByCity(city).getCode();
        return code;
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
