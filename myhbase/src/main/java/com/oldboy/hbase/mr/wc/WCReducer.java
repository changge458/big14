package com.oldboy.hbase.mr.wc;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class WCReducer extends Reducer<Text, IntWritable, NullWritable, Put> {
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

        int sum = 0;

        for (IntWritable value : values) {
            sum += value.get();
        }

        Put put = new Put(key.toString().getBytes());
        put.addColumn("f1".getBytes(), "count".getBytes(), Bytes.toBytes(sum+""));

        context.write(NullWritable.get(), put);

    }
}