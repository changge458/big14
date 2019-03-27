package com.oldboy.test;

import com.oldboy.Emp;
import com.oldboy.Event;
import org.apache.avro.file.CodecFactory;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.nio.ByteBuffer;
import java.util.HashMap;

public class File2Avro {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new FileReader("D:/teaching/1.txt"));

        String line = null;

        //先将java对象处理为avro格式的内存对象
        SpecificDatumWriter<Event> sdw = new SpecificDatumWriter(Event.class);
        //构造写入器
        DataFileWriter<Event> dfw = new DataFileWriter(sdw);

        // dfw.setCodec(CodecFactory.snappyCodec());

        dfw.create(Event.getClassSchema(), new FileOutputStream("D:/teaching/avro/1.avro"));

        while ((line = br.readLine()) != null) {

            Event event = new Event();
            event.setHeaders(null);
            event.setBody(line);
            dfw.append(event);
        }

        dfw.close();
        br.close();
    }


}
