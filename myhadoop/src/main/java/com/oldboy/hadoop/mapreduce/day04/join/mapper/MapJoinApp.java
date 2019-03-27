package com.oldboy.hadoop.mapreduce.day04.join.mapper;

import com.oldboy.hadoop.mapreduce.day04.full.UserApp;
import com.oldboy.hadoop.mapreduce.day04.full.UserMapper;
import com.oldboy.hadoop.mapreduce.day04.full.UserReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.partition.InputSampler;
import org.apache.hadoop.mapreduce.lib.partition.TotalOrderPartitioner;

public class MapJoinApp {
    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "file:///");

        FileSystem fs = FileSystem.get(conf);

        Job job = Job.getInstance(conf);

        job.setJobName("wordcount");

        job.setJarByClass(MapJoinApp.class);

        job.setMapperClass(MapJoinMapper.class);


        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        FileInputFormat.addInputPath(job, new Path("D:/teaching/join/orders.txt"));

        FileOutputFormat.setOutputPath(job, new Path("D:/teaching/join/out"));

        if (fs.exists(new Path("D:/teaching/join/out"))) {
            fs.delete(new Path("D:/teaching/join/out"), true);
        }



        job.waitForCompletion(true);
    }
}
