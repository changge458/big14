package com.oldboy.hadoop.mapreduce.day02.maxtemp;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class MaxTempReducer extends Reducer<Text, IntWritable,Text, Text> {

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

        Integer max = Integer.MIN_VALUE;
        Integer min = Integer.MAX_VALUE;
        Integer count = 0;
        Integer sum = 0;
        //迭代相同年份的所有温度
        for (IntWritable value : values) {
            max = Math.max(value.get(), max);
            min = Math.min(value.get(), min);
            count ++;
            sum += value.get();
        }

        //写入到文件
        context.write(key,new Text("最高气温："+max + "\t" + "最低气温：" + min + "\t" + "平均气温：" + sum/count));


    }
}
