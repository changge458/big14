package com.oldboy.hadoop.mapreduce.day04.second;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class SecondMapper extends Mapper<LongWritable, Text, Compkey, NullWritable> {

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
            context.write(new Compkey(year, Integer.parseInt(temp)), NullWritable.get());

        }


    }
}
