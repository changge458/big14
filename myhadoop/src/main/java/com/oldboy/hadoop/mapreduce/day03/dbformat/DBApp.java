package com.oldboy.hadoop.mapreduce.day03.dbformat;

import com.oldboy.hadoop.mapreduce.day03.seqformat.SequenceFileApp;
import com.oldboy.hadoop.mapreduce.day03.seqformat.SequenceFileMapper;
import com.oldboy.hadoop.mapreduce.day03.seqformat.SequenceFileReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBInputFormat;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

public class DBApp {

    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "file:///");

        Job job = Job.getInstance(conf);

        job.setJobName("DBMaxTemp");

        job.setJarByClass(DBApp.class);
        job.setMapperClass(DBMapper.class);
        job.setReducerClass(DBReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        //以DB输入
        job.setInputFormatClass(DBInputFormat.class);
        //以DB输出
        job.setOutputFormatClass(DBOutputFormat.class);

        //db指定class，url，name，pass
        DBConfiguration.configureDB(job.getConfiguration(), "com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/big14", "root", "root");

        //db指定输入
        DBInputFormat.setInput(job, MyDBWritable.class,
                "select * from temp",
                "select count(*) from temp");

        //db指定输出
        DBOutputFormat.setOutput(job,"maxtemp", 2);

        job.waitForCompletion(true);
    }


}
