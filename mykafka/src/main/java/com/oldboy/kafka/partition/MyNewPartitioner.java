package com.oldboy.kafka.partition;

import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;
import org.apache.kafka.common.Cluster;

import java.util.Map;

public class MyNewPartitioner implements org.apache.kafka.clients.producer.Partitioner {


    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        try {

            Integer numPartitions = cluster.partitionCountForTopic(topic);

            int i = Integer.parseInt(key.toString());
            return i % numPartitions;
        } catch (Exception e) {
        }
        return 0;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
