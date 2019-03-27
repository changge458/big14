package com.oldboy.hadoop.mapreduce.day05.temptags;

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

public class TemptagsApp2 {

    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "file:///");

        FileSystem fs = FileSystem.get(conf);

        Job job = Job.getInstance(conf);

        job.setJobName("wordcount");

        job.setJarByClass(TemptagsApp2.class);

        job.setMapperClass(TemptagsMapper2.class);
        job.setGroupingComparatorClass(TemptagsComparator.class);
        job.setReducerClass(TemptagsReducer2.class);


        job.setInputFormatClass(KeyValueTextInputFormat.class);

        job.setMapOutputKeyClass(Compkey.class);
        job.setMapOutputValueClass(NullWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);



        FileInputFormat.addInputPath(job, new Path("D:/out"));

        FileOutputFormat.setOutputPath(job, new Path("D:/out2"));

        if (fs.exists(new Path("D:/out2"))) {
            fs.delete(new Path("D:/out2"), true);
        }

        job.setNumReduceTasks(1);

        job.waitForCompletion(true);
    }
}
