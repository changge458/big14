package com.oldboy.hadoop.mapreduce.day03.dbformat;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyDBWritable implements Writable, DBWritable {

    private String line;

    //
    public void write(DataOutput out) throws IOException {
        out.writeUTF(line);
    }

    public void readFields(DataInput in) throws IOException {
        line = in.readUTF();

    }

    public void write(PreparedStatement ppst) throws SQLException {
        ppst.setString(1, line);

    }

    public void readFields(ResultSet rs) throws SQLException {
        line = rs.getString(1);

    }

    public MyDBWritable() {
    }

    public MyDBWritable(String line) {
        this.line = line;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }
}
