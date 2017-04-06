package com.iemylife.iot.weather.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iemylife.iot.weather.config.HeWeatherConfig;
import com.iemylife.iot.weather.domain.exception.GetDataFromApiException;
import com.iemylife.iot.weather.domain.po.WeatherDataDailyInfo;
import com.iemylife.iot.weather.mapper.WeatherDataDailyInfoMapper;
import com.iemylife.iot.weather.service.IWeatherDataDailyInfoService;
import com.iemylife.iot.weather.util.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

import static com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolver.length;

/**
 * Created by prf on 2017/3/29.
 */
@Service
public class WeatherDataDailyInfoServicesImpl implements IWeatherDataDailyInfoService {
    //从配置文件(application.properties)读取相关配置信息

    @Autowired
    private HeWeatherConfig weatherConfig;
    private ObjectMapper objectMapper = new ObjectMapper();
    //@Autowired
    //private RestTemplate restTemplate;
    @Autowired
    private WeatherDataDailyInfoMapper weatherDataDailyInfoMapper;

    public List<WeatherDataDailyInfo> getWeatherForecastFromAPIByCode(String code) throws GetDataFromApiException, IOException {
        RestTemplate restTemplate = new RestTemplate();
        String url = weatherConfig.getApiUrl() + "forecast" + code + weatherConfig.getKey();
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
        //将数据放置一份到数据库
        weatherDataDailyInfoMapper.insertBatch(weatherDataDailyInfo2);
        return weatherDataDailyInfo2;

    }

    public List<WeatherDataDailyInfo> getWeatherForecastFromAPIByCity(String city) throws GetDataFromApiException, IOException {
        RestTemplate restTemplate = new RestTemplate();
        String url = weatherConfig.getApiUrl() + "forecast" + city + weatherConfig.getKey();
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
        //将数据放置一份到数据库
        weatherDataDailyInfoMapper.insertBatch(weatherDataDailyInfo2);
        return weatherDataDailyInfo2;

    }

    @Override
    public int insertBatch(List<WeatherDataDailyInfo> list) {

        return weatherDataDailyInfoMapper.insertBatch(list);
    }

    @Override
    public List<WeatherDataDailyInfo> selectByCode(String code) {
        if (code.trim().length() == 0 || code.trim().length() > 100) {
            throw new IllegalArgumentException("参数错误");
        }
        return weatherDataDailyInfoMapper.selectByCode(code);

    }

    @Override
    public List<WeatherDataDailyInfo> selectByCity(String city) {
        if (city.trim().length() == 0 || city.trim().length() > 100) {
            throw new IllegalArgumentException("参数错误");
        }
        return weatherDataDailyInfoMapper.selectByCity(city);
    }

    @Override
    public WeatherDataDailyInfo selectByCodeAndDate(String code, String date) {
        if (code.trim().length() == 0 || code.trim().length() > 100) {
            throw new IllegalArgumentException("参数错误");
        }
        if (date.length() > 10) {
            throw new IllegalArgumentException("参数错误");
        }
        return weatherDataDailyInfoMapper.selectByCodeAndDate(code, date);

    }

    @Override
    public WeatherDataDailyInfo selectByCityAndDate(String city, String date) {
        if (city.trim().length() == 0 || city.trim().length() > 100) {
            throw new IllegalArgumentException("参数错误");
        }
        if (date.trim().length() == 0 || date.trim().length() > 10) {
            throw new IllegalArgumentException("参数错误");
        }
        return weatherDataDailyInfoMapper.selectByCityAndDate(city, date);
    }
}
