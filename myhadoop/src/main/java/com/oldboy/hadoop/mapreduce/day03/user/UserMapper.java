package com.oldboy.hadoop.mapreduce.day03.user;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class UserMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String line = value.toString();

        String[] arr = line.split("\t");
        if(arr.length >= 3 && !arr[2].equals("")){
            String pass = arr[2];
            context.write(new Text(pass), new IntWritable(1));
        }
    }
}