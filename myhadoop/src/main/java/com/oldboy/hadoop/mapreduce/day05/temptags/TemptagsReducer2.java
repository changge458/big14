package com.oldboy.hadoop.mapreduce.day05.temptags;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class TemptagsReducer2 extends Reducer<Compkey, NullWritable, Text, Text> {

    @Override
    protected void reduce(Compkey key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {

        String busId = key.getBusId();

        StringBuffer sb = new StringBuffer();
        for (NullWritable value : values) {

            String tag = key.getTag();
            int num = key.getNum();

            String tag_num = tag+"_"+num;

            sb.append(tag_num + ",");

        }
        String line = sb.toString();
        String newLine = line.substring(0,line.length()-1);
        sb.setLength(0);

        context.write(new Text(busId),new Text(newLine));


    }
}
