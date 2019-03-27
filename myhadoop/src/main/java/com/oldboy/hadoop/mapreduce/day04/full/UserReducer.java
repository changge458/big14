package com.oldboy.hadoop.mapreduce.day04.full;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class UserReducer extends Reducer<Text, Text,Text, Text> {

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        //将相同的key的所有value来进行相加
        for (Text value : values) {
            context.write(key,value);
        }

    }
}
