package com.oldboy.hadoop.mapreduce.day02.testpartition;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class WCPartitioner extends Partitioner<Text, IntWritable> {
    @Override
    public int getPartition(Text key, IntWritable val, int numPartitions) {

        String strKey = key.toString();
        try {
            Integer.parseInt(strKey);
            return 0;
        } catch (Exception e) {
            return 1;
        }

    }
}
