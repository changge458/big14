package com.oldboy.hadoop.mapreduce.day02.screw;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.Random;

public class WCMapper2 extends Mapper<LongWritable, Text, Text, IntWritable> {


    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String line = value.toString();

        String[] arr = line.split("\t");

        context.write(new Text(arr[0].split("_")[0]), new IntWritable(Integer.parseInt(arr[1])));

    }
}