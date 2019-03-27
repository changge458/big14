package com.oldboy.hadoop.mapreduce.day05.temptags;

import com.oldboy.hadoop.mapreduce.day05.join.reducer.*;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class TemptagsApp {

    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "file:///");

        FileSystem fs = FileSystem.get(conf);

        Job job = Job.getInstance(conf);

        job.setJobName("wordcount");

        job.setJarByClass(TemptagsApp.class);

        job.setMapperClass(TemptagsMapper.class);
        job.setReducerClass(TemptagsReducer.class);


        job.setInputFormatClass(KeyValueTextInputFormat.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path("D:/temptags.txt"));

        FileOutputFormat.setOutputPath(job, new Path("D:/out"));

        if (fs.exists(new Path("D:/out"))) {
            fs.delete(new Path("D:/out"), true);
        }


        job.setNumReduceTasks(1);

        job.waitForCompletion(true);
    }
}
