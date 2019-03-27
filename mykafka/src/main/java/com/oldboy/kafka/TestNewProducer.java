package com.oldboy.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class TestNewProducer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "s102:9092,s103:9092,s104:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            ProducerRecord<String, String> record = new ProducerRecord<>("t2", Integer.toString(i), Integer.toString(i));
            producer.send(record);
        }
        System.out.println(System.currentTimeMillis() - start);
        producer.close();
    }
}
