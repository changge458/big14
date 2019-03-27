package com.oldboy.hadoop.mapreduce.day04.second;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class SecondApp {

    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();

        conf.set("fs.defaultFS", "file:///");

        FileSystem fs = FileSystem.get(conf);

        Job job = Job.getInstance(conf);

        job.setJobName("secondary sort");

        job.setJarByClass(SecondApp.class);

        job.setMapperClass(SecondMapper.class);
        job.setPartitionerClass(MyPartition.class);
        job.setGroupingComparatorClass(MyGroupingComparator.class);

        job.setReducerClass(SecondReducer.class);

        job.setMapOutputKeyClass(Compkey.class);
        job.setMapOutputValueClass(NullWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path("D:/teaching/temp2"));

        Path outPath = new Path("D:/out");

        FileOutputFormat.setOutputPath(job, outPath);

        if (fs.exists(outPath)) {
            fs.delete(outPath, true);
        }
        job.setNumReduceTasks(2);

        job.waitForCompletion(true);

    }
}
