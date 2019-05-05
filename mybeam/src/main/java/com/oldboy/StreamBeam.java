package com.oldboy;

import com.google.common.collect.ImmutableMap;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.kafka.KafkaIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Count;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.Values;
import org.apache.beam.sdk.transforms.windowing.*;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.joda.time.Duration;

import java.util.Arrays;

public class StreamBeam {

    public static void main(String[] args) {


        PipelineOptions options = PipelineOptionsFactory.create();

        Pipeline pipeline = Pipeline.create(options);

        PCollection<KV<String, String>> kafka_kv = pipeline.apply(KafkaIO.<String, String>read()
                .withBootstrapServers("s102:9092,s103:9092,s104:9092")
                .withTopics(Arrays.asList("test-beam"))
                .withKeyDeserializer(StringDeserializer.class)
                .withValueDeserializer(StringDeserializer.class)

                //自定义配置
                .updateConsumerProperties(ImmutableMap.of("group.id", "test-beam-01"))

                // set event times and watermark based on 'LogAppendTime'. To provide a custom
                // policy see withTimestampPolicyFactory(). withProcessingTime() is the default.
                // Use withCreateTime() with topics that have 'CreateTime' timestamps.
                .withLogAppendTime()

                // 设置读已提交
                .withReadCommitted()

                .commitOffsetsInFinalize()

                // 读取完毕之后提交偏移量
                .withCreateTime(Duration.ZERO)

                // finally, if you don't need Kafka metadata, you can drop it.g
                .withoutMetadata()
        );

        PCollection<String> kafka_value = kafka_kv.apply(Values.create()).apply("print", ParDo.of(new DoFn<String, String>() {
            @ProcessElement
            public void processElement(ProcessContext context) {
                String msg = context.element();
                System.out.println(msg);
                context.output(msg);
            }
        }));


        PCollection<KV<String, String>> newKV = kafka_value.apply("consKV", ParDo.of(new DoFn<String, KV<String, String>>() {
            @ProcessElement
            public void processElement(ProcessContext context) {
                String msg = context.element();
                context.output(KV.of("hello", msg));
            }
        }));


        newKV.apply(KafkaIO.<String, String>write()
                        .withBootstrapServers("s102:9092,s103:9092,s104:9092")
                        .withTopic("test-beam-result")
                        .withKeySerializer(StringSerializer.class)
                        .withValueSerializer(StringSerializer.class)
                // Optionally enable exactly-once sink (on supported runners). See JavaDoc for withEOS().
                //.withEOS(3, "eos-sink-group-id")
        );


        PCollection<String> windowed_values = kafka_value.apply(
                Window.<String>into(Sessions.withGapDuration(Duration.millis(20000))));

        PCollection<KV<String, Long>> count = windowed_values.apply(Count.perElement());


        PCollection<String> result = count.apply("print", ParDo.of(new DoFn<KV<String, Long>, String>() {
            @ProcessElement
            public void processElement(ProcessContext context) {
                KV<String, Long> kv = context.element();
                System.out.println(kv.getKey() + ":" + kv.getValue());
                context.output(kv.getKey() + ":" + kv.getValue());
            }
        }));
        pipeline.run().waitUntilFinish();


    }


}
