package com.noplugins.keepfit.coachplatform.callback;

public class MessageEvent {
    private double currentLat;
    private double currentLon;
    private String address;
    public MessageEvent() {
    }

    public MessageEvent(double currentLat, double currentLon) {
        this.currentLat = currentLat;
        this.currentLon = currentLon;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getCurrentLat() {
        return currentLat;
    }

    public void setCurrentLat(double currentLat) {
        this.currentLat = currentLat;
    }

    public double getCurrentLon() {
        return currentLon;
    }

    public void setCurrentLon(double currentLon) {
        this.currentLon = currentLon;
    }
}
