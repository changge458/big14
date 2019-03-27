package com.oldboy.hive.udtf;

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

@Description(
        name = "myexplode",
        value = "this is a myexplode(string) function",
        extended = "select myexplode('hello world') => hello\nworld"
)

public class MyExplode extends GenericUDTF {

    private PrimitiveObjectInspector inputOI;

    //通过输入的参数个数和参数类型定义输出的表结构(列名+列数据格式)
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

        fieldNames.add("word");
        //由于输出类型和参数类型一致，故直接将参数类型放入即可
        //fieldOIs.add((argOIs[0]));
        //如果不一致，需要自行构造
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);

        return ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames, fieldOIs);

    }


    //正式处理数据
    @Override
    public void process(Object[] args) throws HiveException {
        //arg是hive数据类型，需要转换成java数据类型
        //解铃还须系铃人
        String line = (String) inputOI.getPrimitiveJavaObject(args[0]);

        Object[] objs = new Object[1];

        String[] arr = line.split(" ");

        for (String word : arr) {
            objs[0] = word;
            forward(objs);
        }
    }

    //什么也不做
    @Override
    public void close() throws HiveException {

    }
}
