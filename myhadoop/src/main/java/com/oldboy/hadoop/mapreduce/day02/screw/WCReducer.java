package com.oldboy.hadoop.mapreduce.day02.screw;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class WCReducer extends Reducer<Text,IntWritable,Text,IntWritable> {

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        //将相同的key的所有value来进行相加
        int sum = 0;
        for (IntWritable value : values) {
            sum += value.get();
        }
        context.write(key,new IntWritable(sum));
    }

}
