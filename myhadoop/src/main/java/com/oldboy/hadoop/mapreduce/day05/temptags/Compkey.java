package com.oldboy.hadoop.mapreduce.day05.temptags;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Compkey implements WritableComparable<Compkey> {

    String busId;
    String tag;
    int num;

    public int compareTo(Compkey o) {
        if ((busId).equals(o.busId)) {
            return o.num - num;
        } else {
            return (busId).compareTo(o.busId);
        }
    }

    public void write(DataOutput out) throws IOException {
        out.writeUTF(busId);
        out.writeUTF(tag);
        out.writeInt(num);

    }

    public void readFields(DataInput in) throws IOException {
        busId = in.readUTF();
        tag = in.readUTF();
        num = in.readInt();
    }

    public Compkey(String busId, String tag, int num) {
        this.busId = busId;
        this.tag = tag;
        this.num = num;
    }

    public Compkey() {
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "Compkey{" +
                "busId='" + busId + '\'' +
                ", tag='" + tag + '\'' +
                ", num=" + num +
                '}';
    }
}
