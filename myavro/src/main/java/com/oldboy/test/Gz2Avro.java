package com.oldboy.test;


import com.oldboy.Emp;
import com.oldboy.Event;
import org.apache.avro.file.CodecFactory;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.CompressionInputStream;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.util.ReflectionUtils;

import java.io.*;

public class Gz2Avro {

    public static void main(String[] args) throws Exception {

        //通过文件夹找到所有文件
        File file = new File("D:\\teaching\\temp");

        //列出所有文件
        File[] files = file.listFiles();

        Configuration conf = new Configuration();

        conf.set("fs.defaultFS", "file:///");

        GzipCodec codec = ReflectionUtils.newInstance(GzipCodec.class, conf);

        //先将java对象处理为avro格式的内存对象
        SpecificDatumWriter<com.oldboy.File> sdw = new SpecificDatumWriter(com.oldboy.File.class);
        //构造写入器
        DataFileWriter<com.oldboy.File> dfw = new DataFileWriter(sdw);

        dfw.setCodec(CodecFactory.deflateCodec(-1));
        //创建文件
        dfw.create(com.oldboy.File.getClassSchema(), new FileOutputStream("D:/teaching/avro/gz2avro.avro"));

        long start = System.currentTimeMillis();
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
                com.oldboy.File event = new com.oldboy.File();
                event.setHeaders(null);
                event.setBody(line);
                dfw.append(event);
            }
            br.close();
            cis.close();
        }
        System.out.println(System.currentTimeMillis() - start);

        dfw.close();



    }

}
