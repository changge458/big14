package com.oldboy.hadoop.mapreduce.day05.temptags;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;

public class TestJson {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new FileReader("D:/temptags.txt"));

        String line = null;

        while((line = br.readLine()) != null){
            String[] arr = line.split("\t");

            String busId = arr[0];

            String json = arr[1];

            parseJson(busId,json);

        }
        br.close();



    }


    public static void parseJson(String busId , String json){

        JSONObject jo = JSON.parseObject(json);

        JSONArray jArray = jo.getJSONArray("extInfoList");

        if(jArray != null && jArray.size() > 0 ){
            JSONObject jo2 = (JSONObject)jArray.get(0);
            JSONArray jArray2 = jo2.getJSONArray("values");
            StringBuffer sb = new StringBuffer();
            for (Object o : jArray2) {
                sb.append(o + ",");
            }
            String line = sb.toString();
            String str = line.substring(0, line.length()-1);
            //将sb置空
            sb.setLength(0);
            System.out.println(busId + "\t" + str);
            System.out.println("===================================================");

        }



    }

}
