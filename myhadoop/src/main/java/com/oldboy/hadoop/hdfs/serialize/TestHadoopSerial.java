package com.oldboy.hadoop.hdfs.serialize;

import org.apache.hadoop.io.IntWritable;
import org.junit.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class TestHadoopSerial {

    @Test
    public void testSerial() throws Exception {
        //
        IntWritable iw = new IntWritable(100);

        DataOutputStream dos = new DataOutputStream(new FileOutputStream("D:/integer2"));

        iw.write(dos);

        dos.close();
    }

    /**
     * 使用hadoop反序列化读取100
     */
    @Test
    public void testDeserial() throws Exception {
        //
        IntWritable iw = new IntWritable();

        DataInputStream dis = new DataInputStream(new FileInputStream("D:/integer2"));

        iw.readFields(dis);

        System.out.println(iw.get());

        dis.close();
    }

    //测试person使用hadoop序列化
    @Test
    public void testPersonSerial() throws Exception {

        Person p = new Person(1, "tom", 20);

        //
        PersonWritable pw = new PersonWritable(p);

        DataOutputStream dos = new DataOutputStream(new FileOutputStream("D:/person2"));

        pw.write(dos);

        dos.close();
    }

    //测试person使用hadoop反序列化
    @Test
    public void testPersonDeserial() throws Exception {

        //
        PersonWritable pw = new PersonWritable();

        DataInputStream dis = new DataInputStream(new FileInputStream("D:/person2"));

        pw.readFields(dis);

        Person p = pw.getP();

        System.out.println(p.toString());

        sayHello();
    }


    public void sayHello(){
        System.out.println("hello");
    }

}
