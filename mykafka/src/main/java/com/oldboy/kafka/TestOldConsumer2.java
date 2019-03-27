package com.oldboy.kafka;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

import java.util.*;

public class TestOldConsumer2 {

    public static void main(String[] args) {

        Properties prop = new Properties();
        //prop.setProperty("metadata.broker.list","s101:9092");
        prop.setProperty("zookeeper.connect", "s102:2181,s103:2181,s104:2181");

        prop.setProperty("group.id", "aaa");

        prop.setProperty("auto.commit.enable","false");

        prop.setProperty("auto.offset.reset","smallest");

        ConsumerConfig consumerProp = new ConsumerConfig(prop);

        ConsumerConnector conn = Consumer.createJavaConsumerConnector(consumerProp);

        //得到连接后，开始拉取数据
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("t1", 2);
        Map<String, List<KafkaStream<byte[], byte[]>>> streams = conn.createMessageStreams(map);

        List<KafkaStream<byte[], byte[]>> streamList = streams.get("t1");

        //迭代消息流获取数据
        for (KafkaStream<byte[], byte[]> stream : streamList) {

            new Thread() {
                @Override
                public void run() {
                    ConsumerIterator<byte[], byte[]> consumerIte = stream.iterator();
                    while (consumerIte.hasNext()) {
                        MessageAndMetadata<byte[], byte[]> metadata = consumerIte.next();

                        System.out.println(Thread.currentThread().getName() + "\t" + "topic:" + metadata.partition() + "\t" + new String(metadata.message()));
                        conn.commitOffsets();
                    }
                }
            }.start();

        }


    }

}
