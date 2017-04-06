package com.iemylife.iot.weather.service.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iemylife.iot.weather.domain.exception.TruncateTableException;
import com.iemylife.iot.weather.domain.po.CityInfo;
import com.iemylife.iot.weather.domain.vo.CityInfoForJson;
import com.iemylife.iot.weather.domain.vo.RemanentCityInfo;
import com.iemylife.iot.weather.mapper.CityInfoMapper;
import com.iemylife.iot.weather.service.ICityInfoService;
import com.iemylife.iot.weather.util.ServiceUtils;
import org.apache.ibatis.annotations.Param;
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
    @Value(value = "${weather.citylist-url}")
    private String url;//城市信息列表url,在application.properties中配置
    @Autowired
    private CityInfoMapper cityInfoMapper;

    private RestTemplate restTemplate = new RestTemplate();
    private ObjectMapper objectMapper = new ObjectMapper();

    public void refreshCityInfos() throws IOException, TruncateTableException {

        String returnValue = restTemplate.getForObject(url, String.class);

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);  //只对实体起作用，对map不起作用
        //List<CityInfoForJson> listCity = objectMapper.readValue(returnValue, List.class);
        List<CityInfoForJson> listCity = objectMapper.readValue(returnValue, new TypeReference<List<CityInfoForJson>>() {
        });//转换为List

        List<CityInfo> cityInfoList = new ArrayList<>();

        for (int i = 1, length = listCity.size(); i < length; i++) {
            CityInfoForJson cityInfoForJson = listCity.get(i);
            cityInfoList.add(cityInfoForJson.getCityInfo());
        }
        insertBatch(cityInfoList);
    }

    /**
     * 更新剩余字段
     * 此方法可用做插入,数据库里已存在全国城市的信息,insert语句没有太大意义
     * 插入一条记录可看做激活一条记录(置isActive=1),同时插入createTime lastUpdateTime ts
     * 修改一条记录等价于
     * 删除一条记录等价于,传入isActive参数为0
     *
     * @param code             城市代码
     * @param remanentCityInfo
     * @return
     */
    @Override
    public int updateRemanentField(String code, RemanentCityInfo remanentCityInfo) {
        return cityInfoMapper.updateRemanentField(code, remanentCityInfo);
    }

    @Override
    public int truncateCityInfo() {
        return cityInfoMapper.truncateCityInfo();
    }

    @Override
    public int insertBatch(List<CityInfo> cityInfoList) throws TruncateTableException {
        //CityInfo info = new CityInfo();
        //for (CityInfo cityinfo : cityInfoList) {
        //    //info = cityinfo;
        //    String code = cityinfo.getCode();
        //    if (!(cityInfoMapper.selectByCode(code) == null)) {
        //        throw new IllegalArgumentException("已存在不需要更新");
        //    }
        //}
        //验证数据库中一条记录是否对应cityInfoList中的一个CityInfo对象
        //若存在,将cityInfoList中的此对象删除,再进行批量插入
        //List<String> codeList = new ArrayList<>();
        //for (int i = 1; i < cityInfoList.size(); i++) {
        //    CityInfo cityInfo = cityInfoList.get(i);
        //    codeList.set(i, cityInfo.getCode());
        //    String code = codeList.get(i);
        //    CityInfo cityInfo1 = cityInfoMapper.selectByCode(code);
        //    if (cityInfo1 != null) {
        //        cityInfoList.remove(i);
        //    }
        //    cityInfoMapper.insertBatch(cityInfoList);
        //
        //}

        //整体删除在批量插入,确保数据最新
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
