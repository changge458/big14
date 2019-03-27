package com.oldboy.hive.udf;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Description(
        name = "",
        value = "",
        extended = ""

)

public class ParseMusic extends UDF {

    public Map<String, List<String>> evaluate(String json) {

        String newJson = json.replaceAll("\\\\", "");

        //将新的json转换成对象
        JSONObject jo = JSON.parseObject(newJson);

        //解析用户
        String deviceId = jo.get("deviceId").toString();

        //初始化list
        List<String> list = new ArrayList<String>();
        //初始化map
        Map<String, List<String>> map = new HashMap<String, List<String>>();

        //解析歌曲
        JSONArray eventLogs = jo.getJSONArray("appEventLogs");
        for (Object eventLog : eventLogs) {
            JSONObject jo2 = (JSONObject) eventLog;
            try {
                Object musicID = jo2.get("musicID");
                Object mark = jo2.get("mark");
                String str = musicID + "_" + mark;
                list.add(str);
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
        map.put(deviceId, list);
        return map;
    }


//    public static void main(String[] args) throws Exception {
//
//        File f = new File("D:/teaching/logs");
//        File[] files = f.listFiles();
//        for (File file : files) {
//            BufferedReader br = new BufferedReader(new FileReader(file));
//            String line = null;
//            while((line  = br.readLine()) != null){
//                evaluate(line.split("#")[4]);
//            }
//        }
//
//    }


}
