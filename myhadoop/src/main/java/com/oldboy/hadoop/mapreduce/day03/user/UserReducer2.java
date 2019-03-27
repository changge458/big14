package com.oldboy.hadoop.mapreduce.day03.user;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class UserReducer2 extends Reducer<KVPair, NullWritable, DuowanWritable, NullWritable> {

    TreeSet<KVPair> ts = new TreeSet<KVPair>();


    @Override
    protected void reduce(KVPair key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {

        //map存在于内存中，不应该将所有的数据全部放进去
        String pass = key.getPass();
        int count = key.getCount();
        KVPair kvPair = new KVPair(pass, count);
        ts.add(kvPair);

        //数据无限添加，所以treeset中无限排序
        //当条数为101条的时候，则删除第101个
        if (ts.size() > 100) {
            ts.remove(ts.last());
        }
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        for (KVPair kvPair : ts) {
            context.write(new DuowanWritable(kvPair.getPass(),kvPair.getCount()),NullWritable.get());

        }
    }
}
