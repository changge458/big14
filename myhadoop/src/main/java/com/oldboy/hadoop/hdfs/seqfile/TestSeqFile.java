package com.oldboy.hadoop.hdfs.seqfile;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.junit.Test;

public class TestSeqFile {

    //创建seqFile
    @Test
    public void testCreate() throws Exception {

        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "file:///");

        FileSystem fs = FileSystem.get(conf);
        Path path = new Path("D:/4.seq");

        //构造写入器
        SequenceFile.Writer writer = SequenceFile.createWriter(fs, conf, path, IntWritable.class, Text.class, SequenceFile.CompressionType.BLOCK);

        //开始写入数据
        for (int i = 1; i <= 1000000; i++) {
            writer.append(new IntWritable(i), new Text("tom" + i));
            //writer.sync();

        }

        writer.close();
    }

    @Test
    public void testRead() throws Exception {

        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "file:///");

        FileSystem fs = FileSystem.get(conf);
        Path path = new Path("D:/1.seq");

        //构造阅读器
        SequenceFile.Reader reader = new SequenceFile.Reader(fs, path, conf);

        //开始读取数据
        IntWritable key = new IntWritable();
        Text val = new Text();

        while (reader.next(key, val)) {
            System.out.println(key + "/" + val);

        }
    }


}
