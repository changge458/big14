package com.oldboy.hadoop.mapreduce.day04.second;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Compkey implements WritableComparable<Compkey> {

    private String year;
    private int temp;

    //year相等比较temp
    public int compareTo(Compkey o) {

        if (this.year.equals(o.year)) {
            return o.temp - this.temp;
        }
        return this.year.compareTo(o.year);

    }

    public void write(DataOutput out) throws IOException {
        out.writeUTF(year);
        out.writeInt(temp);
    }

    public void readFields(DataInput in) throws IOException {
        year = in.readUTF();
        temp = in.readInt();

    }

    public Compkey(String year, int temp) {
        this.year = year;
        this.temp = temp;
    }

    public Compkey() {
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    @Override
    public String toString() {
        return "Compkey{" +
                "year='" + year + '\'' +
                ", temp=" + temp +
                '}';
    }

    @Override
    public int hashCode() {
        return year.hashCode();
    }
}
