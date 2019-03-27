package com.oldboy.hadoop.mapreduce.day05.join.mapper;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MapJoinApp {
    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "file:///");
        conf.set("mapper.file.path",args[2]);

        FileSystem fs = FileSystem.get(conf);

        Job job = Job.getInstance(conf);

        job.setJobName("wordcount");

        job.setJarByClass(MapJoinApp.class);

        job.setMapperClass(MapJoinMapper.class);


        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));

        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        if (fs.exists(new Path(args[1]))) {
            fs.delete(new Path(args[1]), true);
        }



        job.waitForCompletion(true);
    }
}
