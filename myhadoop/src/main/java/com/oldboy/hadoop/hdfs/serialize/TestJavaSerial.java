package com.oldboy.hadoop.hdfs.serialize;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class TestJavaSerial {


    @Test
    public void testSerial() throws Exception {

        Person p = new Person(1, "tom", 20);

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("D:/person"));

        oos.writeObject(p);

        oos.close();

    }

    @Test
    public void testSerial2() throws Exception {

        Integer i = 100;

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("D:/integer"));

        oos.writeObject(i);

        oos.close();

    }

    //通过java反序列化100
    @Test
    public void testDeserial() throws Exception {

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("D:/integer"));

        Object o = ois.readObject();

        System.out.println(o);

    }


}
