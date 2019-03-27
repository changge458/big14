package com.oldboy.hbase.hfile;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class HFileMapper extends Mapper<LongWritable, Text, ImmutableBytesWritable, Cell> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String line = value.toString();

        ImmutableBytesWritable rowKey = new ImmutableBytesWritable(Bytes.toBytes(key.get()));

        Cell cell = CellUtil.createCell(rowKey.get(), "f1".getBytes(), "line".getBytes(), System.currentTimeMillis(), KeyValue.Type.Minimum,
                line.getBytes(), null);

        context.write(rowKey, cell);


    }
}
