package com.oldboy.umeng.genlog;

import com.alibaba.fastjson.JSON;
import com.oldboy.umeng.common.*;
import com.oldboy.umeng.util.DictUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenLogAgg {
    static Random r = new Random();

    public static String genLogAgg(int type, String deviceId, String date) {

        try {

            Class clazz = AppLogAggEntity.class;
            Object t1 = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                field.set(t1, DictUtil.getRandomValue(field.getName().toLowerCase()));
            }

            ((AppLogAggEntity) t1).setAppErrorLogs(genLogList(AppErrorLog.class, date, type));
            ((AppLogAggEntity) t1).setAppEventLogs(genLogList(AppEventLog.class, date, type));
            ((AppLogAggEntity) t1).setAppPageLogs(genLogList(AppPageLog.class, date, type));
            ((AppLogAggEntity) t1).setAppStartupLogs(genLogList(AppStartupLog.class, date, type));
            ((AppLogAggEntity) t1).setAppUsageLogs(genLogList(AppUsageLog.class, date, type));

            ((AppLogAggEntity) t1).setDeviceId(deviceId);

            return JSON.toJSONString(t1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static <T> List<T> genLogList(Class<T> clazz, String date, int type) throws Exception {

        List<T> list = new ArrayList<>();
        Random r = new Random();

        if (clazz.equals(AppStartupLog.class)) {
            for (int i = 0; i < r.nextInt(2); i++) {
                list.add(new GenLogUtil(date).genLog(clazz));
            }
        } else if (clazz.equals(AppEventLog.class)) {
            for (int i = 0; i < r.nextInt(10); i++) {
                list.add(new GenLogUtil(type, date).genLog(clazz));
            }
        } else {
            for (int i = 0; i < r.nextInt(4); i++) {
                list.add(new GenLogUtil(date).genLog(clazz));
            }
        }
        return list;
    }
}
