package com.oldboy.kafka.partition;

import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

public class MyPartitioner implements Partitioner {


    public MyPartitioner(VerifiableProperties prop) {
    }


    @Override
    public int partition(Object key, int numPartitions) {

        try {
            int i = Integer.parseInt(key.toString());
            return i % numPartitions;
        } catch (Exception e) {
        }
        return 0;
    }
}
