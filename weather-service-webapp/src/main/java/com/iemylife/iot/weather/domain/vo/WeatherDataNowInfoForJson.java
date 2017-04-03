package com.iemylife.iot.weather.domain.vo;

import com.iemylife.iot.weather.domain.po.WeatherDataNowInfo;

import java.io.Serializable;

/**
 * Created by prf on 2017/4/3.
 */
public class WeatherDataNowInfoForJson implements Serializable {
    private static final long serialVersionUID = -1L;
    private String city;

    private String code;

    private String condCode;

    private String condTxt;

    private String feel;

    private String humidity;

    private String pcpn;

    private String pres;

    private String temperatureMax;

    private String temperatureMin;

    private String visibility;

    private String windDeg;

    private String windDir;

    private String winDsc;

    private String windSpd;

    private String extendData;

    private String weatherProvider;

    private String reportDate;
    private Long updateTime;
    private Long createTime;
    private boolean isActive;
    private Long ts;


    public WeatherDataNowInfoForJson() {
    }

    public WeatherDataNowInfoForJson(String city, String code, String condCode, String condTxt, String feel, String humidity, String pcpn, String pres, String temperatureMax, String temperatureMin, String visibility, String windDeg, String windDir, String winDsc, String windSpd, String extendData, String weatherProvider, String reportDate, Long updateTime, Long createTime, boolean isActive, Long ts) {
        this.city = city;
        this.code = code;
        this.condCode = condCode;
        this.condTxt = condTxt;
        this.feel = feel;
        this.humidity = humidity;
        this.pcpn = pcpn;
        this.pres = pres;
        this.temperatureMax = temperatureMax;
        this.temperatureMin = temperatureMin;
        this.visibility = visibility;
        this.windDeg = windDeg;
        this.windDir = windDir;
        this.winDsc = winDsc;
        this.windSpd = windSpd;
        this.extendData = extendData;
        this.weatherProvider = weatherProvider;
        this.reportDate = reportDate;
        this.updateTime = updateTime;
        this.createTime = createTime;
        this.isActive = isActive;
        this.ts = ts;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public String getCondCode() {
        return condCode;
    }

    public void setCondCode(String condCode) {
        this.condCode = condCode;
    }

    public String getCondTxt() {
        return condTxt;
    }

    public void setCondTxt(String condTxt) {
        this.condTxt = condTxt;
    }

    public String getFeel() {
        return feel;
    }

    public void setFeel(String feel) {
        this.feel = feel;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPcpn() {
        return pcpn;
    }

    public void setPcpn(String pcpn) {
        this.pcpn = pcpn;
    }

    public String getPres() {
        return pres;
    }

    public void setPres(String pres) {
        this.pres = pres;
    }

    public String getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(String temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    public String getTemperatureMin() {
        return temperatureMin;
    }

    public void setTemperatureMin(String temperatureMin) {
        this.temperatureMin = temperatureMin;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getWindDeg() {
        return windDeg;
    }

    public void setWindDeg(String windDeg) {
        this.windDeg = windDeg;
    }

    public String getWindDir() {
        return windDir;
    }

    public void setWindDir(String windDir) {
        this.windDir = windDir;
    }

    public String getWinDsc() {
        return winDsc;
    }

    public void setWinDsc(String winDsc) {
        this.winDsc = winDsc;
    }

    public String getWindSpd() {
        return windSpd;
    }

    public void setWindSpd(String windSpd) {
        this.windSpd = windSpd;
    }

    public String getExtendData() {
        return extendData;
    }

    public void setExtendData(String extendData) {
        this.extendData = extendData;
    }

    public String getWeatherProvider() {
        return weatherProvider;
    }

    public void setWeatherProvider(String weatherProvider) {
        this.weatherProvider = weatherProvider;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setISActive(boolean active) {
        isActive = active;
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }
}
