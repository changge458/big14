package com.oldboy.hadoop.mapreduce.day04.second;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

//编写分组对比器
//将自定义相同的key放入到同一个reduce循环中
public class MyGroupingComparator extends WritableComparator {

    //必填项
    //目的在于创建key的实例
    public MyGroupingComparator() {
        super(Compkey.class, true);
    }

    //判断是相等不相等
    //而不是判断谁大谁小
    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        Compkey ck1 = (Compkey) a;
        Compkey ck2 = (Compkey) b;
        return ck1.getYear().compareTo(ck2.getYear());
    }
}
