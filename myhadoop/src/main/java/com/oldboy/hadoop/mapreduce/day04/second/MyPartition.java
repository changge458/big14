package com.oldboy.hadoop.mapreduce.day04.second;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Partitioner;

public class MyPartition extends Partitioner<Compkey, NullWritable> {

    @Override
    public int getPartition(Compkey compkey, NullWritable nullWritable, int numPartitions) {

        int yearNum = Integer.parseInt(compkey.getYear());
        if(yearNum <= 1901){
            return 0;
        }
        else {
            return 1;
        }

    }
}
