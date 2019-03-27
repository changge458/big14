package com.oldboy.hadoop.mapreduce.day03.user;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DuowanWritable implements Writable, DBWritable {

    private String pass;
    private int count;

    //
    public void write(DataOutput out) throws IOException {
        out.writeUTF(pass);
        out.writeInt(count);
    }

    public void readFields(DataInput in) throws IOException {
        pass = in.readUTF();
        count = in.readInt();

    }

    public void write(PreparedStatement ppst) throws SQLException {
        ppst.setString(1, pass);
        ppst.setInt(2, count);

    }

    public void readFields(ResultSet rs) throws SQLException {
        pass = rs.getString(1);
        count = rs.getInt(2);

    }

    public DuowanWritable() {
    }

    public DuowanWritable(String pass, int count) {
        this.pass = pass;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
