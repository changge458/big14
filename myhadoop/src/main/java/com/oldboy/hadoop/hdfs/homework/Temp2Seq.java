package com.oldboy.hadoop.hdfs.homework;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.CompressionInputStream;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.util.ReflectionUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Temp2Seq {

    //将temp文件夹中的所有gzip文件解压
    //并将其内容写入到temp2seq.seq文件中
    public static void main(String[] args) throws Exception {

        //通过文件夹找到所有文件
        File file = new File("D:\\teaching\\temp");

        //列出所有文件
        File[] files = file.listFiles();

        Configuration conf = new Configuration();

        conf.set("fs.defaultFS", "file:///");

        FileSystem fs = FileSystem.get(conf);

        GzipCodec codec = ReflectionUtils.newInstance(GzipCodec.class, conf);

        SequenceFile.Writer writer = SequenceFile.createWriter(fs, conf, new Path("D:/temp2seq.seq"), NullWritable.class, Text.class
                , SequenceFile.CompressionType.BLOCK, codec);

        for (File gzip : files) {
            //获取文件路径，并将其解压
            String path = gzip.getPath();
            //初始化解压输入流
            CompressionInputStream cis = codec.createInputStream(new FileInputStream(gzip));

            //将cis转换为bufferedReader，进行读行技术
            BufferedReader br = new BufferedReader(new InputStreamReader(cis));

            String line = null;
            while ((line = br.readLine()) != null) {
                //获取到line之后，直接写入到sequenceFile中
                writer.append(NullWritable.get(), new Text(line));

            }
            br.close();
            cis.close();


        }

        writer.close();


    }


}
