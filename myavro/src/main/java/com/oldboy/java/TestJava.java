package com.oldboy.java;

import com.oldboy.Emp;
import com.oldboy.hadoop.EmpWritable;
import org.junit.Test;

import java.io.*;

public class TestJava {

     /**
      *  测试hadoop序列化
     */
    @Test
    public void testSerial() throws Exception {

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("D:/teaching/avro/emp.ja"));

        long start = System.currentTimeMillis();
        for (int i = 1; i <= 1000000; i++) {
            Emp emp = new Emp();
            emp.setId(i);
            emp.setName("tom" + i);
            emp.setAge(i % 100);
            emp.setSalary(20000);
            emp.setAddress("changping");

            oos.writeObject(emp);
        }
        System.out.println(System.currentTimeMillis() - start);
        oos.close();

    }

    /**
     * 测试hadoop反序列化
     */
    @Test
    public void testDeserial() throws Exception {

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("D:/teaching/avro/emp.ja"));

        long start = System.currentTimeMillis();
        for (int i = 1; i <= 1000000; i++) {
            Emp emp = (Emp)ois.readObject();
        }
        System.out.println(System.currentTimeMillis() - start);
        ois.close();

    }
}
