package com.oldboy.kafka.ack;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TestOldProducer {

    public static void main(String[] args) {

        Properties prop = new Properties();
        prop.setProperty("metadata.broker.list","s102:9092,s103:9092,s104:9092");
        prop.setProperty("request.required.acks","0");

        ProducerConfig kafkaProp = new ProducerConfig(prop);

        //构造生产者
        Producer<String,byte[]> producer = new Producer<>(kafkaProp);

        //构造消息

        KeyedMessage<String, byte[]> msg2 = new KeyedMessage<>("t3", "helloworld how are you".getBytes());

        List<KeyedMessage<String, byte[]>> messages = new ArrayList<>();
        messages.add(msg2);

        //生产者发送消息
        for (int i = 0; i < 100; i++) {
            KeyedMessage<String, byte[]> msg = new KeyedMessage<>("t3", ("helloworld tom"+i).getBytes());
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
