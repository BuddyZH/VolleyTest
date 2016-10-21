package com.zzh.cm.volleypractice.weatherdata;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by cm on 2016/10/18.
 */
public class WeatherData {
    private String location;
    private String weather;
    private String temperature;

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public static WeatherData parse(JSONObject jsonObject) {
        if (jsonObject == null) {
            return null;
        }
        JSONArray resultArray = jsonObject.optJSONArray("results");
        if (resultArray == null) {
            return null;
        }
        WeatherData weatherData = new WeatherData();
        try {
            JSONObject resultJson = resultArray.getJSONObject(0);
            if (resultJson != null) {
                JSONObject locationJson = resultJson.optJSONObject("location");
                weatherData.location = locationJson.optString("name");
            }
            JSONObject nowJson = resultJson.optJSONObject("now");
            if (nowJson != null) {
                weatherData.weather = nowJson.optString("text");
                weatherData.temperature = nowJson.optString("temperature");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return weatherData;
    }

    @Override
    public String toString() {
        return "地点：" + location +
                "， 天气：" + weather +
                "， 温度：" + temperature + "度";
    }
}
