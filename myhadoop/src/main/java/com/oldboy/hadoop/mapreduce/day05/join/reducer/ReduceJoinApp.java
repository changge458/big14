package com.oldboy.hadoop.mapreduce.day05.join.reducer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class ReduceJoinApp {
    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "file:///");

        FileSystem fs = FileSystem.get(conf);

        Job job = Job.getInstance(conf);

        job.setJobName("wordcount");

        job.setJarByClass(ReduceJoinApp.class);

        job.setMapperClass(ReduceJoinMapper.class);
        job.setGroupingComparatorClass(MyComparator.class);
        job.setReducerClass(ReduceJoinReducer.class);

        job.setMapOutputKeyClass(Compkey.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        FileInputFormat.addInputPath(job, new Path("D:/teaching/join/"));

        FileOutputFormat.setOutputPath(job, new Path("D:/teaching/join/out"));

        if (fs.exists(new Path("D:/teaching/join/out"))) {
            fs.delete(new Path("D:/teaching/join/out"), true);
        }

        job.setNumReduceTasks(1);

        job.waitForCompletion(true);
    }
}
