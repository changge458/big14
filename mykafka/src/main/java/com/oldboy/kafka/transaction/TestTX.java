package com.oldboy.kafka.transaction;

import kafka.Kafka;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.AuthorizationException;
import org.apache.kafka.common.errors.OutOfOrderSequenceException;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Arrays;
import java.util.Properties;

public class TestTX {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "s102:9092,s103:9092,s104:9092");
        //设置完事务id之后，幂等性会自动开启
        props.put("transactional.id", "my-transactional-id");
        Producer<String, String> producer = new KafkaProducer<>(props, new StringSerializer(), new StringSerializer());

        producer.initTransactions();

        KafkaConsumer<String, String> consumer = createKafkaConsumer();

        consumer.subscribe(Arrays.asList("t11"));

        int i = 0;
        while (true) {

            producer.beginTransaction();
            try {

                //producer在前？consumer在前
                producer.send(new ProducerRecord<>("t11", i + ""));
                ConsumerRecords<String, String> records = consumer.poll(1000);
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("offset = " + record.offset() + "," + "key = " + record.key() + "," + "value = " + record.value() + ",partition = " + record.partition());
                }

                producer.commitTransaction();
            } catch (ProducerFencedException | OutOfOrderSequenceException | AuthorizationException e) {
                // We can't recover from these exceptions, so our only option is to close the producer and exit.
                producer.close();
            } catch (KafkaException e) {
                // For all other exceptions, just abort the transaction and try again.
                producer.abortTransaction();
            }
            i++;

        }

    }


    private static KafkaConsumer<String, String> createKafkaConsumer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "s102:9092,s103:9092,s104:9092");
        props.put("group.id", "myGroup");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("isolation.level", "read_committed");

        return new KafkaConsumer<String, String>(props);


    }


}
