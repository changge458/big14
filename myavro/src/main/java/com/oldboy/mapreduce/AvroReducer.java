package com.oldboy.mapreduce;

import com.oldboy.Temp;
import org.apache.avro.mapred.AvroKey;
import org.apache.avro.mapred.AvroValue;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class AvroReducer extends Reducer<Text, IntWritable, AvroKey<String>, AvroValue<Integer>> {

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

        int max = Integer.MIN_VALUE;
        for (IntWritable value : values) {
            max = Math.max(value.get(), max);

        }

        AvroKey<String> outKey = new AvroKey<>(key.toString());
        AvroValue<Integer> outValue = new AvroValue<>(max);

        context.write(outKey,outValue);


    }
}
