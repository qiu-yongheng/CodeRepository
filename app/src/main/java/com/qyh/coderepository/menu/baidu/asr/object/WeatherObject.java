package com.qyh.coderepository.menu.baidu.asr.object;

/**
 * @author 邱永恒
 * @time 2018/1/20  13:22
 * @desc 天气
 */

public class WeatherObject {
    private String date;
    private String region;
    private String weather;
    private String wind;
    private String temp;
    private String focus;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getFocus() {
        return focus;
    }

    public void setFocus(String focus) {
        this.focus = focus;
    }

    @Override
    public String toString() {
        return "WeatherObject{" +
                "date='" + date + '\'' +
                ", region='" + region + '\'' +
                ", weather='" + weather + '\'' +
                ", wind='" + wind + '\'' +
                ", temp='" + temp + '\'' +
                ", focus='" + focus + '\'' +
                '}';
    }
}
