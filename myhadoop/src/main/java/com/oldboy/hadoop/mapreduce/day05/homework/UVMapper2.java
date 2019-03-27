package com.oldboy.hadoop.mapreduce.day05.homework;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * 11	xiaoming	url1	20171224
 * 第二步：key：url+date
 */
public class UVMapper2 extends Mapper<LongWritable, Text, Text, IntWritable> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String line = value.toString();

        String[] arr = line.split("\t");

        String url = arr[2];
        String date = arr[3];

        context.write(new Text(url + "\t" + date), new IntWritable(1));

    }
}
