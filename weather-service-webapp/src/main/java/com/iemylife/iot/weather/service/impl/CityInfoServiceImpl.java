package com.iemylife.iot.weather.service.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iemylife.iot.weather.config.HeWeatherConfig;
import com.iemylife.iot.weather.domain.exception.TruncateTableException;
import com.iemylife.iot.weather.domain.po.CityInfo;
import com.iemylife.iot.weather.domain.vo.CityInfoForJson;
import com.iemylife.iot.weather.mapper.CityInfoMapper;
import com.iemylife.iot.weather.service.ICityInfoService;
import com.iemylife.iot.weather.util.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

/**
 * Created by prf on 2017/3/29.
 */
@Service
public class CityInfoServiceImpl implements ICityInfoService {
    @Autowired
    private HeWeatherConfig weatherConfig;//天气相关配置项

    @Autowired
    private CityInfoMapper cityInfoMapper;

    private RestTemplate restTemplate = new RestTemplate();//用于调用第三方api,有异步版本

    private ObjectMapper objectMapper = new ObjectMapper();//用于解析json字符串

    public void refreshCityInfos() throws IOException, TruncateTableException {
        //调用api,获取城市信息列表
        String returnValue = restTemplate.getForObject(weatherConfig.getCityInfoUrl(), String.class);

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);//排除值为null的值 //只对实体起作用，对map不起作用
        List<CityInfoForJson> listCity = objectMapper.readValue(returnValue, new TypeReference<List<CityInfoForJson>>() {
        });//将json串转换为List,注意写法

        List<CityInfo> cityInfoList = new ArrayList<>();
        //将序列化的List遍历
        for (int i = 1, length = listCity.size(); i < length; i++) {
            //取出listCity中的CityInfoForJson对象
            CityInfoForJson cityInfoForJson = listCity.get(i);
            //取出CityInfoForJson中和CityInfo对象对应的值赋给CityInfo对象
            //将新赋值的CityInfo对象存入新的List
            cityInfoList.add(cityInfoForJson.getCityInfo());
        }
        //执行批量插入操作前先进行整体删除,确保数据最新
        if (cityInfoMapper.truncateCityInfo() < 1) {
            throw new TruncateTableException("删除数据数据失败");
        }
        //执行批量插入操作
        insertBatch(cityInfoList);
    }


    @Override
    public int truncateCityInfo() {
        return cityInfoMapper.truncateCityInfo();
    }

    @Override
    public int insertBatch(List<CityInfo> cityInfoList) throws TruncateTableException {

        //整体删除再批量插入,确保数据最新
        if (cityInfoMapper.truncateCityInfo() < 1) {
            throw new TruncateTableException("删除数据数据失败");
        }
        return cityInfoMapper.insertBatch(cityInfoList);
    }

    @Override
    public List<CityInfo> selectBymodelIdAndPage(String code, Integer size, Integer page) {

        return cityInfoMapper.selectByCodeAndPage(code, size, page);
    }

    @Override
    public int updateByCodeSelective(String code, CityInfo cityInfo) {
        //首先判断空指针
        if (code == null || code.trim().length() == 0 || code.trim().length() > 100) {
            throw new IllegalArgumentException("参数错误");
        }
        if (cityInfo == null || cityInfo.getProvince().trim().length() == 0 || cityInfo.getProvince().trim().length() > 100
                || cityInfo.getCity().trim().length() == 0 || cityInfo.getCity().trim().length() > 100
                || cityInfo.getCnty().trim().length() == 0 || cityInfo.getCnty().trim().length() > 100) {
            throw new IllegalArgumentException("参数错误:必填参数缺失");

        }
        return cityInfoMapper.updateByCodeSelective(code, cityInfo);
    }

    @Override
    public int deleteByCode(String code) {
        if (code == null || code.trim().length() == 0 || code.trim().length() > 100) {
            throw new IllegalArgumentException("参数错误");
        }
        CityInfo cityInfo = cityInfoMapper.selectByCode(code);
        if (cityInfo == null) {
            throw new NullPointerException("未找到");
        }
        return cityInfoMapper.deleteByCode(code);
    }

    @Override
    public CityInfo selectByCode(String code) {
        if (code == null || code.trim().length() == 0 || code.trim().length() > 100) {
            throw new IllegalArgumentException("参数错误");
        }
        return cityInfoMapper.selectByCode(code);
    }


    @Override
    public int insertSelective(CityInfo record) {
        CityInfo cityInfo = new CityInfo();
        //必填字段,数据库不允许为null
        if (record == null || record.getProvince().trim().length() == 0 || record.getProvince().trim().length() > 100
                || record.getCity().trim().length() == 0 || record.getCity().trim().length() > 100
                || record.getCnty().trim().length() == 0 || record.getCnty().trim().length() > 100) {
            throw new IllegalArgumentException("参数错误:必填参数缺失");

        }
        Date currentDate = new Date();
        cityInfo.setProvince(record.getProvince());
        cityInfo.setCity(record.getCity());
        cityInfo.setCode(record.getCode());
        cityInfo.setCnty(record.getCnty());
        //非必填参数.数据库允许为null
        cityInfo.setDistrictEn(record.getDistrictEn());
        cityInfo.setDistrictZh(record.getDistrictEn());
        cityInfo.setLon(record.getLon());
        cityInfo.setLat(record.getLat());
        //isActive对应数据库字段类型为bit,true=1=激活,false=0=未激活(删除)
        cityInfo.setIsActive(true);
        cityInfo.setCreateTime(ServiceUtils.getTenNumbersTimeStamp(currentDate));
        cityInfo.setLastupdateTime(ServiceUtils.getTenNumbersTimeStamp(currentDate));
        cityInfo.setTs(currentDate.getTime());

        return cityInfoMapper.insertSelective(cityInfo);
    }


}
