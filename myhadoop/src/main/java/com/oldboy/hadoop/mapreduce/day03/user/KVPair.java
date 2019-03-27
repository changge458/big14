package com.oldboy.hadoop.mapreduce.day03.user;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class KVPair implements WritableComparable<KVPair> {

    private String pass;
    private int count;


    public int compareTo(KVPair o) {
        if (count == o.count) {
            return pass.compareTo(o.pass);
        } else {
            return o.count - count;
        }
    }

    public void write(DataOutput out) throws IOException {
        out.writeUTF(pass);
        out.writeInt(count);

    }

    public void readFields(DataInput in) throws IOException {
        pass = in.readUTF();
        count = in.readInt();
    }

    @Override
    public String toString() {
        return pass + "\t" + count;
    }

    public KVPair(String pass, int count) {
        this.pass = pass;
        this.count = count;
    }

    public KVPair() {
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
