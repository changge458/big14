package com.oldboy;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.io.hdfs.HadoopFileSystemOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.hadoop.conf.Configuration;

import java.util.Arrays;

public class WordCountBeam {

    public static void main(String[] args) throws Exception {


        //spark提交： spark-submit  --class com.oldboy.WordCountBeam --master yarn --deploy-mode cluster  hdfs://mycluster/mybeam.jar hdfs://mycluster/readme.txt hdfs://mycluster/xxx/yyy
        //spark提交： spark-submit  --class com.oldboy.WordCountBeam --master spark://s101:7077 --deploy-mode cluster  hdfs://mycluster/mybeam.jar hdfs://mycluster/readme.txt hdfs://mycluster/xxx/yyy
        //flink提交： flink  run -m yarn-cluster  -c com.oldboy.WordCountBeam mybeam.jar  hdfs://mycluster/readme.txt hdfs://mycluster/xxx/yyy


        HadoopFileSystemOptions options = PipelineOptionsFactory.create().as(HadoopFileSystemOptions.class);
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://mycluster");
        conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
        options.setHdfsConfiguration(Arrays.asList(conf));
        Pipeline p = Pipeline.create(options);
        PCollection<String> lines = p.apply("ReadLines", TextIO.read().from(args[0]));

        //将line转换为word,并标1成对
        PCollection<KV<String, Integer>> word_1 = lines.apply("extract words and mapping", ParDo.of(new DoFn<String, KV<String, Integer>>() {
            @ProcessElement
            public void processElement(ProcessContext context) {
                String line = context.element();
                for (String word : line.split("\\s+")) {
                    context.output(KV.of(word, 1));
                }
            }
        }));

        PCollection<KV<String, Long>> word_count = word_1.apply(Count.perKey());

        PCollection<String> result = word_count.apply("print", ParDo.of(new DoFn<KV<String, Long>, String>() {
            @ProcessElement
            public void processElement(ProcessContext context) {
                KV<String, Long> kv = context.element();
                System.out.println(kv.getKey() + ":" + kv.getValue());
                context.output(kv.getKey() + ":" + kv.getValue());
            }
        }));

        result.apply(TextIO.write().to(args[1]));
        p.run().waitUntilFinish();
    }
}
