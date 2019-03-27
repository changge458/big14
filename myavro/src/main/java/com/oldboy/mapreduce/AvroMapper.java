package com.oldboy.mapreduce;

import com.oldboy.File;
import org.apache.avro.mapred.AvroKey;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class AvroMapper extends Mapper<AvroKey<File>, NullWritable, Text, IntWritable> {


    @Override
    protected void map(AvroKey<File> key, NullWritable value, Context context) throws IOException, InterruptedException {

        String line = key.datum().getBody().toString();

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
