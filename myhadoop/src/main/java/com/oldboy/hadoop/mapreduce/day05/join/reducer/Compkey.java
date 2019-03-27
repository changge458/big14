package com.oldboy.hadoop.mapreduce.day05.join.reducer;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Compkey implements WritableComparable<Compkey> {

    int flag;
    int uid;

    public int compareTo(Compkey o) {
        if (uid == o.uid) {
            return flag - o.flag;
        }
        return uid - o.uid;

    }

    public void write(DataOutput out) throws IOException {
        out.writeInt(flag);
        out.writeInt(uid);

    }

    public void readFields(DataInput in) throws IOException {
        flag = in.readInt();
        uid = in.readInt();
    }

    public Compkey(int flag, int uid) {
        this.flag = flag;
        this.uid = uid;
    }

    public Compkey() {
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "Compkey{" +
                "flag=" + flag +
                ", uid=" + uid +
                '}';
    }

    @Override
    public int hashCode() {
        return uid;
    }
}
