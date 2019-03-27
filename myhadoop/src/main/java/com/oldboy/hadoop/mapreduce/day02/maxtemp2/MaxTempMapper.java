package com.oldboy.hadoop.mapreduce.day02.maxtemp2;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class MaxTempMapper extends Mapper<LongWritable, Text, Text, Text> {


    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        //value是一行数据
        String line = value.toString();

        //截串出年份
        String year = line.substring(15, 19);

        //截串出气温
        String temp = line.substring(87, 92);

        if (!temp.contains("9999")) {
            //将k-v传给reduce
            context.write(new Text(year), new Text(temp));

        }


    }
}
