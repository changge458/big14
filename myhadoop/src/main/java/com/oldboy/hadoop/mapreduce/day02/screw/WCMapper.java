package com.oldboy.hadoop.mapreduce.day02.screw;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.Random;

public class WCMapper extends Mapper<LongWritable, Text,Text, IntWritable> {

    //通过setup获取context数据
    //通过context获取reduce个数
    int numReduceTasks;
    Random r;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        numReduceTasks = context.getNumReduceTasks();
        r = new Random();
    }

    //向每一个key添加随机后缀[0- (num-1)]
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String line = value.toString();

        String[] arr = line.split(" ");

        for (String word : arr) {
            //取得随机后缀
            int prefix = r.nextInt(numReduceTasks);
            String newWord = word+"_"+prefix;
            context.write(new Text(newWord), new IntWritable(1));
        }

    }
}
