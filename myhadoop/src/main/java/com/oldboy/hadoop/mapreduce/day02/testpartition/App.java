package com.oldboy.hadoop.mapreduce.day02.testpartition;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
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

        FileSystem fs = FileSystem.get(conf);

        Job job = Job.getInstance(conf);

        job.setJobName("wordcount");

        job.setJarByClass(App.class);

        job.setMapperClass(WCMapper.class);
        job.setReducerClass(WCReducer.class);
        job.setPartitionerClass(WCPartitioner.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path("D:/teaching/1.txt"));

        FileOutputFormat.setOutputPath(job,new Path("D:/out"));
        if (fs.exists(new Path("D:/out"))) {
            fs.delete(new Path("D:/out"), true);
        }
        job.setNumReduceTasks(3);

        job.waitForCompletion(true);
    }
}
