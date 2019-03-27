package com.oldboy.hadoop.mapreduce.day05.temptags;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtil {

    public static List<String> parseJson(String json){

        JSONObject jo = JSON.parseObject(json);

        JSONArray jArray = jo.getJSONArray("extInfoList");

        List<String> list = new ArrayList<String>();

        if(jArray != null && jArray.size() > 0 ){
            JSONObject jo2 = (JSONObject)jArray.get(0);
            JSONArray jArray2 = jo2.getJSONArray("values");

            for (Object o : jArray2) {
                list.add(o.toString());
            }
            return list;
        }
        return null;

    }

}
