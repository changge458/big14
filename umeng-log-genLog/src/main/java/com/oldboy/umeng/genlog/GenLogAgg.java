package com.oldboy.umeng.genlog;

import com.alibaba.fastjson.JSON;
import com.oldboy.umeng.common.*;
import com.oldboy.umeng.phone.DeviceInfo;
import com.oldboy.umeng.util.DictUtil;
import com.oldboy.umeng.util.RedisUtil;
import org.apache.commons.beanutils.BeanUtils;


import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenLogAgg {
    static Random r = new Random();

    public static String genLogAgg(int numUsers, int numMonths) throws Exception {

        if (numUsers > 1000000) {
            throw new Exception("人数不能超过1000000");
        }

        AppLogAggEntity agg = new AppLogAggEntity();

        //生成deviceid
        int i = r.nextInt(numUsers);
        DecimalFormat df = new DecimalFormat("000000");
        String devid = "dev" + df.format(i);
        agg.setDeviceId(devid);

        //通过devid去redis中寻找是否含有相应的硬件信息
        //有的话，直接从redis获取
        DeviceInfo deviceInfo = RedisUtil.getDeviceInfo(devid);
        //将deviceinfo中的属性值，直接赋值给agg
        BeanUtils.copyProperties(agg, deviceInfo);


        //通过devid去redis中查找app信息:包括appid和appversion
        String appid = DictUtil.getRandomValue("appid");
        String appVersion = RedisUtil.getAppVersion(devid, appid);
        agg.setAppId(appid);
        agg.setAppVersion(appVersion);


        agg.setTenantId(DictUtil.getRandomValue("tenantid"));
        agg.setAppChannel(DictUtil.getRandomValue("appchannel"));

        agg.setAppErrorLogs(genLogList(AppErrorLog.class, numMonths));
        agg.setAppEventLogs(genLogList(AppEventLog.class, numMonths));
        agg.setAppPageLogs(genLogList(AppPageLog.class, numMonths));
        agg.setAppStartupLogs(genLogList(AppStartupLog.class, numMonths));
        agg.setAppUsageLogs(genLogList(AppUsageLog.class, numMonths));

        String s = JSON.toJSONString(agg);
        return s;

    }


    private static <T> List<T> genLogList(Class<T> clazz, int numMonths) throws Exception {

        List<T> list = new ArrayList<>();

        if (clazz.equals(AppStartupLog.class)) {
             for (int i = 0; i < r.nextInt(2); i++) {
            list.add(GenLogUtil.genLog(clazz, numMonths));
            }
        } else if (clazz.equals(AppEventLog.class)) {
            for (int i = 0; i < r.nextInt(5); i++) {
                list.add(GenLogUtil.genLog(clazz, numMonths));
            }
        } else {
            for (int i = 0; i < r.nextInt(4); i++) {
                list.add(GenLogUtil.genLog(clazz, numMonths));
            }
        }
        return list;
    }

    public static void main(String[] args) throws Exception {
        genLogAgg(1000, 3);
//        genLogAgg(1000, 3);
//        genLogAgg(1000, 3);
    }
}
