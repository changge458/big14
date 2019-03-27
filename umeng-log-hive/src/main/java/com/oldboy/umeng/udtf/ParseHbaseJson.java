package com.oldboy.umeng.udtf;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oldboy.umeng.columnutil.Log_Event_Column_Util;
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
import java.util.Set;


@Description(
        name = "parsehbasejson",
        value = "xxx",
        extended = "select parsehbasejson(json,json) => devid\tappid\tappversion\tftime\tltime"
)
public class ParseHbaseJson extends GenericUDTF {

    private PrimitiveObjectInspector inputOI;
    private PrimitiveObjectInspector inputOI2;

    //初始化表结构
    //{"tianya_1.2.0":"1552840450228","gaodemap_1.1.0":"1553054423776","faobengplay_1.0.0":"1553606402376"}
    @Override
    public StructObjectInspector initialize(ObjectInspector[] argOIs) throws UDFArgumentException {

        //判断参数的个数
        if (argOIs.length != 2) {
            throw new UDFArgumentException("此函数只能有2个参数");
        }

        //判断参数的类型，必须为String类型
        if (((PrimitiveObjectInspector) argOIs[0]).getPrimitiveCategory() != PrimitiveObjectInspector.PrimitiveCategory.STRING) {
            throw new UDFArgumentException("此函数数据类型必须为String类型");
        }
        if (((PrimitiveObjectInspector) argOIs[1]).getPrimitiveCategory() != PrimitiveObjectInspector.PrimitiveCategory.STRING) {
            throw new UDFArgumentException("此函数数据类型必须为String类型");
        }

        inputOI = (PrimitiveObjectInspector) argOIs[0];
        inputOI2 = (PrimitiveObjectInspector) argOIs[1];


        //构造字段名称集合
        ArrayList<String> fieldNames = new ArrayList<>();
        fieldNames.add("appid");
        fieldNames.add("appVersion");
        fieldNames.add("ftime");
        fieldNames.add("ltime");


        //构造的字段类型集合
        ArrayList<ObjectInspector> fieldOIs = new ArrayList<>();

        //由于输出类型和参数类型一致，故直接将参数类型放入即可
        //fieldOIs.add((argOIs[0]));
        //如果不一致，需要自行构造
        for (int i = 0; i < 4; i++) {
            fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        }

        return ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames, fieldOIs);
    }

    @Override
    public void process(Object[] args) throws HiveException {
        //{"tianya_1.2.0":"1552840450228","gaodemap_1.1.0":"1553054423776","faobengplay_1.0.0":"1553606402376"}

        String ftimeJson = (String) inputOI.getPrimitiveJavaObject(args[0]);
        String ltimeJson = (String) inputOI.getPrimitiveJavaObject(args[1]);

        //解析json字段的所有key
        JSONObject ftimeJo = JSON.parseObject(ftimeJson);
        JSONObject ltimeJo = JSON.parseObject(ltimeJson);


        Set<String> keys = ftimeJo.keySet();

        Object[] objs = new Object[4];
        for (String key : keys) {
            //key:tianya_1.2.0
            String[] arr = key.split("_");
            objs[0] = arr[0];
            objs[1] = arr[1];
            objs[2] = ftimeJo.get(key);
            objs[3] = ltimeJo.get(key);

            forward(objs);

        }


    }

    @Override
    public void close() throws HiveException {

    }
}
