package com.oldboy.hive.udf;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//第三步，指定函数名称和描述
@Description(
        name = "add",
        value = "this is an add function",
        extended = "select add(1,2) => 3 \nselect add('hello','world') => 'helloworld'"
)

//第一步，继承UDF类
public class TestUDF extends UDF {

    //第二步：需要编写evaluate方法
    public int evaluate(int a, int b) {
        return a + b;
    }

    public String evaluate(String a, String b) {
        return a + b;
    }

    public List<String> evaluate(String a, String b, String c) {
        List<String> list = new ArrayList<String>();
        list.add(a);
        list.add(b);
        list.add(c);
        return list;
    }

    public Map<String,Integer> evaluate(String a,int b) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put(a,b);
        return map;
    }
}
