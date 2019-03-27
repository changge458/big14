package com.oldboy.umeng.phone;

public class DeviceInfo {

    private String appPlatform;            //平台
    private String osType;                //操作系统
    private String brand;                //品牌
    private String deviceStyle;            //机型

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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDeviceStyle() {
        return deviceStyle;
    }

    public void setDeviceStyle(String deviceStyle) {
        this.deviceStyle = deviceStyle;
    }

    @Override
    public String toString() {
        return "DeviceInfo{" +
                "appPlatform='" + appPlatform + '\'' +
                ", osType='" + osType + '\'' +
                ", brand='" + brand + '\'' +
                ", deviceStyle='" + deviceStyle + '\'' +
                '}';
    }
}
