package com.oldboy.hadoop.mapreduce.day01;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * key in 为什么是LongWritable类型,是每行的起始字节位置
 * value in为什么是text类型
 *         因为它是一行文本
 */
public class WCMapper extends Mapper<LongWritable, Text,Text, IntWritable> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String line = value.toString();

        String[] arr = line.split(" ");

        for (String word : arr) {
            Text keyOut = new Text(word);
            IntWritable valueOut = new IntWritable(1);
            //将hello    1传给reduce
            context.write(keyOut,valueOut);
        }
    }
}
