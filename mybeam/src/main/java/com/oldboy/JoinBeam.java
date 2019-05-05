package com.oldboy;

import org.apache.beam.runners.direct.DirectOptions;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.io.hdfs.HadoopFileSystemOptions;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.join.CoGbkResult;
import org.apache.beam.sdk.transforms.join.CoGroupByKey;
import org.apache.beam.sdk.transforms.join.KeyedPCollectionTuple;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.TupleTag;
import org.apache.hadoop.conf.Configuration;

import java.util.Arrays;

public class JoinBeam {

    //Flink提交： flink  run -m yarn-cluster  -c com.oldboy.JoinBeam mybeam.jar  hdfs://mycluster/join/users.txt hdfs://mycluster/join/orders.txt hdfs://mycluster/join/out/www
    //Spark提交： spark-submit  --class com.oldboy.JoinBeam --master yarn --deploy-mode cluster  hdfs://mycluster/mybeam.jar hdfs://mycluster/join/users.txt hdfs://mycluster/join/orders.txt hdfs://mycluster/join/out/eee
    //Spark提交：spark-submit  --class com.oldboy.JoinBeam --master spark://s101:7077 --deploy-mode cluster  hdfs://mycluster/mybeam.jar hdfs://mycluster/join/users.txt hdfs://mycluster/join/orders.txt hdfs://mycluster/join/out/rrr

    public static void main(String[] args) {

        HadoopFileSystemOptions options = PipelineOptionsFactory.create().as(HadoopFileSystemOptions.class);
        options.setJobName("join");
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://mycluster");
        conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
        options.setHdfsConfiguration(Arrays.asList(conf));
        Pipeline p = Pipeline.create(options);
        PCollection<String> user_line = p.apply(TextIO.read().from(args[0]));

        PCollection<KV<String, String>> user_kv = user_line.apply("map", ParDo.of(new DoFn<String, KV<String, String>>() {

            @ProcessElement
            public void processElement(ProcessContext context) {

                String line = context.element();
                String userID = line.split("\t")[0];
                context.output(KV.of(userID, line));
            }
        }));


        PCollection<String> order_line = p.apply(TextIO.read().from(args[1]));

        PCollection<KV<String, String>> order_kv = order_line.apply("map", ParDo.of(new DoFn<String, KV<String, String>>() {

            @ProcessElement
            public void processElement(ProcessContext context) {
                String line = context.element();
                String userID = line.split("\t")[3];
                context.output(KV.of(userID, line));
            }
        }));


        final TupleTag<String> userTag = new TupleTag<String>();
        final TupleTag<String> orderTag = new TupleTag<String>();

        final PCollection<KV<String, CoGbkResult>> cogrouppedCollection = KeyedPCollectionTuple
                .of(userTag, user_kv)
                .and(orderTag, order_kv)
                .apply(CoGroupByKey.create());

        final PCollection<KV<String, String>> finalResultCollection = cogrouppedCollection
                .apply("CreateJoinedIdInfoPairs", ParDo.of(new DoFn<KV<String, CoGbkResult>, KV<String, String>>() {
                    @ProcessElement
                    public void processElement(ProcessContext c) {
                        KV<String, CoGbkResult> e = c.element();
                        String id = e.getKey();
                        String userLine = e.getValue().getOnly(userTag);
                        for (String orderLine : c.element().getValue().getAll(orderTag)) {

                            String[] userArr = userLine.split("\t");
                            String name = userArr[1];
                            String age = userArr[2];

                            String[] orderArr = orderLine.split("\t");
                            String orderNo = orderArr[1];
                            String price = orderArr[2];

                            // Generate a string that combines information from both collection values
                            c.output(KV.of(id, name + "\t" + age + "\t" + orderNo + "\t" + price));
                        }
                    }
                }));

        PCollection<String> formattedResults = finalResultCollection
                .apply("FormatFinalResults", ParDo.of(new DoFn<KV<String, String>, String>() {
                    @ProcessElement
                    public void processElement(ProcessContext c) {
                        System.out.println(c.element().getKey() + "\t" + c.element().getValue());
                        c.output(c.element().getKey() + "\t" + c.element().getValue());
                    }
                }));

        formattedResults.apply(TextIO.write().to(args[2]));
        p.run().waitUntilFinish();


    }


}
