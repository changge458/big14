package com.oldboy.hbase.mr.maxtemp;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class MaxTempReducer extends Reducer<Text, IntWritable, NullWritable, Put> {
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

        int max = Integer.MIN_VALUE;

        for (IntWritable value : values) {
            max = Math.max(max, value.get());
        }

        Put put = new Put(key.toString().getBytes());
        put.addColumn("f1".getBytes(), "maxtemp".getBytes(), Bytes.toBytes(max + ""));

        context.write(NullWritable.get(), put);

    }
}
