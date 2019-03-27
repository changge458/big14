package com.oldboy.hadoop.mapreduce.day05.join.reducer;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

public class ReduceJoinReducer extends Reducer<Compkey, Text, Text, NullWritable> {

    @Override
    protected void reduce(Compkey key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        Iterator<Text> it = values.iterator();
        //迭代获取第一个条目
        String userLine = it.next().toString();

        while (it.hasNext()){
            String orderLine = it.next().toString();

            String[] orderArr = orderLine.split("\t");

            String orderno = orderArr[1];
            String oprice = orderArr[2];

            context.write(new Text(userLine + "\t" + orderno + "\t" + oprice) , NullWritable.get() );


        }

    }
}
