package com.oldboy.hadoop.mapreduce.day05.join.reducer;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

public class ReduceJoinMapper extends Mapper<LongWritable, Text ,Compkey, Text> {

    Path path;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        //通过context来获取切片路径
        path = ((FileSplit) context.getInputSplit()).getPath();
    }

    //在map端获取文件名
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        int flag;
        String line = value.toString();

        if(path.getName().startsWith("users")){
            //user数据
            flag= 0;
            int uid = Integer.parseInt(line.split("\t")[0]);
            Compkey ck = new Compkey(flag, uid);
            context.write(ck,new Text(line));

        }
        else {
            //order数据
            flag = 1;
            int uid = Integer.parseInt(line.split("\t")[3]);
            Compkey ck = new Compkey(flag, uid);
            context.write(ck,new Text(line));
        }



    }
}
