package com.oldboy.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestHadoop2 {
    /**
     * 文件上传
     */
    @Test
    public void testUpload() throws IOException {

        System.setProperty("HADOOP_USER_NAME", "hdfs");

        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://s205:8020/");
        //API入口：FileSystem
        FileSystem fs = FileSystem.get(conf);

        //获得输入流
        FileInputStream fis = new FileInputStream("D:/teaching/wc/1.txt");

        //编写文件输出流
        FSDataOutputStream fos = fs.create(new Path("/user/hive/warehouse/big14.db/employee2/employee.txt"));

        //一气呵成
        IOUtils.copyBytes(fis, fos, 1024);

        fis.close();

        fos.close();

        System.out.println("传输成功");
    }

}
