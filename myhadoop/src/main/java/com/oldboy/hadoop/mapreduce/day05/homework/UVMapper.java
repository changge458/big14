package com.oldboy.hadoop.mapreduce.day05.homework;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * 11	xiaoming	url1	20171224
 * 第一步去重
 */
public class UVMapper extends Mapper<LongWritable, Text, Text, NullWritable> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        //获取整行
        String line = value.toString();
        //将其最后的空格去掉
        String newLine = line.trim();

        context.write(new Text(newLine),NullWritable.get());

    }
}
