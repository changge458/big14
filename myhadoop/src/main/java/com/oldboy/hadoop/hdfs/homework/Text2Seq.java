package com.oldboy.hadoop.hdfs.homework;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.util.ReflectionUtils;

import java.io.BufferedReader;
import java.io.FileReader;

public class Text2Seq {

    //文件转换成sequencefile
    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "file:///");


        FileSystem fs = FileSystem.get(conf);
        Path path = new Path("D:/temptags.seq2");

        GzipCodec codec = ReflectionUtils.newInstance(GzipCodec.class, conf);

        //构造写入器
        SequenceFile.Writer writer = SequenceFile.createWriter(fs, conf, path, IntWritable.class, Text.class,
                SequenceFile.CompressionType.BLOCK, codec);


        BufferedReader br = new BufferedReader(new FileReader("D:/teaching/temptags.txt"));

        String line = null;

        while ((line = br.readLine()) != null) {

            String[] arr = line.split("\t");

            writer.append(new IntWritable(Integer.parseInt(arr[0])), new Text(arr[1]));

        }

        br.close();
        writer.close();


    }


}
