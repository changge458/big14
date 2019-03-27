package com.oldboy.domain;

import java.io.Serializable;

public class StartupLog implements Serializable {

    private String deviceId;
    private String deviceStyle;
    private String screenSize;
    private String carrier;
    private String remote_addr;
    private String country;
    private String province;
    private String http_client_time;
    private String msec;
    private String createdAtMs;
    private String network;
    private String appVersion;
    private String appChannel;
    private String appPlatform;
    private String osType;
    private String appId;
    private String brand;
    private String tenantId;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceStyle() {
        return deviceStyle;
    }

    public void setDeviceStyle(String deviceStyle) {
        this.deviceStyle = deviceStyle;
    }

    public String getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getRemote_addr() {
        return remote_addr;
    }

    public void setRemote_addr(String remote_addr) {
        this.remote_addr = remote_addr;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getHttp_client_time() {
        return http_client_time;
    }

    public void setHttp_client_time(String http_client_time) {
        this.http_client_time = http_client_time;
    }

    public String getMsec() {
        return msec;
    }

    public void setMsec(String msec) {
        this.msec = msec;
    }

    public String getCreatedAtMs() {
        return createdAtMs;
    }

    public void setCreatedAtMs(String createdAtMs) {
        this.createdAtMs = createdAtMs;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppChannel() {
        return appChannel;
    }

    public void setAppChannel(String appChannel) {
        this.appChannel = appChannel;
    }

    public String getAppPlatform() {
        return appPlatform;
    }

    public void setAppPlatform(String appPlatform) {
        this.appPlatform = appPlatform;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public StartupLog(String deviceId, String deviceStyle, String screenSize, String carrier, String remote_addr, String country, String province, String http_client_time, String msec, String createdAtMs, String network, String appVersion, String appChannel, String appPlatform, String osType, String appId, String brand, String tenantId) {
        this.deviceId = deviceId;
        this.deviceStyle = deviceStyle;
        this.screenSize = screenSize;
        this.carrier = carrier;
        this.remote_addr = remote_addr;
        this.country = country;
        this.province = province;
        this.http_client_time = http_client_time;
        this.msec = msec;
        this.createdAtMs = createdAtMs;
        this.network = network;
        this.appVersion = appVersion;
        this.appChannel = appChannel;
        this.appPlatform = appPlatform;
        this.osType = osType;
        this.appId = appId;
        this.brand = brand;
        this.tenantId = tenantId;
    }

    public StartupLog() {
    }

    @Override
    public String toString() {
        return "StartupLog{" +
                "deviceId='" + deviceId + '\'' +
                ", deviceStyle='" + deviceStyle + '\'' +
                ", screenSize='" + screenSize + '\'' +
                ", carrier='" + carrier + '\'' +
                ", remote_addr='" + remote_addr + '\'' +
                ", country='" + country + '\'' +
                ", province='" + province + '\'' +
                ", http_client_time='" + http_client_time + '\'' +
                ", msec='" + msec + '\'' +
                ", createdAtMs='" + createdAtMs + '\'' +
                ", network='" + network + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", appChannel='" + appChannel + '\'' +
                ", appPlatform='" + appPlatform + '\'' +
                ", osType='" + osType + '\'' +
                ", appId='" + appId + '\'' +
                ", brand='" + brand + '\'' +
                ", tenantId='" + tenantId + '\'' +
                '}';
    }
}
