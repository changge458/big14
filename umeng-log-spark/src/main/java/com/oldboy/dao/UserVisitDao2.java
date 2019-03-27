package com.oldboy.dao;

import com.alibaba.fastjson.JSONObject;
import com.oldboy.domain.StartupLog;
import com.oldboy.domain.UserVisit;
import com.oldboy.util.HbaseUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class UserVisitDao2 {


    public static UserVisit getUserVisit(StartupLog startupLog) {

        String deviceId = startupLog.getDeviceId();
        String timestamp = startupLog.getCreatedAtMs();
        Long ts = Long.valueOf(timestamp);

        String appId = startupLog.getAppId();
        String appVersion = startupLog.getAppVersion();


        //将app以baofeng_1.1.0的方式写入到json中，例如
        // firstvisittime:{"baofeng_1.1.0":150988777782,"baofeng_1.2.0":150988324282,"souhuvideo_1.2.0":150988324282}
        String compKey = appId + "_" + appVersion;


        String firstvisitjson = HbaseUtil.get("umeng:uservisit2", deviceId, "time", "firstvisittime");
        //从json值中获取compkey对应的value
        String firstvistitime = fromJsonParseTime(firstvisitjson, compKey);

        String lastvisitjson = HbaseUtil.get("umeng:uservisit2", deviceId, "time", "lastvisittime");
        //从json值中获取compkey对应的value
        String lastvistitime = fromJsonParseTime(lastvisitjson, compKey);


        //此deviceid第一次访问hbase
        //timestamp为第一次和最后一次访问
        if (firstvistitime == null) {
            String dataJson = createDataJson(firstvisitjson, compKey, timestamp);
            HbaseUtil.put("umeng:uservisit2", deviceId, "time", "firstvisittime", dataJson);
        }
        if (lastvistitime == null) {
            String dataJson = createDataJson(firstvisitjson, compKey, timestamp);
            HbaseUtil.put("umeng:uservisit2", deviceId, "time", "lastvisittime", dataJson);

        } else {
            long firstvisittimestamp = Long.valueOf(firstvistitime);
            long lastvisittimestamp = Long.valueOf(lastvistitime);

            if (ts < firstvisittimestamp) {
                firstvistitime = timestamp;
                String dataJson = createDataJson(firstvisitjson, compKey, firstvistitime);
                HbaseUtil.put("umeng:uservisit2", deviceId, "time", "firstvisittime", dataJson);
            }
            if (ts > lastvisittimestamp) {
                lastvistitime = timestamp;
                String dataJson = createDataJson(lastvisitjson, compKey, lastvistitime);
                HbaseUtil.put("umeng:uservisit2", deviceId, "time", "lastvisittime", dataJson);
            }

        }

        UserVisit visit = new UserVisit();
        visit.setDevId(deviceId);
        visit.setFirstVisitTime(firstvisitjson);
        visit.setLastVisitTime(lastvisitjson);
        visit.setAppId(appId);
        visit.setAppVersion(appVersion);

        return visit;

    }

    /**
     * 判断这个json串中是否含有指定的key，含有的话，将value获取
     *
     * @param resultJson
     * @param key
     * @return
     */
    private static String fromJsonParseTime(String resultJson, String key) {
        if (resultJson != null) {
            Map map = JSONObject.parseObject(resultJson, Map.class);
            Object value = map.get(key);
            if (value != null) {
                return value.toString();
            }
        }
        return null;
    }

    /**
     * 通过给定的json串，给定的k-v，来进行构造或添加map数据
     *
     * @param resultJson
     * @param compkey
     * @return
     */
    private static String createDataJson(String resultJson, String compkey, String timestamp) {

        Map<String, String> map = new HashMap<>();
        if (StringUtils.isNotBlank(resultJson)) {
            /**
             * 数据示例
             * {"baofeng_1.1.0":150988777782}
             */
            map = JSONObject.parseObject(resultJson, Map.class);

        }
        map.put(compkey, timestamp);
        return JSONObject.toJSONString(map);
    }
}
