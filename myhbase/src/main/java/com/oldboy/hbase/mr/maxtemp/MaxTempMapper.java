package com.oldboy.hbase.mr.maxtemp;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class MaxTempMapper extends Mapper<LongWritable, Text, Text, IntWritable> {


    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {


        String line = value.toString();

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
