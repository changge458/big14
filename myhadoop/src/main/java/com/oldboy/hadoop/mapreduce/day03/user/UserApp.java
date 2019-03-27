package com.oldboy.hadoop.mapreduce.day03.user;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class UserApp {
    public static void main(String[] args) throws Exception{

        Configuration conf = new Configuration();
        //conf.set("fs.defaultFS","file:///");
        FileSystem fs = FileSystem.get(conf);

        Job job = Job.getInstance(conf);

        job.setJobName("wordcount");

        job.setJarByClass(UserApp.class);

        job.setMapperClass(UserMapper.class);
        job.setReducerClass(UserReducer.class);
        job.setCombinerClass(UserReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));

        Path outPath = new Path(args[1]);
        FileOutputFormat.setOutputPath(job, outPath);

        if (fs.exists(outPath)) {
            fs.delete(outPath, true);
        }
        job.setNumReduceTasks(1);
        boolean b = job.waitForCompletion(true);

        if(b){
            Job job2 = Job.getInstance(conf);

            job2.setJobName("wordcount");

            job2.setJarByClass(UserApp.class);

            job2.setMapperClass(UserMapper2.class);
            job2.setReducerClass(UserReducer2.class);


            job2.setInputFormatClass(KeyValueTextInputFormat.class);

            job2.setMapOutputKeyClass(KVPair.class);
            job2.setMapOutputValueClass(NullWritable.class);
            job2.setOutputKeyClass(DuowanWritable.class);
            job2.setOutputValueClass(NullWritable.class);

            FileInputFormat.addInputPath(job2, new Path(args[1]));

            job2.setOutputFormatClass(DBOutputFormat.class);
            //db指定class，url，name，pass
            DBConfiguration.configureDB(job2.getConfiguration(), "com.mysql.jdbc.Driver",
                    "jdbc:mysql://s101:3306/big14", "root", "");

            //db指定输出
            DBOutputFormat.setOutput(job2,"top100", 2);

            job2.waitForCompletion(true);
        }
    }
}
