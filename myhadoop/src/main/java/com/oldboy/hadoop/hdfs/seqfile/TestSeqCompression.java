package com.oldboy.hadoop.hdfs.seqfile;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;

import java.io.BufferedReader;
import java.io.FileReader;

public class TestSeqCompression {

    //文件转换成sequencefile
    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "file:///");


        FileSystem fs = FileSystem.get(conf);
        Path path = new Path("D:/temptags.seq");
        Path path2 = new Path("D:/temptags2.seq");
        Path path3 = new Path("D:/temptags3.seq");


        //构造写入器
        SequenceFile.Writer writer = SequenceFile.createWriter(fs, conf, path, IntWritable.class, Text.class, SequenceFile.CompressionType.NONE);
        SequenceFile.Writer writer2 = SequenceFile.createWriter(fs, conf, path2, IntWritable.class, Text.class, SequenceFile.CompressionType.RECORD);
        SequenceFile.Writer writer3 = SequenceFile.createWriter(fs, conf, path3, IntWritable.class, Text.class, SequenceFile.CompressionType.BLOCK);

        BufferedReader br = new BufferedReader(new FileReader("D:/teaching/temptags.txt"));

        String line = null;

        while ((line = br.readLine()) != null) {

            String[] arr = line.split("\t");

            writer.append(new IntWritable(Integer.parseInt(arr[0])), new Text(arr[1]));
            writer2.append(new IntWritable(Integer.parseInt(arr[0])), new Text(arr[1]));
            writer3.append(new IntWritable(Integer.parseInt(arr[0])), new Text(arr[1]));

        }

        br.close();
        writer.close();
        writer2.close();
        writer3.close();


    }


}
