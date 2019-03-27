package com.oldboy.hadoop.mapreduce.day03.dbformat;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class DBReducer extends Reducer<Text, IntWritable,MyDBWritable2, NullWritable> {

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

        int max = Integer.MIN_VALUE;

        for (IntWritable value : values) {
            max = Math.max(max, value.get());
        }

        MyDBWritable2 mw = new MyDBWritable2(key.toString(), max);

        context.write(mw,NullWritable.get());

    }
}
