package com.capgemini.psisingapore.model;

import java.io.Serializable;
import java.util.HashMap;

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
        HashMap<String, Integer> no2OnehourMax = new HashMap<>();
        no2OnehourMax.put(key, val);
        this.no2OneHourMax = no2OnehourMax;
    }

    public void setO3EightHourMax(String key, int val) {
        HashMap<String, Integer> o3EightHourMax = new HashMap<>();
        o3EightHourMax.put(key, val);
        this.o3EightHourMax = o3EightHourMax;
    }

    public void setPsiTwentyFourHourly(String key, int val) {
        HashMap<String, Integer> psiTwentyFourHourly = new HashMap<>();
        psiTwentyFourHourly.put(key, val);
        this.psiTwentyFourHourly = psiTwentyFourHourly;
    }

    public void setSo2TwentyFourHourly(String key, int val) {
        HashMap<String, Integer> so2TwentyFourHourly = new HashMap<>();
        so2TwentyFourHourly.put(key, val);
        this.so2TwentyFourHourly = so2TwentyFourHourly;
    }

    public HashMap<String, Integer> getNo2OnehourMax() {
        return no2OneHourMax;
    }

    public HashMap<String, Integer> getO3EightHourMax() {
        return o3EightHourMax;
    }

    public HashMap<String, Integer> getPsiTwentyFourHourly() {
        return psiTwentyFourHourly;
    }

    public HashMap<String, Integer> getSo2TwentyFourHourly() {
        return so2TwentyFourHourly;
    }

    @Override
    public String toString() {
        return "Location [latitude = " + latitude + ", longitude = " + longitude + "]";
    }
}
