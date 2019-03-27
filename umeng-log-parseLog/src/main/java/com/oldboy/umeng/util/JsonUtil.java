package com.oldboy.umeng.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class JsonUtil {

    public static String parseJson(String json, String key) {

        try {
            String newJson = json.replaceAll("\\\\", "");

            JSONObject jo = JSON.parseObject(newJson);

            return jo.getString(key);
        } catch (Exception e) {

            return null;
        }


    }




}
