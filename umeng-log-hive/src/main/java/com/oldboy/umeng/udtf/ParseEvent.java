package com.oldboy.umeng.udtf;

import com.oldboy.umeng.columnutil.Log_Event_Column_Util;
import com.oldboy.umeng.constant.Constants;
import com.oldboy.umeng.util.ParseKeyUtil;
import com.oldboy.umeng.util.PropUtil;
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
import java.util.List;

@Description(
        name = "ParseEvent",
        value = "xxx",
        extended = "select ParseEvent(line) => xxx,yyy,zzz...."
)
public class ParseEvent extends GenericUDTF {


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
        ArrayList<String> fieldNames = Log_Event_Column_Util.list;

        //构造的字段类型集合
        ArrayList<ObjectInspector> fieldOIs = new ArrayList<>();

        //由于输出类型和参数类型一致，故直接将参数类型放入即可
        //fieldOIs.add((argOIs[0]));
        //如果不一致，需要自行构造
        for (String fieldName : fieldNames) {
            fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        }

        return ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames, fieldOIs);
    }

    @Override
    public void process(Object[] args) throws HiveException {
        String line = (String) inputOI.getPrimitiveJavaObject(args[0]);

        String tablename = PropUtil.getValue(Constants.EVENT_TABLE_NAME);

        List<Object[]> list = ParseKeyUtil.parseKey(line, tablename);

        if(list!= null && list.size() > 0){
            for (Object[] objs : list) {
                forward(objs);
            }
        }

    }

    //什么也不做
    @Override
    public void close() throws HiveException {

    }
}
