package com.iemylife.iot.weather.domain.vo;

import com.iemylife.iot.weather.domain.po.CityInfo;
import com.iemylife.iot.weather.util.ServiceUtils;

import java.util.Date;

/**
 * Created by prf on 2017/3/31.
 */
public class CityInfoForJson {
    private String id;
    private String cityEn;
    private String cityZh;
    private String countryCode;
    private String countryEn;
    private String countryZh;
    private String provinceEn;
    private String provinceZh;
    private String leaderEn;
    private String leaderZh;
    private String lat;
    private String lon;

    public CityInfo getCityInfo() {

        Date date = new Date();
        CityInfo cityInfo = new CityInfo();
        //将数据库字段和json返回值一一对应
        cityInfo.setProvince(this.provinceZh);
        cityInfo.setCity(this.cityZh);
        cityInfo.setCode(this.id);
        cityInfo.setCnty(this.countryZh);
        cityInfo.setDistrictEn(this.countryEn);
        cityInfo.setDistrictZh(this.countryZh);
        cityInfo.setLon(this.lon);
        cityInfo.setLat(this.lat);
        cityInfo.setCreateTime(ServiceUtils.getTenNumbersTimeStamp(date));
        cityInfo.setLastupdateTime(ServiceUtils.getTenNumbersTimeStamp(date));
        cityInfo.setTs(date.getTime());
        cityInfo.setIsActive(true);//置1
        return cityInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCityEn() {
        return cityEn;
    }

    public void setCityEn(String cityEn) {
        this.cityEn = cityEn;
    }

    public String getCityZh() {
        return cityZh;
    }

    public void setCityZh(String cityZh) {
        this.cityZh = cityZh;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryEn() {
        return countryEn;
    }

    public void setCountryEn(String countryEn) {
        this.countryEn = countryEn;
    }

    public String getCountryZh() {
        return countryZh;
    }

    public void setCountryZh(String countryZh) {
        this.countryZh = countryZh;
    }

    public String getProvinceEn() {
        return provinceEn;
    }

    public void setProvinceEn(String provinceEn) {
        this.provinceEn = provinceEn;
    }

    public String getProvinceZh() {
        return provinceZh;
    }

    public void setProvinceZh(String provinceZh) {
        this.provinceZh = provinceZh;
    }

    public String getLeaderEn() {
        return leaderEn;
    }

    public void setLeaderEn(String leaderEn) {
        this.leaderEn = leaderEn;
    }

    public String getLeaderZh() {
        return leaderZh;
    }

    public void setLeaderZh(String leaderZh) {
        this.leaderZh = leaderZh;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "CityInfoReturnValue{" +
                "id='" + id + '\'' +
                ", cityEn='" + cityEn + '\'' +
                ", cityZh='" + cityZh + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", countryEn='" + countryEn + '\'' +
                ", countryZh='" + countryZh + '\'' +
                ", provinceEn='" + provinceEn + '\'' +
                ", provinceZh='" + provinceZh + '\'' +
                ", leaderEn='" + leaderEn + '\'' +
                ", leaderZh='" + leaderZh + '\'' +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                '}';
    }
}
