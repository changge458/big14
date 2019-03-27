package com.oldboy.mapreduce;

import com.oldboy.File;
import com.oldboy.Temp;
import org.apache.avro.Schema;
import org.apache.avro.mapreduce.AvroJob;
import org.apache.avro.mapreduce.AvroKeyInputFormat;
import org.apache.avro.mapreduce.AvroKeyOutputFormat;
import org.apache.avro.mapreduce.AvroKeyValueOutputFormat;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class AvroApp {

    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "file:///");

        FileSystem fs = FileSystem.get(conf);

        //通过conf初始化job
        Job job = Job.getInstance(conf);

        //给job起名
        job.setJobName("maxTemp");

        //入口函数所在的类
        job.setJarByClass(AvroApp.class);

        //mapper类
        job.setMapperClass(AvroMapper.class);
        //reducer类
        job.setReducerClass(AvroReducer.class);

        //输入格式
        job.setInputFormatClass(AvroKeyInputFormat.class);
        //构造schema
        Schema schema = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"File\",\"namespace\":\"com.oldboy\",\"fields\":[{\"name\":\"headers\",\"type\":\"null\"},{\"name\":\"body\",\"type\":\"string\"}]}");
        //输入key，value为kong
        AvroJob.setInputKeySchema(job,schema);

        //map的输出k-v类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        //reduce输出类型
        job.setOutputFormatClass(AvroKeyValueOutputFormat.class);
        AvroJob.setOutputKeySchema(job, Schema.create(Schema.Type.STRING));
        AvroJob.setOutputValueSchema(job, Schema.create(Schema.Type.INT));


        //输入路径，可以指定文件，可以指定目录
        FileInputFormat.addInputPath(job, new Path("D:/teaching/avro/gz2avro.avro"));

        //输出路径，必须是目录
        FileOutputFormat.setOutputPath(job, new Path("D:/out"));

        //如果路径存在则删除
        if (fs.exists(new Path("D:/out"))) {
            fs.delete(new Path("D:/out"), true);
        }

        job.setNumReduceTasks(1);
        //开始执行
        job.waitForCompletion(true);
    }
}
