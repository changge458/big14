package com.oldboy.hadoop.mapreduce.day05.temptags;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.List;

public class TemptagsMapper2 extends Mapper<Text,Text,Compkey, NullWritable> {

    @Override
    protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {

        String busId = key.toString();
        String tag_num = value.toString();

        String[] arr = tag_num.split("\t");
        String tag = arr[0];
        int num = Integer.parseInt(arr[1]);
        Compkey ck = new Compkey(busId, tag , num);

        context.write(ck, NullWritable.get());





    }
}
