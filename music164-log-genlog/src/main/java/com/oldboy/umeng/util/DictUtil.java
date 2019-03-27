package com.oldboy.umeng.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class DictUtil {

    private static Map<String, List<String>> map = new HashMap<String, List<String>>();


    static {
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("dictionary.dat");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line = null;
            List<String> list = null;
            while ((line = br.readLine()) != null) {


                //将带有[的解析为map的key
                if (line.startsWith("[")) {

                    list = new ArrayList<String>();

                    String key = line.substring(1, line.length() - 1);

                    map.put(key, list);

                }

                //其他的添加到List中
                else {
                    list.add(line);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static String getRandomValue(String key) {
        Random r = new Random();
        List<String> values = map.get(key);
        if(values == null){
            return null;
        }
        return values.get(r.nextInt(values.size()));
    }

    //在生成歌曲事件日志时使用，随机生成喜欢的音乐事件
    public static String randomValue_positive(){
        Random r = new Random();
        List<String> values = map.get("eventid");
        if(values == null){
            return null;
        }
        //0-3
        return values.get(r.nextInt(values.size()-4));
    }
    //在生成歌曲事件日志时使用，随机生成不喜欢的音乐事件
    public static String randomValue_negative(){
        Random r = new Random();
        List<String> values = map.get("eventid");
        if(values == null){
            return null;
        }
        //4-7
        return values.get(r.nextInt(values.size()-4)+4);
    }



}
