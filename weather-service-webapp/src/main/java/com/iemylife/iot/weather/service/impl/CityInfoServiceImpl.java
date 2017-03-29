package com.iemylife.iot.weather.service.impl;

import com.iemylife.iot.weather.domain.po.CityInfo;
import com.iemylife.iot.weather.mapper.CityInfoMapper;
import com.iemylife.iot.weather.service.ICityInfoService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by prf on 2017/3/29.
 */
public class CityInfoServiceImpl implements ICityInfoService {
    @Autowired
    private CityInfoMapper cityInfoMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return cityInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(CityInfo record) {
        return cityInfoMapper.insert(record);
    }

    @Override
    public int insertSelective(CityInfo record) {
        CityInfo cityInfo = new CityInfo();
        if (record != null && record.getProvince() != ""
                && record.getCity() != ""
                && record.getCode() != ""
                && record.getCnty() != "") {
            cityInfo.setProvince(record.getProvince());
            cityInfo.setCity(record.getCity());
            cityInfo.setCode(record.getCode());
            cityInfo.setCnty(record.getCnty());
        }


        cityInfo.setDistrictEn(record.getDistrictEn());
        cityInfo.setDistrictZh(record.getDistrictEn());
        cityInfo.setLon(record.getLon());
        cityInfo.setLat(record.getLat());

        return cityInfoMapper.insertSelective(record);
    }

    @Override
    public CityInfo selectByPrimaryKey(Integer id) {

        return cityInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(CityInfo record) {
        return cityInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CityInfo record) {
        return cityInfoMapper.updateByPrimaryKey(record);
    }
}
