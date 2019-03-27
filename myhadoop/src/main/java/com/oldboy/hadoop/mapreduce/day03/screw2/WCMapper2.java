package com.oldboy.hadoop.mapreduce.day03.screw2;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class WCMapper2 extends Mapper<Text, Text, Text, IntWritable> {


    @Override
    protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {

        context.write(key, new IntWritable(Integer.parseInt(value.toString())));

    }
}