package com.oldboy.hadoop.mapreduce.day03.seqformat;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class SequenceFileReducer extends Reducer<Text, IntWritable,Text,Text> {

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

        int max = Integer.MIN_VALUE;

        for (IntWritable value : values) {
            max = Math.max(max, value.get());
        }

        context.write(key,new Text(max+""));

    }
}
