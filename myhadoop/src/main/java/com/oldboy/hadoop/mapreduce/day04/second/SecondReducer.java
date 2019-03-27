package com.oldboy.hadoop.mapreduce.day04.second;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class SecondReducer extends Reducer<Compkey, NullWritable, Text, IntWritable> {
    @Override
    protected void reduce(Compkey key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {

        int i = 0;
        for (NullWritable value : values) {
            context.write(new Text(key.getYear()), new IntWritable(key.getTemp()));
            i++;

        }
        context.write(new Text("=========================================================="),new IntWritable());
        System.out.println(i);

    }
}
