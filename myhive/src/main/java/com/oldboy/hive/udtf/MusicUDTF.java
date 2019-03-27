package com.oldboy.hive.udtf;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Description(
        name = "ParseEvent",
        value = "通过解析json字段，输出id,music,mark三个字段",
        extended = "select ParseEvent(json) => 'Device000001','社会摇',5"
)
public class MusicUDTF extends GenericUDTF {

    private PrimitiveObjectInspector inputOI;

    //生成输出表结构
    @Override
    public StructObjectInspector initialize(ObjectInspector[] argOIs) throws UDFArgumentException {
        //判断参数的个数
        if (argOIs.length != 1) {
            throw new UDFArgumentException("此函数只能有一个参数");
        }

        //判断参数的类型，必须为String类型
        if (((PrimitiveObjectInspector) argOIs[0]).getPrimitiveCategory() != PrimitiveObjectInspector.PrimitiveCategory.STRING) {
            throw new UDFArgumentException("此函数数据类型必须为String类型");
        }

        inputOI = (PrimitiveObjectInspector) argOIs[0];

        //构造的字段名称集合
        ArrayList<String> fieldNames = new ArrayList<String>();

        //构造的字段类型集合
        ArrayList<ObjectInspector> fieldOIs = new ArrayList<ObjectInspector>();

        fieldNames.add("id");
        fieldNames.add("music");
        fieldNames.add("mark");
        //由于输出类型和参数类型一致，故直接将参数类型放入即可
        //fieldOIs.add((argOIs[0]));
        //如果不一致，需要自行构造
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaIntObjectInspector);

        return ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames, fieldOIs);
    }

    @Override
    public void process(Object[] args) throws HiveException {
        String json = (String) inputOI.getPrimitiveJavaObject(args[0]);

        String newJson = json.replaceAll("\\\\", "");

        //将新的json转换成对象
        JSONObject jo = JSON.parseObject(newJson);

        //解析用户
        String id = jo.get("deviceId").toString();
        //解析歌曲
        JSONArray eventLogs = jo.getJSONArray("appEventLogs");

        //初始化object[]
        Object[] objs = new Object[3];

        for (Object eventLog : eventLogs) {
            JSONObject jo2 = (JSONObject) eventLog;
            try {

                Object musicID = jo2.get("musicID");
                Object mark = jo2.get("mark");

                objs[0] = id;
                objs[1] = musicID.toString();
                objs[2] = Integer.parseInt(mark.toString());

                forward(objs);

            } catch (Exception e) {
                //e.printStackTrace();
            }
        }

    }

    //什么也不做
    @Override
    public void close() throws HiveException {

    }
}
