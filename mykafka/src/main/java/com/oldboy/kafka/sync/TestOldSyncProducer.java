package com.oldboy.kafka.sync;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TestOldSyncProducer {

    public static void main(String[] args) {

        Properties prop = new Properties();
        prop.setProperty("metadata.broker.list","s102:9092,s103:9092,s104:9092");
        prop.setProperty("producer.type","sync");

        ProducerConfig kafkaProp = new ProducerConfig(prop);

        //构造生产者
        Producer<String,byte[]> producer = new Producer<>(kafkaProp);

        //构造消息

        long start = System.currentTimeMillis();
        //生产者发送消息
        for (int i = 0; i < 100000; i++) {
            KeyedMessage<String, byte[]> msg = new KeyedMessage<>("t3", ("helloworld tom"+i).getBytes());
            producer.send(msg);
        }
        System.out.println(System.currentTimeMillis() - start);

        producer.close();
    }
}
