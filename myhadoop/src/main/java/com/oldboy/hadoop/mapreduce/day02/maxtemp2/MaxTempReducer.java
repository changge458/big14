package com.oldboy.hadoop.mapreduce.day02.maxtemp2;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class MaxTempReducer extends Reducer<Text, Text,Text, Text> {

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        Integer max = Integer.MIN_VALUE;
        Integer min = Integer.MAX_VALUE;
        Integer sum = 0;
        Integer count = 0;

        //迭代相同年份的所有温度
        for (Text value : values) {
            String[] arr = value.toString().split("\t");
            max = Math.max(Integer.parseInt(arr[0]), max);
            min = Math.min(Integer.parseInt(arr[1]), min);
            sum += Integer.parseInt(arr[2]);
            count += Integer.parseInt(arr[3]);

        }

        //写入到文件
        context.write(key,new Text("最高气温："+max + "\t" + "最低气温：" + min +  "\t" + "平均气温：" + sum/count  ));


    }
}
