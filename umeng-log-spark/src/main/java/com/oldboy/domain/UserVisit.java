package com.oldboy.domain;

import java.io.Serializable;

public class UserVisit implements Serializable {

    private String devId;

    private String firstVisitTime;

    private String lastVisitTime;

    private String appId;

    private String appVersion;

    public UserVisit() {
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getFirstVisitTime() {
        return firstVisitTime;
    }

    public void setFirstVisitTime(String firstVisitTime) {
        this.firstVisitTime = firstVisitTime;
    }

    public String getLastVisitTime() {
        return lastVisitTime;
    }

    public void setLastVisitTime(String lastVisitTime) {
        this.lastVisitTime = lastVisitTime;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    @Override
    public String toString() {
        return "UserVisit{" +
                "devId='" + devId + '\'' +
                ", firstVisitTime='" + firstVisitTime + '\'' +
                ", lastVisitTime='" + lastVisitTime + '\'' +
                ", appId='" + appId + '\'' +
                ", appVersion='" + appVersion + '\'' +
                '}';
    }
}
