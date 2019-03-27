package com.oldboy.hadoop.hdfs.seqfile;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.junit.Test;

public class TestSeqReader {


    @Test
    public void testRead() throws Exception {

        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "file:///");

        FileSystem fs = FileSystem.get(conf);
        Path path = new Path("D:/2.seq");

        //构造阅读器
        SequenceFile.Reader reader = new SequenceFile.Reader(fs, path, conf);

        //将指针定位到0处顺序读取
        //reader.seek(0);
        reader.sync(85);

        //开始读取数据
        IntWritable key = new IntWritable();
        Text val = new Text();

        while (reader.next(key, val)) {
            System.out.println(key + "/" + val);

        }
    }

    @Test
    public void testGetPosition() throws Exception {

        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "file:///");

        FileSystem fs = FileSystem.get(conf);
        Path path = new Path("D:/2.seq");

        //构造阅读器
        SequenceFile.Reader reader = new SequenceFile.Reader(fs, path, conf);

        //开始读取数据
        IntWritable key = new IntWritable();
        Text val = new Text();

        while (reader.next(key, val)) {
            System.out.println(key + "/" + val + "/" + reader.getPosition());

        }
    }

    @Test
    public void testSyncPosition() throws Exception {

        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "file:///");

        FileSystem fs = FileSystem.get(conf);
        Path path = new Path("D:/4.seq");

        //构造阅读器
        SequenceFile.Reader reader = new SequenceFile.Reader(fs, path, conf);

        //开始读取数据
        IntWritable key = new IntWritable();
        Text val = new Text();


        long pos = 0;
        while (true) {
            //查找第一个同步点
            reader.sync(pos);
            //得到同步点位置
            pos = reader.getPosition();
            //打印同步点位置
            System.out.println(pos);
            //移动指针到下一条数据
            reader.next(key, val);
            //得到当前位置
            pos = reader.getPosition();
            if (!reader.next(key, val)) {
                break;
            }
        }


    }
}
