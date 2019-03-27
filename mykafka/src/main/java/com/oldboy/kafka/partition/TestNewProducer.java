package com.oldboy.kafka.partition;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.Future;

public class TestNewProducer {
    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.put("bootstrap.servers", "s102:9092,s103:9092,s104:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("partitioner.class", "com.oldboy.kafka.partition.MyNewPartitioner");
        props.put("batch.size", "1");

        Producer<Integer, String> producer = new KafkaProducer<>(props);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            ProducerRecord<Integer, String> record = new ProducerRecord<>("t3", i, Integer.toString(i));
            Future<RecordMetadata> future = producer.send(record);
            //future.get();
        }
        System.out.println(System.currentTimeMillis() - start);
        producer.close();
    }
}
