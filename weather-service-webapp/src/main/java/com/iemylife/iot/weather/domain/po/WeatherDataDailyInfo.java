package com.iemylife.iot.weather.domain.po;

public class WeatherDataDailyInfo {
    private Integer id;

    private String city;

    private String code;

    private String condDayCode;
    private String condDayTxt;

    private String condNightCode;

    private String condNightTxt;

    private String feel;

    private String humidity;

    private String pcpn;

    private String pres;

    private String temperatureMax;

    private String temperatureMin;

    private String visibility;

    private String winDeg;

    private String windDir;

    private String windSc;

    private String windSpd;

    private String extendData;

    private String weatherProvider;

    private String reportDate;

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

    public String getCondDayCode() {
        return condDayCode;
    }

    public void setCondDayCode(String condDayCode) {
        this.condDayCode = condDayCode == null ? null : condDayCode.trim();
    }

    public String getCondDayTxt() {
        return condDayTxt;
    }

    public void setCondDayTxt(String condDayTxt) {
        this.condDayTxt = condDayTxt;
    }

    public String getCondNightCode() {
        return condNightCode;
    }

    public void setCondNightCode(String condNightCode) {
        this.condNightCode = condNightCode == null ? null : condNightCode.trim();
    }

    public String getCondNightTxt() {
        return condNightTxt;
    }

    public void setCondNightTxt(String condNightTxt) {
        this.condNightTxt = condNightTxt == null ? null : condNightTxt.trim();
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

    public String getWinDeg() {
        return winDeg;
    }

    public void setWinDeg(String winDeg) {
        this.winDeg = winDeg == null ? null : winDeg.trim();
    }

    public String getWindDir() {
        return windDir;
    }

    public void setWindDir(String windDir) {
        this.windDir = windDir == null ? null : windDir.trim();
    }

    public String getWindSc() {
        return windSc;
    }

    public void setWindSc(String windSc) {
        this.windSc = windSc == null ? null : windSc.trim();
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

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate == null ? null : reportDate.trim();
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