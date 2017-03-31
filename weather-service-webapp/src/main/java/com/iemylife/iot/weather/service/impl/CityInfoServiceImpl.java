package com.iemylife.iot.weather.service.impl;

import com.iemylife.iot.weather.domain.po.CityInfo;
import com.iemylife.iot.weather.mapper.CityInfoMapper;
import com.iemylife.iot.weather.service.ICityInfoService;
import com.iemylife.iot.weather.util.ServiceUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by prf on 2017/3/29.
 */
@Service
public class CityInfoServiceImpl implements ICityInfoService {
    @Autowired
    private CityInfoMapper cityInfoMapper;

    @Override


    public int insertBatch(String city, String code) {
        if (city == null || city.trim().length() == 0 || city.trim().length() > 100) {
            throw new IllegalArgumentException("参数错误");
        }
        if (code == null || code.trim().length() == 0 || code.trim().length() > 100) {
            throw new IllegalArgumentException("参数错误");
        }
        CityInfo cityInfo = cityInfoMapper.selectByCode(code);
        if (cityInfo != null)
            System.out.println("已存在,不需要更新");
        return cityInfoMapper.insertBatch(city, code);
    }

    @Override
    public List<CityInfo> selectBymodelIdAndPage(@Param("code") String code, @Param("size") Integer size, @Param("page") Integer page) {
        return cityInfoMapper.selectByCodeAndPage(code, size, page);
    }

    @Override
    public int updateByCodeSelective(String code, CityInfo cityInfo) {
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
