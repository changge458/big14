package com.oldboy.hadoop.mapreduce.day03.screw2;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WCApp {
    public static void main(String[] args) throws Exception{

        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","file:///");

        FileSystem fs = FileSystem.get(conf);

        Job job = Job.getInstance(conf);

        job.setJobName("wordcount");

        job.setJarByClass(WCApp.class);

        job.setMapperClass(WCMapper.class);
        job.setReducerClass(WCReducer.class);
        job.setPartitionerClass(RandomPartition.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path("D:/teaching/1.txt"));

        FileOutputFormat.setOutputPath(job,new Path("D:/out"));

        //如果路径存在则删除
        if (fs.exists(new Path("D:/out"))) {
            fs.delete(new Path("D:/out"), true);
        }
        job.setNumReduceTasks(3);

        boolean b = job.waitForCompletion(true);

        if(b){
            Job job2 = Job.getInstance(conf);

            job2.setJobName("wordcount2");

            job2.setJarByClass(WCApp.class);

            job2.setMapperClass(WCMapper2.class);
            job2.setReducerClass(WCReducer.class);

            job2.setOutputKeyClass(Text.class);
            job2.setOutputValueClass(IntWritable.class);

            job2.setInputFormatClass(KeyValueTextInputFormat.class);

            FileInputFormat.addInputPath(job2, new Path("D:/out"));

            FileOutputFormat.setOutputPath(job2,new Path("D:/out2"));

            //如果路径存在则删除
            if (fs.exists(new Path("D:/out2"))) {
                fs.delete(new Path("D:/out2"), true);
            }

            job2.setNumReduceTasks(1);

            job2.waitForCompletion(true);

        }

    }
}
