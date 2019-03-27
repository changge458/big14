package com.oldboy.flink.streaming;

import com.oldboy.dao.UserVisitDao;
import com.oldboy.dao.UserVisitDao2;
import com.oldboy.domain.StartupLog;
import com.oldboy.domain.UserVisit;
import com.oldboy.umeng.constant.Constants;
import com.oldboy.umeng.util.ParseKeyUtil;
import com.oldboy.umeng.util.PropUtil;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.util.Collector;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ParseStartupFlink {
    public static void main(String[] args) throws Exception {

        args = new String[]{"--input-topic", "test-etl", "--bootstrap.servers", "s102:9092,s103:9092,s104:9092",
                "--group.id", "test"};

        // parse input arguments
        final ParameterTool parameterTool = ParameterTool.fromArgs(args);

        if (parameterTool.getNumberOfParameters() < 3) {
            System.out.println("Missing parameters!\n" +
                    "Usage: --input-topic <topic> " +
                    "--bootstrap.servers <kafka brokers> " +
                    "--group.id <some id>");
            return;
        }

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //禁止输出系统日志
        env.getConfig().disableSysoutLogging();
        //flink流在失败后，会自动重试
        //第一个参数说明，每隔4秒重试一次
        //第二个参数，总共重试多少次
        env.getConfig().setRestartStrategy(RestartStrategies.fixedDelayRestart(4, 10000));
        env.enableCheckpointing(5000); // create a checkpoint every 5 seconds
        env.getConfig().setGlobalJobParameters(parameterTool); // make parameters available in the web interface
        //设定水印时间戳
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        String topic = parameterTool.get("input-topic");


        //正式数据抽取
        FlinkKafkaConsumer011<String> kafkaConsumer = new FlinkKafkaConsumer011<>(topic, new SimpleStringSchema(), parameterTool.getProperties());
        DataStreamSource<String> ds1 = env.addSource(kafkaConsumer.assignTimestampsAndWatermarks(new KafkaMsgWatermarks()));

        DataStream<StartupLog> ds2 = ds1.flatMap(new FlatMapFunction<String, StartupLog>() {
            @Override
            //out是一个集合，每次生成泛型的对象，需要out.collect进行收集
            public void flatMap(String line, Collector<StartupLog> out) throws Exception {

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
                    out.collect(log);
                }
            }
        });

        ds2.map(new MapFunction<StartupLog, UserVisit>() {
            @Override
            public UserVisit map(StartupLog startupLog) throws Exception {
                return UserVisitDao2.getUserVisit(startupLog);
            }
        });

        //ds2.print();


        env.execute("Kafka 0.10 Example");
    }
}
