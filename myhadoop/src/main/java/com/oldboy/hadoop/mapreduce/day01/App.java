package com.oldboy.hadoop.mapreduce.day01;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class App {

    public static void main(String[] args) throws Exception{

        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","file:///");

        Job job = Job.getInstance(conf);

        job.setJobName("wordcount");

        job.setJarByClass(App.class);

        job.setMapperClass(WCMapper.class);
        //job.setReducerClass(UserReducer.class);
        job.setCombinerClass(WCCombiner.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path("D:/1.txt"));

        FileOutputFormat.setOutputPath(job,new Path("D:/out"));

        job.waitForCompletion(true);
    }
}
