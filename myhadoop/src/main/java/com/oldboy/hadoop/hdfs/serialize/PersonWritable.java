package com.oldboy.hadoop.hdfs.serialize;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class PersonWritable implements WritableComparable<PersonWritable> {


    private Person p;

    public int compareTo(PersonWritable o) {

        Person thisP = this.getP();
        Person thatP = o.getP();

        return thisP.getAge() - thatP.getAge();

    }


    //序列化
    public void write(DataOutput out) throws IOException {
        //序列化id
        out.writeInt(p.getId());
        //序列化name
        out.writeUTF(p.getName());
        //序列化age
        out.writeInt(p.getAge());

    }


    //反序列化
    public void readFields(DataInput in) throws IOException {
        p = new Person();
        //反序列化，给person赋值
        p.setId(in.readInt());

        p.setName(in.readUTF());

        p.setAge(in.readInt());
    }


    public PersonWritable() {
    }

    public PersonWritable(Person p) {
        this.p = p;
    }

    public Person getP() {
        return p;
    }

    public void setP(Person p) {
        this.p = p;
    }

}
