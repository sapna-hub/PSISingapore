package com.capgemini.psisingapore.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Model class that holds data from the response as an object called location
 */
public class Location implements Serializable {

    private String name;
    private String appInfo;
    private double latitude;
    private double longitude;
    private HashMap<String, Integer> no2OneHourMax = new HashMap<>();
    private HashMap<String, Integer> o3EightHourMax = new HashMap<>();
    private HashMap<String, Integer> psiTwentyFourHourly = new HashMap<>();
    private HashMap<String, Integer> so2TwentyFourHourly = new HashMap<>();


    public Location(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public String getAppInfo() {
        return appInfo;
    }

    public void setAppInfo(String appInfo) {
        this.appInfo = appInfo;
    }

    public void setNo2OnehourMax(String key, int val) {
        HashMap<String, Integer> mapNo2OneHourMax = new HashMap<>();
        mapNo2OneHourMax.put(key, val);
        this.no2OneHourMax = mapNo2OneHourMax;
    }

    public void setO3EightHourMax(String key, int val) {
        HashMap<String, Integer> mapO3EightHourMax = new HashMap<>();
        mapO3EightHourMax.put(key, val);
        this.o3EightHourMax = mapO3EightHourMax;
    }

    public void setPsiTwentyFourHourly(String key, int val) {
        HashMap<String, Integer> mapPsiTwentyFourHourly = new HashMap<>();
        mapPsiTwentyFourHourly.put(key, val);
        this.psiTwentyFourHourly = mapPsiTwentyFourHourly;
    }

    public void setSo2TwentyFourHourly(String key, int val) {
        HashMap<String, Integer> mapSo2TwentyFourHourly = new HashMap<>();
        mapSo2TwentyFourHourly.put(key, val);
        this.so2TwentyFourHourly = mapSo2TwentyFourHourly;
    }

    public Map<String, Integer> getNo2OneHourMax() {
        return no2OneHourMax;
    }

    public Map<String, Integer> getO3EightHourMax() {
        return o3EightHourMax;
    }

    public Map<String, Integer> getPsiTwentyFourHourly() {
        return psiTwentyFourHourly;
    }

    public Map<String, Integer> getSo2TwentyFourHourly() {
        return so2TwentyFourHourly;
    }

    @Override
    public String toString() {
        return "Location [latitude = " + latitude + ", longitude = " + longitude + "]";
    }
}
