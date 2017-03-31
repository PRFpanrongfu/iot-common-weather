package com.iemylife.iot.weather.domain.vo;

/**
 * Created by prf on 2017/3/30.
 */
public class CityInfoReturnValue {
    private String province;
    private String city;
    private String code;
    private String cnty;
    private String districtEn;
    private String districtZh;
    private String lon;
    private String lat;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCnty() {
        return cnty;
    }

    public void setCnty(String cnty) {
        this.cnty = cnty;
    }

    public String getDistrictEn() {
        return districtEn;
    }

    public void setDistrictEn(String districtEn) {
        this.districtEn = districtEn;
    }

    public String getDistrictZh() {
        return districtZh;
    }

    public void setDistrictZh(String districtZh) {
        this.districtZh = districtZh;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "CityInfoReturnValue{" +
                "province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", code='" + code + '\'' +
                ", cnty='" + cnty + '\'' +
                ", districtEn='" + districtEn + '\'' +
                ", districtZh='" + districtZh + '\'' +
                ", lon='" + lon + '\'' +
                ", lat='" + lat + '\'' +
                '}';
    }
}
