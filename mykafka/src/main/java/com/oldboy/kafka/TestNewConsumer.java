package com.oldboy.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class TestNewConsumer {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "s102:9092,s103:9092,s104:9092");
        props.put("group.id", "myGroup");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        //通过subscribe函数订阅多主题
        //consumer.subscribe(Arrays.asList("t3", "t2"));

        //可以指定分区
        TopicPartition tp = new TopicPartition("t2", 0);
        consumer.assign(Arrays.asList(tp));

        //手动指定偏移量消费
        //consumer.seek(tp,0);


        //修改偏移量,修改元数据
//        Map<TopicPartition, OffsetAndMetadata> map = new HashMap<>();
//        map.put(tp,new OffsetAndMetadata(0));
//        consumer.commitSync(map);


        while (true) {
            //100为每次等待多久
            //也可以理解为consumer拉取broker的数据的时长
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("offset = " + record.offset() + "," + "key = " + record.key() + "," + "value = " + record.value() + ",partition = " + record.partition());
            }
        }
    }
}
