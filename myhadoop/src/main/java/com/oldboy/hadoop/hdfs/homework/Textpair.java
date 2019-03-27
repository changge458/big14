package com.oldboy.hadoop.hdfs.homework;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Textpair implements WritableComparable<Textpair> {

    private String str1;
    private String str2;


    public int compareTo(Textpair o) {

        if (str1.compareTo(o.str1) == 0) {
            return str2.compareTo(o.str2);
        } else {
            return str1.compareTo(o.str1);
        }
    }

    //序列化
    public void write(DataOutput out) throws IOException {
        out.writeUTF(str1);
        out.writeUTF(str2);
    }

    public void readFields(DataInput in) throws IOException {
        str1 = in.readUTF();
        str2 = in.readUTF();

    }


    public Textpair(String str1, String str2) {
        this.str1 = str1;
        this.str2 = str2;
    }

    public String getStr1() {
        return str1;
    }

    public void setStr1(String str1) {
        this.str1 = str1;
    }

    public String getStr2() {
        return str2;
    }

    public void setStr2(String str2) {
        this.str2 = str2;
    }
}
