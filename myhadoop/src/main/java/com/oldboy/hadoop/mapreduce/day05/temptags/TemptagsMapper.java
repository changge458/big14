package com.oldboy.hadoop.mapreduce.day05.temptags;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.List;

public class TemptagsMapper extends Mapper<Text,Text,Text, IntWritable> {

    @Override
    protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {

        String busId = key.toString();
        String json = value.toString();

        List<String> tags = JsonUtil.parseJson(json);

        if(tags != null){
            for (String tag : tags) {
                context.write(new Text(busId+"\t"+tag), new IntWritable(1));
            }
        }


    }
}
