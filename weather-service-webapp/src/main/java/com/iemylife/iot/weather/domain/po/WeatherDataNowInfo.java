package com.iemylife.iot.weather.domain.po;

public class WeatherDataNowInfo {
    private Integer id;

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

    private String reportData;

    private Long updateTime;

    private Long createTime;

    private Boolean isActive;

    private Long ts;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getCondCode() {
        return condCode;
    }

    public void setCondCode(String condCode) {
        this.condCode = condCode == null ? null : condCode.trim();
    }

    public String getCondTxt() {
        return condTxt;
    }

    public void setCondTxt(String condTxt) {
        this.condTxt = condTxt == null ? null : condTxt.trim();
    }

    public String getFeel() {
        return feel;
    }

    public void setFeel(String feel) {
        this.feel = feel == null ? null : feel.trim();
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity == null ? null : humidity.trim();
    }

    public String getPcpn() {
        return pcpn;
    }

    public void setPcpn(String pcpn) {
        this.pcpn = pcpn == null ? null : pcpn.trim();
    }

    public String getPres() {
        return pres;
    }

    public void setPres(String pres) {
        this.pres = pres == null ? null : pres.trim();
    }

    public String getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(String temperatureMax) {
        this.temperatureMax = temperatureMax == null ? null : temperatureMax.trim();
    }

    public String getTemperatureMin() {
        return temperatureMin;
    }

    public void setTemperatureMin(String temperatureMin) {
        this.temperatureMin = temperatureMin == null ? null : temperatureMin.trim();
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility == null ? null : visibility.trim();
    }

    public String getWindDeg() {
        return windDeg;
    }

    public void setWindDeg(String windDeg) {
        this.windDeg = windDeg == null ? null : windDeg.trim();
    }

    public String getWindDir() {
        return windDir;
    }

    public void setWindDir(String windDir) {
        this.windDir = windDir == null ? null : windDir.trim();
    }

    public String getWinDsc() {
        return winDsc;
    }

    public void setWinDsc(String winDsc) {
        this.winDsc = winDsc == null ? null : winDsc.trim();
    }

    public String getWindSpd() {
        return windSpd;
    }

    public void setWindSpd(String windSpd) {
        this.windSpd = windSpd == null ? null : windSpd.trim();
    }

    public String getExtendData() {
        return extendData;
    }

    public void setExtendData(String extendData) {
        this.extendData = extendData == null ? null : extendData.trim();
    }

    public String getWeatherProvider() {
        return weatherProvider;
    }

    public void setWeatherProvider(String weatherProvider) {
        this.weatherProvider = weatherProvider == null ? null : weatherProvider.trim();
    }

    public String getReportData() {
        return reportData;
    }

    public void setReportData(String reportData) {
        this.reportData = reportData == null ? null : reportData.trim();
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