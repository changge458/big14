package com.oldboy.hadoop.mapreduce.day02.maxtemp2;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * 如何将两种类的value传入reducer
 */
public class MaxTempCombiner extends Reducer<Text, Text,Text, Text> {

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        Integer max = Integer.MIN_VALUE;
        Integer min = Integer.MAX_VALUE;
        Integer sum = 0;
        Integer count = 0;

        //迭代相同年份的所有温度
        for (Text value : values) {
            int newVal = Integer.parseInt(value.toString());

            max = Math.max(newVal, max);
            min = Math.min(newVal, min);
            sum += newVal;
            count ++;
        }
        //写入到文件
        context.write(key,new Text(max + "\t" + min + "\t" + sum + "\t" + count));


    }
}
