package com.oldboy.hive.udf;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;

import java.util.ArrayList;
import java.util.List;

@Description(
        name = "parseJson",
        value = "this is a parseJson function",
        extended = "select parseJson(json) => ['技师优秀','味道好']"
)

public class JsonUDF extends UDF {

    public  List<String> evaluate(String json){

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
