package com.deka.myapplication;

public class Earthquake {
    public double magnitude;
    public String location;
    public long timeInMilliSeconds;
    public String url;

    public Earthquake(double magnitude,String location,long timeInMilliSeconds,String url){
        this.magnitude = magnitude;
        this.timeInMilliSeconds = timeInMilliSeconds;
        this.location = location;
        this.url = url;
    }
}
