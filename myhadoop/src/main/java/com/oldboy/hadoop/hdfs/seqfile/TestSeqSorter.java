package com.oldboy.hadoop.hdfs.seqfile;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.junit.Test;

public class TestSeqSorter {

    /**
     * SequenceFile排序
     * @throws Exception
     */
    @Test
    public void testSort() throws Exception {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "file:///");

        FileSystem fs = FileSystem.get(conf);
        Path path = new Path("D:/temptags.seq");

        SequenceFile.Sorter sorter = new SequenceFile.Sorter(fs, IntWritable.class, Text.class,conf);

        sorter.sort(path, new Path("D:/temptags_sort.seq"));

    }

    /**
     * SequenceFile融合
     * @throws Exception
     */
    @Test
    public void testMerge() throws Exception {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "file:///");

        FileSystem fs = FileSystem.get(conf);
        Path path1 = new Path("D:/temptags.seq");
        Path path2 = new Path("D:/temptags3.seq");
        Path[] inpaths = {path1,path2};

        SequenceFile.Sorter sorter = new SequenceFile.Sorter(fs, IntWritable.class, Text.class,conf);

        sorter.merge(inpaths, new Path("D:/temptags_merge.seq"));


    }

    @Test
    public void testRead() throws Exception {

        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "file:///");

        FileSystem fs = FileSystem.get(conf);
        Path path = new Path("D:/temptags_sort.seq");

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
