package com.oldboy.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestHadoop {


    //单元测试
    //要求：没有返回值，没有参数

    /**
     * 读取hdfs中的文件
     */
    @Test
    public void testRead() throws IOException {

        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://s205:8020/");
        //API入口：FileSystem
        FileSystem fs = FileSystem.get(conf);

        //获得输入流
        FSDataInputStream fis = fs.open(new Path("/readme.txt"));

        int len = 0;
        byte[] buf = new byte[1024];

        while ((len = fis.read(buf)) != -1) {
            System.out.print(new String(buf, 0, len));
        }
        fis.close();
    }

    /**
     * 文件下载
     */
    @Test
    public void testDownload() throws IOException {

        Configuration conf = new Configuration();
        //API入口：FileSystem
        FileSystem fs = FileSystem.get(conf);

        //获得输入流
        FSDataInputStream fis = fs.open(new Path("/jdk-8u131-linux-x64.tar.gz"));

        //编写文件输出流
        FileOutputStream fos = new FileOutputStream("D:/jdk.tar.gz");

        //一气呵成
        IOUtils.copyBytes(fis, fos, 1024);

        fis.close();

        fos.close();

        System.out.println("传输成功");
    }

    /**
     * 文件上传
     */
    @Test
    public void testUpload() throws IOException {

        System.setProperty("HADOOP_USER_NAME", "hdfs");

        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://s203:8020/");
        //API入口：FileSystem
        FileSystem fs = FileSystem.get(conf);


        //编写文件输出流
        FSDataOutputStream fos = fs.create(new Path("/readme.txt"));

        //一气呵成
        fos.write("helloworld".getBytes());
        fos.close();
        System.out.println("传输成功");
    }


    /**
     * 测试列出文件
     */
    @Test
    public void testFind() throws IOException {

        Configuration conf = new Configuration();

        FileSystem fs = FileSystem.get(conf);

        RemoteIterator<LocatedFileStatus> files = fs.listFiles(new Path("/"), true);

        while (files.hasNext()) {
            LocatedFileStatus status = files.next();
            String name = status.getPath().getName();
            System.out.println(name);
        }

    }

    /**
     * 递归列出文件及文件夹(PATH)
     */

    public static void testFind2(Path path, FileSystem fs) throws IOException {

        //列出指定目录下所有文件（夹）
        FileStatus[] statuses = fs.listStatus(path);

        //通过iter自动生成
        for (FileStatus status : statuses) {
            //通过status判断是否是文件夹
            if (status.isDirectory()) {
                //文件夹，则打印出来文件名
                System.out.println(status.getPath());
                testFind2(status.getPath(), fs);
            }
            //文件，则打印出来文件名
            else {
                System.out.println(status.getPath());
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();

        FileSystem fs = FileSystem.get(conf);

        Path p = new Path("/");

        testFind2(p, fs);

    }

    /**
     * 创建文件夹
     */
    @Test
    public void testMkdir() throws IOException {

        System.setProperty("HADOOP_USER_NAME", "centos");

        Configuration conf = new Configuration();

        FileSystem fs = FileSystem.get(conf);

        fs.mkdirs(new Path("/ccc"));

    }

    /**
     * 删除
     *
     * @throws IOException
     */
    @Test
    public void testDel() throws IOException {

        System.setProperty("HADOOP_USER_NAME", "centos");

        Configuration conf = new Configuration();

        FileSystem fs = FileSystem.get(conf);

        fs.delete(new Path("/a"), true);

    }


}
