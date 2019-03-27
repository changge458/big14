package com.oldboy.umeng.util;

import com.oldboy.umeng.phone.DeviceInfo;
import io.lettuce.core.KeyValue;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RedisUtil {
    static RedisClient client;
    static StatefulRedisConnection<String, String> connect;
    static RedisAsyncCommands<String, String> commands;


    static {
        //初始化客户端
        client = RedisClient.create("redis://s101:6379");

        //获取连接
        connect = client.connect();

        //使用异步方式访问
        commands = connect.async();

    }


    //通过devid，从redis中获取相应的硬件信息
    public static DeviceInfo getDeviceInfo(String devId) {
        DeviceInfo devInfo = null;
        try {
            //判断存不存在
            Long exist = commands.exists(devId).get();
            devInfo = new DeviceInfo();
            if (exist == 1) {
                List<KeyValue<String, String>> list = commands.hmget(devId, "appPlatform", "osType", "brand", "deviceStyle").get();
                //通过获取的值给devinfo赋值
                devInfo.setAppPlatform(list.get(0).getValue());
                devInfo.setOsType(list.get(1).getValue());
                devInfo.setBrand(list.get(2).getValue());
                devInfo.setDeviceStyle(list.get(3).getValue());

            } else {
                //没有的话，随机从deviceList中选择一个硬件信息，将其写入redis中
                String devicelist = DictUtil.getRandomValue("devicelist");
                String[] arr = devicelist.split("#");

                //"appPlatform", "osType", "brand", "deviceStyle"
                String appPlatform = arr[0];
                String osType = DictUtil.getRandomOstype(appPlatform);
                String brand = arr[1];
                String deviceStyle = arr[2];

                Map<String, String> map = new HashMap<String, String>();
                //"appPlatform", "osType", "brand", "deviceStyle"
                map.put("appPlatform", appPlatform);
                map.put("osType", osType);
                map.put("brand", brand);
                map.put("deviceStyle", deviceStyle);


                String s = commands.hmset(devId, map).get();

                devInfo.setAppPlatform(appPlatform);
                devInfo.setOsType(osType);
                devInfo.setBrand(brand);
                devInfo.setDeviceStyle(deviceStyle);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return devInfo;
    }


    //通过devid，从redis中获取相应的硬件信息
    public static String getAppVersion(String devId, String appId) {
        String appVersion = null;
        try {
            //判断存不存在
            boolean exist = commands.hexists(devId, appId).get();
            if (exist) {
                appVersion = commands.hget(devId, appId).get();
            } else {
                //没有的话，随机从deviceList中选择一个硬件信息，将其写入redis中
                appVersion = DictUtil.getRandomValue("appversion");
                commands.hset(devId, appId, appVersion);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appVersion;
    }


    public static void main(String[] args) {

        System.out.println(getDeviceInfo("dev000001").toString());
        //getDeviceInfo("dev002");

    }


}
