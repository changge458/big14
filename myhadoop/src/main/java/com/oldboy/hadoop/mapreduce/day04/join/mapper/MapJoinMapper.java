package com.oldboy.hadoop.mapreduce.day04.join.mapper;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MapJoinMapper extends Mapper<LongWritable, Text, Text, NullWritable> {

    Map<String, String> map = new HashMap<String, String>();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {

        BufferedReader br = new BufferedReader(new FileReader("D:/teaching/join/users.txt"));
        String line = null;
        while ((line = br.readLine()) != null) {
            String id = line.split("\t")[0];
            map.put(id, line);
        }
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //一行order数据
        String line = value.toString();

        String[] orderArr = line.split("\t");

        String uid = orderArr[3];
        String orderno = orderArr[1];
        String oprice = orderArr[2];

        String userLine = map.get(uid);

        context.write(new Text(userLine + "\t" + orderno + "\t" + oprice) , NullWritable.get() );

    }


}
