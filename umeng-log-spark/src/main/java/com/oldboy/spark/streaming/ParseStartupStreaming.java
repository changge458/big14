package com.oldboy.spark.streaming;

import com.oldboy.dao.UserVisitDao;
import com.oldboy.domain.StartupLog;
import com.oldboy.domain.UserVisit;
import com.oldboy.umeng.constant.Constants;
import com.oldboy.umeng.util.ParseKeyUtil;
import com.oldboy.umeng.util.PropUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.*;

import java.lang.reflect.Field;
import java.util.*;

public class ParseStartupStreaming {

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

        JavaDStream<StartupLog> ds2 = ds1.flatMap(new FlatMapFunction<String, StartupLog>() {
            @Override
            public Iterator<StartupLog> call(String line) throws Exception {

                List<StartupLog> startupLogList = new ArrayList<>();

                String tablename = PropUtil.getValue(Constants.STARTUP_TABLE_NAME);
                List<Object[]> list = ParseKeyUtil.parseKey(line, tablename);
                for (Object[] objs : list) {
                    Class clazz = StartupLog.class;
                    Field[] fields = clazz.getDeclaredFields();
                    StartupLog log = (StartupLog) clazz.newInstance();
                    for (int i = 0; i < fields.length; i++) {
                        fields[i].setAccessible(true);
                        fields[i].set(log, objs[i]);
                    }
                    startupLogList.add(log);
                }
                return startupLogList.iterator();
            }
        });

        //将ds2中的数据放到hbase中，首先需要查找hbase数据

        JavaDStream<UserVisit> ds3 = ds2.map(new Function<StartupLog, UserVisit>() {
            @Override
            public UserVisit call(StartupLog startupLog) throws Exception {
                UserVisit userVisit = UserVisitDao.getUserVisit(startupLog);

                return userVisit;
            }
        });

        ds3.window(Durations.minutes(2)).count().print();


        ssc.start();
        ssc.awaitTermination();
    }
}
