package com.oldboy.hadoop;

import com.oldboy.Emp;
import org.junit.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class TestHadoop {

    /**
     * 测试hadoop序列化
     */
    @Test
    public void testSerial() throws Exception {

        DataOutputStream dos = new DataOutputStream(new FileOutputStream("D:/teaching/avro/emp.hadoop"));

        long start = System.currentTimeMillis();
        for (int i = 1; i <= 1000000; i++) {
            Emp emp = new Emp();
            emp.setId(i);
            emp.setName("tom" + i);
            emp.setAge(i % 100);
            emp.setSalary(20000);
            emp.setAddress("changping");

            EmpWritable ew = new EmpWritable(emp);
            //追加数据
            ew.write(dos);
        }
        System.out.println(System.currentTimeMillis() - start);
        dos.close();

    }

    /**
     * 测试hadoop反序列化
     */
    @Test
    public void testDeserial() throws Exception {

        DataInputStream dis = new DataInputStream(new FileInputStream("D:/teaching/avro/emp.hadoop"));


        EmpWritable ew = new EmpWritable();
        long start = System.currentTimeMillis();

        for (int i = 1; i <= 1000000; i++) {
            ew.readFields(dis);

            Emp emp = ew.getEmp();

        }
        System.out.println(System.currentTimeMillis() - start);
        dis.close();

    }


}
