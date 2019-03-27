package com.oldboy.hbase.mr.maxtemp;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

public class MaxTempApp {

    public static void main(String[] args) throws Exception {

        Configuration conf = HBaseConfiguration.create();
        //由于输入是本地文件，所以应选择本地文件系统
        conf.set("fs.defaultFS", "file:///");
        conf.set(TableOutputFormat.OUTPUT_TABLE, "ns1:maxtemp");

        Job job = Job.getInstance(conf);

        job.setJobName("Hbase job");

        job.setJarByClass(MaxTempApp.class);

        job.setMapperClass(MaxTempMapper.class);

        job.setReducerClass(MaxTempReducer.class);

        //使用TableInputFormat
        FileInputFormat.addInputPath(job,new Path("D:/teaching/temp"));
        job.setOutputFormatClass(TableOutputFormat.class);

        //map的输出k-v
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        //reduce的输出k-v
        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Put.class);

        job.waitForCompletion(true);

    }
}
