package com.oldboy.umeng.genlog;

import com.alibaba.fastjson.JSON;
import com.oldboy.umeng.common.AppBaseLog;
import com.oldboy.umeng.common.AppStartupLog;
import com.oldboy.umeng.util.DictUtil;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Random;

public class GenLogUtil {

    static Random r = new Random();

    //随机生成几个月之内的数据
    public static <T> T genLog(Class<T> clazz, int numMonths) throws Exception {
        T t1 = clazz.newInstance();
        //第一步：将所有的日志类型赋值
        if (t1 instanceof AppBaseLog) {

            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.getType() == String.class) {
                    field.setAccessible(true);
                    if (!field.getName().equals("appPlatform")
                            && !field.getName().equals("osType")
                            && !field.getName().equals("brand")
                            && !field.getName().equals("deviceStyle")) {
                        field.set(t1, DictUtil.getRandomValue(field.getName().toLowerCase()));
                    }
                }
            }
            //将所有日志的创建时间设定
            long createTimestamp = System.currentTimeMillis() - r.nextInt(numMonths * 30 * 24 * 60 * 60) * 1000;
            ((AppBaseLog) t1).setCreatedAtMs(createTimestamp);
        }
        return t1;

    }


    public static void main(String[] args) throws Exception {

        AppStartupLog appStartupLog = genLog(AppStartupLog.class, 3);

        String s = JSON.toJSONString(appStartupLog);

        System.out.println(s);

    }

}
