package com.zzh.cm.volleypractice.weatherdata;

/**
 * Created by cm on 2016/10/20.
 */
public class WeatherInfo {
   private Location location;
   private Now now;
   private String last_update;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Now getNow() {
        return now;
    }

    public void setNow(Now now) {
        this.now = now;
    }

    public String getLast_update() {
        return last_update;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }

    @Override
    public String toString() {
        return "地点：" + location.getName() + "，天气：" + now.getText() + "，温度：" + now.getTemperature() + "度，" + last_update;
    }
}
