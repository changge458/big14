package com.oldboy.avro;

import com.oldboy.Emp;
import org.apache.avro.file.CodecFactory;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;

public class TestAvro {

    //序列化
    @Test
    public void testSerial() throws Exception{

        //先将java对象处理为avro格式的内存对象
        SpecificDatumWriter<Emp> sdw = new SpecificDatumWriter(Emp.class);
        //构造写入器
        DataFileWriter<Emp> dfw = new DataFileWriter(sdw);

        dfw.setCodec(CodecFactory.snappyCodec());
        //创建文件
        dfw.create(Emp.getClassSchema(), new FileOutputStream("D:/teaching/avro/emp.avro"));

        long start = System.currentTimeMillis();

        for(int i = 1; i<= 1000000; i++){
            Emp emp = new Emp();
            emp.setId(i);
            emp.setName("tom" + i);
            emp.setAge(i % 100);
            emp.setSalary(20000);
            emp.setAddress("changping");
            //追加数据
            dfw.append(emp);
        }

        System.out.println(System.currentTimeMillis() - start);

        //关闭写入器
        dfw.close();

    }

    //反序列化
    @Test
    public void testDeserial() throws Exception{

        SpecificDatumReader<Emp> sdr = new SpecificDatumReader(Emp.class);
        DataFileReader<Emp> dfw = new DataFileReader(new File("D:/teaching/avro/emp.avro"),sdr);
        long start = System.currentTimeMillis();
        while(dfw.hasNext()){
            Emp emp = dfw.next();
            //System.out.println(emp.toString());
        }
        System.out.println(System.currentTimeMillis() - start);
        dfw.close();

    }

}
