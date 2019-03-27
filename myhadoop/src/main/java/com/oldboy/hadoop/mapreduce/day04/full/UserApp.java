package com.oldboy.hadoop.mapreduce.day04.full;

import com.oldboy.hadoop.mapreduce.day01.WCCombiner;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.partition.InputSampler;
import org.apache.hadoop.mapreduce.lib.partition.TotalOrderPartitioner;

public class UserApp {

    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "file:///");

        FileSystem fs = FileSystem.get(conf);

        Job job = Job.getInstance(conf);

        job.setJobName("wordcount");

        job.setJarByClass(UserApp.class);

        job.setMapperClass(UserMapper.class);

        job.setReducerClass(UserReducer.class);

        job.setInputFormatClass(KeyValueTextInputFormat.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job, new Path("D:/out"));

        FileOutputFormat.setOutputPath(job, new Path("D:/out2"));

        if (fs.exists(new Path("D:/out2"))) {
            fs.delete(new Path("D:/out2"), true);
        }

        job.setNumReduceTasks(4);

        //
        job.setPartitionerClass(TotalOrderPartitioner.class);
        //设置全排序分界点的输出路径
        TotalOrderPartitioner.setPartitionFile(job.getConfiguration(), new Path("D:/total.par"));

        //选择采样方式
        //五百万条，选择百分之一即可
        //freq * 所有条数 > 分区数
        //InputSampler.RandomSampler sampler = new InputSampler.RandomSampler(0.01, 200);
        //InputSampler.SplitSampler sampler = new InputSampler.SplitSampler(200);
        InputSampler.IntervalSampler sampler = new InputSampler.IntervalSampler(0.01);

        //开始写入分区数据
        InputSampler.writePartitionFile(job, sampler);

        job.waitForCompletion(true);
    }
}
