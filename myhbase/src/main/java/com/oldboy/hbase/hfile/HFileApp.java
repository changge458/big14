package com.oldboy.hbase.hfile;

import com.oldboy.hbase.mr.wc.WCApp;
import com.oldboy.hbase.mr.wc.WCMapper;
import com.oldboy.hbase.mr.wc.WCReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.HFileOutputFormat2;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

public class HFileApp {

    public static void main(String[] args) throws Exception {

        Configuration conf = HBaseConfiguration.create();
        conf.set("fs.defaultFS", "file:///");

        Job job = Job.getInstance(conf);

        job.setJobName("Hbase job");

        job.setJarByClass(HFileApp.class);

        job.setMapperClass(HFileMapper.class);


        //使用TableInputFormat
        FileInputFormat.addInputPath(job, new Path("D:/teaching/temp/"));
        job.setOutputFormatClass(HFileOutputFormat2.class);
        HFileOutputFormat2.setOutputPath(job, new Path("D:/temp"));

        //map的输出k-v
        job.setOutputKeyClass(ImmutableBytesWritable.class);
        job.setOutputValueClass(Cell.class);

        Connection conn = ConnectionFactory.createConnection(job.getConfiguration());
        //指定表进行生成,然后设置增量导入
        HFileOutputFormat2.configureIncrementalLoad(job, conn.getTable(TableName.valueOf("ns1:temp")),
                conn.getRegionLocator(TableName.valueOf("ns1:temp")));


        boolean b = job.waitForCompletion(true);





    }


}
