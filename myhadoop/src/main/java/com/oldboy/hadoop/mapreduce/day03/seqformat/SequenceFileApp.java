package com.oldboy.hadoop.mapreduce.day03.seqformat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

public class SequenceFileApp {

    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "file:///");

        FileSystem fs = FileSystem.get(conf);

        Job job = Job.getInstance(conf);

        job.setJobName("seqFileMaxTemp");

        job.setJarByClass(SequenceFileApp.class);
        job.setMapperClass(SequenceFileMapper.class);
        job.setReducerClass(SequenceFileReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        job.setInputFormatClass(SequenceFileInputFormat.class);
        job.setOutputFormatClass(SequenceFileOutputFormat.class);

        SequenceFileOutputFormat.setOutputCompressionType(job, SequenceFile.CompressionType.BLOCK);
        FileOutputFormat.setOutputCompressorClass(job,GzipCodec.class);
        FileInputFormat.addInputPath(job, new Path("D:/temp2seq.seq"));

        Path outPath = new Path("D:/out");
        FileOutputFormat.setOutputPath(job, outPath);

        if (fs.exists(outPath)) {
            fs.delete(outPath, true);
        }
        job.waitForCompletion(true);
    }

}
