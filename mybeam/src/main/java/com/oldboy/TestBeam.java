package com.oldboy;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.transforms.display.DisplayData;
import org.apache.beam.sdk.transforms.windowing.BoundedWindow;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PDone;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;

public class TestBeam {

    public static void main(String[] args) {

        PipelineOptions pipe = PipelineOptionsFactory.create();

        Pipeline p = Pipeline.create(pipe);

        // 一个根pipline转换，类似于TextIO.Read 或者 Create操作可以通过请求添加到pipline
        PCollection<String> lines = p.apply(TextIO.read().from("file:///D:/teaching/1.txt"));


        PCollection<String> words = lines.apply(ParDo.of(new DoFn<String, String>() {

            @ProcessElement
            public void processElement(ProcessContext context) {

                String[] arr = context.element().split("\\s+");

                for (String word : arr) {
                    context.output(word);
                }
            }
        }));


        PCollection<KV<String, Long>> kv = words.apply(Count.<String>perElement());


        PCollection<String> result = kv.apply("FormatResult", MapElements.via(new SimpleFunction<KV<String, Long>, String>() {
            @Override
            public String apply(KV input) {
                System.out.println(input.getKey() + ":" + input.getValue());
                return input.getKey() + ":" + input.getValue();

            }
        }));

        result.apply(TextIO.write().to("/file/xxx"));

        p.run().waitUntilFinish();


//        // A Pipeline can have multiple root transforms:
//        PCollection<String> moreLines =
//                p.apply(TextIO.read().from("gs://bucket/other/dir/file*.txt"));
//        PCollection<String> yetMoreLines =
//                p.apply(Create.of("yet", "more", "lines").withCoder(StringUtf8Coder.of()));
//
//        // Further PTransforms can be applied, in an arbitrary (acyclic) graph.
//        // Subsequent PTransforms (and intermediate PCollections etc.) are
//        // implicitly part of the same Pipeline.
//        PCollection<String> allLines =
//                PCollectionList.of(lines).and(moreLines).and(yetMoreLines)
//                        .apply(new Flatten<String>());
//        PCollection<KV<String, Integer>> wordCounts =
//                allLines
//                        .apply(ParDo.of(new ExtractWords()))
//                        .apply(new Count<String>());
//        PCollection<String> formattedWordCounts =
//                wordCounts.apply(ParDo.of(new FormatCounts()));
//        formattedWordCounts.apply(TextIO.write().to("gs://bucket/dir/counts.txt"));
//
//        // PTransforms aren't executed when they're applied, rather they're
//        // just added to the Pipeline.  Once the whole Pipeline of PTransforms
//        // is constructed, the Pipeline's PTransforms can be run using a
//        // PipelineRunner.  The default PipelineRunner executes the Pipeline
//        // directly, sequentially, in this one process, which is useful for
//        // unit tests and simple experiments:
//        p.run();


    }


}
