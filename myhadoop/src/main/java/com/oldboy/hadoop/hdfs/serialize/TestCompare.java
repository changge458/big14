package com.oldboy.hadoop.hdfs.serialize;

public class TestCompare {


    public static void main(String[] args) {

        Person p1 = new Person(1, "tom", 20);
        Person p2 = new Person(2, "tomas", 40);

        PersonWritable pw1 = new PersonWritable(p1);

        PersonWritable pw2 = new PersonWritable(p2);

        System.out.println(pw1.compareTo(pw2));

    }
}
