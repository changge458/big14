package com.oldboy.hadoop.mapreduce.day03.user;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.TreeSet;

/**
 * k-v类型是两个text
 */
public class UserMapper2 extends Mapper<Text, Text, KVPair, NullWritable> {

    TreeSet<KVPair> ts = new TreeSet<KVPair>();

    @Override
    protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {

        String pass = key.toString();
        int count = Integer.parseInt(value.toString());

        ts.add(new KVPair(pass,count));

        //数据无限添加，所以treeset中无限排序
        //当条数为101条的时候，则删除第101个
        if (ts.size() > 100) {
            ts.remove(ts.last());
        }

    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {

        for (KVPair kvPair : ts) {
            context.write(kvPair,NullWritable.get());
        }

    }
}