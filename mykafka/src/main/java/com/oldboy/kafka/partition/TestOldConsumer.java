package com.oldboy.kafka.partition;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

import java.util.*;

public class TestOldConsumer {

    public static void main(String[] args) {

        Properties prop = new Properties();
        //prop.setProperty("metadata.broker.list","s101:9092");
        prop.setProperty("zookeeper.connect", "s102:2181,s103:2181,s104:2181");

        prop.setProperty("group.id", "xxx");

        ConsumerConfig consumerProp = new ConsumerConfig(prop);

        ConsumerConnector conn = Consumer.createJavaConsumerConnector(consumerProp);

        //得到连接后，开始拉取数据
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("t3", 1);
        Map<String, List<KafkaStream<byte[], byte[]>>> streams = conn.createMessageStreams(map);

        Set<Map.Entry<String, List<KafkaStream<byte[], byte[]>>>> entrySet = streams.entrySet();

        for (Map.Entry<String, List<KafkaStream<byte[], byte[]>>> entry : entrySet) {

            new Thread() {
                @Override
                public void run() {
                    String topic = entry.getKey();

                    List<KafkaStream<byte[], byte[]>> value = entry.getValue();
                    //迭代消息流获取数据
                    for (KafkaStream<byte[], byte[]> stream : value) {
                        ConsumerIterator<byte[], byte[]> consumerIte = stream.iterator();
                        while (consumerIte.hasNext()) {

                            MessageAndMetadata<byte[], byte[]> metadata = consumerIte.next();


                            System.out.println(Thread.currentThread().getName() + "\t" + "topic:" + metadata.partition() + "\t" + new String(metadata.message()));


                            //conn.commitOffsets();
                        }

                    }
                }
            }.start();


        }

    }
}
