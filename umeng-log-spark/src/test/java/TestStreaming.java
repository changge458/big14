import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.*;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestStreaming {

    public static void main(String[] args) throws Exception {

        SparkConf conf = new SparkConf();
        conf.setAppName("kafka");
        conf.setMaster("local[*]");


        //创建java streaming上下文
        JavaStreamingContext ssc = new JavaStreamingContext(new JavaSparkContext(conf), Durations.seconds(2));

        //kafka参数
        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", "s102:9092,s103:9092,s104:9092");
        kafkaParams.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaParams.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaParams.put("auto.offset.reset", "latest");
        kafkaParams.put("group.id", "g1");
        kafkaParams.put("enable.auto.commit", "true");
        kafkaParams.put("auto.commit.interval.ms", "20");

        LocationStrategy strategy = LocationStrategies.PreferConsistent();


        ArrayList<TopicPartition> list = new ArrayList<>();
        list.add(new TopicPartition("test-etl", 0));
        list.add(new TopicPartition("test-etl", 1));
        ConsumerStrategy<String, String> consumerStrategy = ConsumerStrategies.Assign(list, kafkaParams);

        JavaInputDStream<ConsumerRecord<String, String>> dStream = KafkaUtils.createDirectStream(ssc, strategy, consumerStrategy);


        JavaDStream<String> ds1 = dStream.map(new Function<ConsumerRecord<String, String>, String>() {
            @Override
            public String call(ConsumerRecord<String, String> record) throws Exception {
                return record.value();
            }
        });
        //ds1.print();


        JavaDStream<String> ds3 = ds1.window(Durations.minutes(3));


        JavaPairDStream<String, Integer> ds2 = ds3.mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String s) throws Exception {
                return new Tuple2<String, Integer>("key", 1);
            }
        }).reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer i1, Integer i2) throws Exception {
                return i1 + i2;
            }
        });

        ds2.print();

        ssc.start();
        ssc.awaitTermination();
    }
}
