package com.oldboy.hbase.mr.wc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;

public class WCApp {

    public static void main(String[] args) throws Exception {

        Configuration conf = HBaseConfiguration.create();
        conf.set(TableInputFormat.INPUT_TABLE,"ns1:word");
        conf.set(TableOutputFormat.OUTPUT_TABLE,"ns1:wc");

        Job job = Job.getInstance(conf);

        job.setJobName("Hbase job");

        job.setJarByClass(WCApp.class);

        job.setMapperClass(WCMapper.class);

        job.setReducerClass(WCReducer.class);

        //使用TableInputFormat
        job.setInputFormatClass(TableInputFormat.class);
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
