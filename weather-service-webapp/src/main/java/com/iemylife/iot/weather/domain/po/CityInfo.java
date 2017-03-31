package com.iemylife.iot.weather.domain.po;

import com.iemylife.iot.weather.domain.vo.CityInfoReturnValue;

import java.util.Date;

public class CityInfo {
    private Integer id;

    private String province;

    private String city;

    private String code;

    private String cnty;

    private String districtEn;

    private String districtZh;

    private String lon;

    private String lat;

    private Long createTime;

    private Long lastupdateTime;

    private Boolean isActive;

    private Long ts;

    public CityInfoReturnValue getCityInfoReturnValue() {
        CityInfoReturnValue cityInfoReturnValue = new CityInfoReturnValue();
        cityInfoReturnValue.setProvince(this.province);
        cityInfoReturnValue.setCity(this.city);
        cityInfoReturnValue.setCode(this.code);
        cityInfoReturnValue.setCnty(this.cnty);
        cityInfoReturnValue.setDistrictEn(this.districtEn);
        cityInfoReturnValue.setDistrictZh(this.districtZh);
        cityInfoReturnValue.setLon(this.lon);
        cityInfoReturnValue.setLat(this.lat);
        return cityInfoReturnValue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getCnty() {
        return cnty;
    }

    public void setCnty(String cnty) {
        this.cnty = cnty == null ? null : cnty.trim();
    }

    public String getDistrictEn() {
        return districtEn;
    }

    public void setDistrictEn(String districtEn) {
        this.districtEn = districtEn == null ? null : districtEn.trim();
    }

    public String getDistrictZh() {
        return districtZh;
    }

    public void setDistrictZh(String districtZh) {
        this.districtZh = districtZh == null ? null : districtZh.trim();
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon == null ? null : lon.trim();
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat == null ? null : lat.trim();
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getLastupdateTime() {
        return lastupdateTime;
    }

    public void setLastupdateTime(Long lastupdateTime) {
        this.lastupdateTime = lastupdateTime;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }
}