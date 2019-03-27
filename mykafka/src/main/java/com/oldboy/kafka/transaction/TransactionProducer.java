package com.oldboy.kafka.transaction;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.AuthorizationException;
import org.apache.kafka.common.errors.OutOfOrderSequenceException;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class TransactionProducer {
    public static void main(String[] args) {
        /**
         To use the transactional producer and the attendant APIs, you must set the transactional.id configuration
         property.If the transactional.id is set, idempotence is automatically enabled along with the producer configs
         which idempotence depends on.Further, topics which are included in transactions should be configured for
         durability.In particular, the replication.factor should be at least 3, and the min.insync.replicas for
         these topics should be set to 2. Finally, in order for transactional guarantees to be realized from
         end - to - end, the consumers must be configured to read only committed messages as well.
         The purpose of the transactional.id is to enable transaction recovery across multiple sessions of
         a single producer instance.It would typically be derived from the shard identifier in
         a partitioned, stateful, application.As such, it should be unique to each producer instance running within a
         partitioned application.
         All the new transactional APIs are blocking and will throw exceptions on failure.The example below illustrates
         how the new APIs are meant to be used.It is similar to the example above, except that all 100 messages are
         part of a single transaction. **/


        Properties props = new Properties();
        props.put("bootstrap.servers", "s102:9092,s103:9092,s104:9092");
        //设置完事务id之后，幂等性会自动开启
        props.put("transactional.id", "my-transactional-id");
        Producer<String, String> producer = new KafkaProducer<>(props, new StringSerializer(), new StringSerializer());

        producer.initTransactions();

        try {
            producer.beginTransaction();
            for (int i = 0; i < 100; i++) {
                producer.send(new ProducerRecord<>("t10", Integer.toString(i), Integer.toString(i)));
                //producer.send(new ProducerRecord<>("t11", Integer.toString(i), Integer.toString(i)));
                System.out.println(i);
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            producer.commitTransaction();
        } catch (ProducerFencedException | OutOfOrderSequenceException | AuthorizationException e) {
            // We can't recover from these exceptions, so our only option is to close the producer and exit.
            producer.close();
        } catch (KafkaException e) {
            // For all other exceptions, just abort the transaction and try again.
            producer.abortTransaction();
        }
        producer.close();
    }
}
