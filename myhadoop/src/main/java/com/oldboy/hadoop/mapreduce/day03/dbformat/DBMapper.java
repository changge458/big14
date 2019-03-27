package com.oldboy.hadoop.mapreduce.day03.dbformat;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class DBMapper extends Mapper<LongWritable, MyDBWritable, Text, IntWritable> {

    @Override
    protected void map(LongWritable key, MyDBWritable value, Context context) throws IOException, InterruptedException {

        String line = value.getLine();

        //截串出年份
        String year = line.substring(15, 19);

        //截串出气温
        String temp = line.substring(87, 92);

        if (!temp.contains("9999")) {
            //将k-v传给reduce
            context.write(new Text(year), new IntWritable(Integer.parseInt(temp)));

        }

    }
}