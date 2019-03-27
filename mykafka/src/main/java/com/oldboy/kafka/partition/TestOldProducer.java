package com.oldboy.kafka.partition;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TestOldProducer {

    public static void main(String[] args) {

        Properties prop = new Properties();
        prop.setProperty("metadata.broker.list", "s102:9092,s103:9092,s104:9092");
        prop.setProperty("request.required.acks", "0");
        prop.setProperty("partitioner.class", "com.oldboy.kafka.partition.MyPartitioner");
        prop.setProperty("key.serializer.class", "kafka.serializer.IntegerEncoder");

        ProducerConfig kafkaProp = new ProducerConfig(prop);

        //构造生产者
        Producer<Integer, byte[]> producer = new Producer<>(kafkaProp);

        //构造消息

        //生产者发送消息
        for (int i = 0; i < 100; i++) {
            KeyedMessage<Integer, byte[]> msg = new KeyedMessage<>("t3", i, ("helloworld tom" + i).getBytes());
            producer.send(msg);
            try {
                Thread.sleep(1000);
                System.out.println(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        producer.close();
    }
}
