package com.oldboy.hadoop;

import com.oldboy.Emp;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class EmpWritable implements Writable {

    Emp emp;


    public void write(DataOutput out) throws IOException {
        out.writeUTF((String) emp.getName());
        out.writeInt(emp.getId());
        out.writeInt(emp.getSalary());
        out.writeInt(emp.getAge());
        out.writeUTF((String) emp.getAddress());
    }

    public void readFields(DataInput in) throws IOException {

        emp = new Emp();

        emp.setName(in.readUTF());
        emp.setId(in.readInt());
        emp.setSalary(in.readInt());
        emp.setAge(in.readInt());
        emp.setAddress(in.readUTF());


    }

    public EmpWritable(Emp emp) {
        this.emp = emp;
    }

    public EmpWritable() {
    }

    public Emp getEmp() {
        return emp;
    }

    public void setEmp(Emp emp) {
        this.emp = emp;
    }
}
