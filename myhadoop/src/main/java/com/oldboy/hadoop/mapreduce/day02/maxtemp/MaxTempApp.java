package com.oldboy.hadoop.mapreduce.day02.maxtemp;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MaxTempApp {

    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "file:///");

        FileSystem fs = FileSystem.get(conf);

        //通过conf初始化job
        Job job = Job.getInstance(conf);

        //给job起名
        job.setJobName("maxTemp");

        //入口函数所在的类
        job.setJarByClass(MaxTempApp.class);

        //mapper类
        job.setMapperClass(MaxTempMapper.class);
        //reducer类
        job.setReducerClass(MaxTempReducer.class);

        //map的输出k-v类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        //reduce的输出k-v类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        //输入路径，可以指定文件，可以指定目录
        FileInputFormat.addInputPath(job, new Path("D:/teaching/temp"));

        //输出路径，必须是目录
        FileOutputFormat.setOutputPath(job, new Path("D:/out"));

        //如果路径存在则删除
        if (fs.exists(new Path("D:/out"))) {
            fs.delete(new Path("D:/out"), true);
        }

        job.setNumReduceTasks(5);
        //开始执行
        job.waitForCompletion(true);
    }
}
