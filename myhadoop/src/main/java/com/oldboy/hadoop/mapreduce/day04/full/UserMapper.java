package com.oldboy.hadoop.mapreduce.day04.full;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * key in 为什么是LongWritable类型,是每行的起始字节位置
 * value in为什么是text类型
 * 因为它是一行文本
 */
public class UserMapper extends Mapper<Text, Text, Text, Text> {

    @Override
    protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {


        context.write(key, value);
    }
}
