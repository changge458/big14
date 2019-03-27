package com.oldboy.hadoop.mapreduce.day05.homework;

import com.oldboy.hadoop.mapreduce.day04.join.mapper.MapJoinApp;
import com.oldboy.hadoop.mapreduce.day04.join.mapper.MapJoinMapper;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class UVApp {
    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "file:///");

        FileSystem fs = FileSystem.get(conf);

        Job job = Job.getInstance(conf);

        job.setJobName("wordcount");

        job.setJarByClass(UVApp.class);

        job.setMapperClass(UVMapper.class);
        job.setReducerClass(UVReducer.class);


        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        FileInputFormat.addInputPath(job, new Path("D:/teaching/uv.txt"));

        FileOutputFormat.setOutputPath(job, new Path("D:/teaching/out"));

        if (fs.exists(new Path("D:/teaching/out"))) {
            fs.delete(new Path("D:/teaching/out"), true);
        }


        boolean b = job.waitForCompletion(true);

        if (b) {
            Job job2 = Job.getInstance(conf);

            job2.setJobName("wordcount");

            job2.setJarByClass(UVApp.class);

            job2.setMapperClass(UVMapper2.class);
            job2.setReducerClass(UVReducer2.class);


            job2.setOutputKeyClass(Text.class);
            job2.setOutputValueClass(IntWritable.class);

            FileInputFormat.addInputPath(job2, new Path("D:/teaching/out"));

            FileOutputFormat.setOutputPath(job2, new Path("D:/teaching/out2"));

            if (fs.exists(new Path("D:/teaching/out2"))) {
                fs.delete(new Path("D:/teaching/out2"), true);
            }


            job2.waitForCompletion(true);
        }
    }
}
