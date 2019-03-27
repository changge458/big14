package com.oldboy.hadoop.mapreduce.day03.dbformat;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyDBWritable2 implements Writable, DBWritable {

    private String year;
    private int temp;

    //
    public void write(DataOutput out) throws IOException {
        out.writeUTF(year);
        out.writeInt(temp);
    }

    public void readFields(DataInput in) throws IOException {
        year = in.readUTF();
        temp = in.readInt();

    }

    public void write(PreparedStatement ppst) throws SQLException {
        ppst.setString(1, year);
        ppst.setInt(2, temp);

    }

    public void readFields(ResultSet rs) throws SQLException {
        year = rs.getString(1);
        temp = rs.getInt(2);

    }

    public MyDBWritable2() {
    }

    public MyDBWritable2(String year, int temp) {
        this.year = year;
        this.temp = temp;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
