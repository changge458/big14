package com.oldboy.hadoop.hdfs.codec;

import com.hadoop.compression.lzo.LzopCodec;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.*;
import org.apache.hadoop.util.ReflectionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class TestCodec {

    public static void main(String[] args) {
        Class[] clazzes = {
                DeflateCodec.class,
                GzipCodec.class,
                BZip2Codec.class,
                Lz4Codec.class,
                LzopCodec.class,
                SnappyCodec.class
        };

        for (Class clazz : clazzes) {
            testCompress(clazz, args[0]);
            testDecompress(clazz, args[0]);

        }
    }


    public static void testCompress(Class clazz, String path) {

        try {
            Configuration conf = new Configuration();

            //初始化编解码器
            CompressionCodec codec = (CompressionCodec) ReflectionUtils.newInstance(clazz, conf);

            //获取扩展名
            String ext = codec.getDefaultExtension();

            long start = System.currentTimeMillis();

            FileInputStream fis = new FileInputStream(path);

            File f = new File(path + ext);

            FileOutputStream fos = new FileOutputStream(f);
            CompressionOutputStream cos = codec.createOutputStream(fos);

            IOUtils.copyBytes(fis, cos, 1024);

            System.out.print(ext + "\t" + "压缩时间：" + (System.currentTimeMillis() - start) + "\t大小：" + f.length());

            fis.close();
            cos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void testDecompress(Class clazz, String path) {

        try {
            Configuration conf = new Configuration();

            //初始化编解码器
            CompressionCodec codec = (CompressionCodec) ReflectionUtils.newInstance(clazz, conf);

            //获取扩展名
            String ext = codec.getDefaultExtension();

            long start = System.currentTimeMillis();

            //创建解压输入流
            File f = new File(path + ext);
            FileInputStream fis = new FileInputStream(f);
            CompressionInputStream cis = codec.createInputStream(fis);

            //创建文件输出流
            FileOutputStream fos = new FileOutputStream(f + ".txt");

            IOUtils.copyBytes(cis, fos, 1024);

            System.out.println("\t" + "解压时间：" + (System.currentTimeMillis() - start));

            cis.close();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
