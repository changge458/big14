package com.oldboy.umeng.genlog;

import com.oldboy.umeng.common.AppBaseLog;
import com.oldboy.umeng.common.AppEventLog;
import com.oldboy.umeng.util.DictUtil;
import com.oldboy.umeng.util.GenLogTimeUtil;
import com.oldboy.umeng.util.MusicTableUtil;
import com.oldboy.umeng.util.MusicUtil;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Random;

public class GenLogUtil {

    static Random r = new Random();

    public static int type;
    public static String date;

    public GenLogUtil(String date) {
        this.date = date;
    }

    public GenLogUtil(int type, String date) {
        this.type = type;
        this.date = date;
    }


    public static <T> T genLog(Class<T> clazz) throws Exception {
            T t1 = clazz.newInstance();
            //第一步：将所有的日志类型赋值
            if (t1 instanceof AppBaseLog) {

                Field[] fields = clazz.getDeclaredFields();
                //AppPageLog log = new AppPageLog();
                for (Field field : fields) {
                    if (field.getType() == String.class) {
                        field.setAccessible(true);
                        field.set(t1, DictUtil.getRandomValue(field.getName().toLowerCase()));
                    }
                }
                //将所有日志的创建时间设定
                ((AppBaseLog) t1).setCreatedAtMs(GenLogTimeUtil.genTime(date));
            }



            if (t1 instanceof AppEventLog) {
            //强转为eventLog
            AppEventLog eventLog = (AppEventLog) t1;

            switch (r.nextInt(2)) {
                case 0:
                    genPositive(eventLog);
                    break;
                case 1:
                    genNegative(eventLog);
                    break;
            }
        }
        // System.out.println(JSON.toJSONString(t1, true));
        return t1;
    }

    private static void genPositive(AppEventLog eventLog){

        String table = MusicTableUtil.parseTable(type);
        //positive变量是好评的eventid
        String positive = DictUtil.randomValue_positive();

        int i = r.nextInt(MusicUtil.MUSIC_MAP_LIST.get(table).size());
        Map<String, String> music_time = MusicUtil.MUSIC_MAP_LIST.get(table).get(i);

        //设置歌曲名称
        eventLog.setMusicID(music_time.get("mname"));


        //设置播放时间和播放时长
        if (positive.equals("play") || positive.equals("listen")) {
            eventLog.setDuration(music_time.get("mtime"));
            eventLog.setPlayTime(eventLog.getCreatedAtMs() + "");
        }
        eventLog.setEventId(positive);
        eventLog.setMark(MusicUtil.MARK_MAPPING.get(positive));
    }

    private static void genNegative(AppEventLog eventLog){

        String table = MusicTableUtil.parseTable(r.nextInt(9) + 1);
        //negative变量是差评的eventid
        String negative = DictUtil.randomValue_negative();

        int i = r.nextInt(MusicUtil.MUSIC_MAP_LIST.get(table).size());
        Map<String, String> music_time = MusicUtil.MUSIC_MAP_LIST.get(table).get(i);

        //设置歌曲名称
        eventLog.setMusicID(music_time.get("mname"));

        //设置打分
        eventLog.setMark(MusicUtil.MARK_MAPPING.get(negative));

        //设置播放时间和播放时长
        if (negative.equals("skip")) {
            eventLog.setDuration("00:20");
            eventLog.setPlayTime(eventLog.getCreatedAtMs() + "");
        }

        eventLog.setEventId(negative);
        eventLog.setMark(MusicUtil.MARK_MAPPING.get(negative));
    }

}
