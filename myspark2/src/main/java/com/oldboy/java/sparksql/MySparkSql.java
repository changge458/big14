package com.oldboy.java.sparksql;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class MySparkSql {
    public static void main(String[] args) {
        System.setProperty("user.name", "centos");
        System.setProperty("HADOOP_USER_NAME", "centos");


        SparkSession sess = SparkSession.builder()
                //.config("spark.yarn.jars", "hdfs://mycluster/spark/jars/*")
                .master("spark://s101:7077")
                .appName("app")
                .enableHiveSupport()
                .getOrCreate();

        //Dataset<Row> sql = sess.sql("select count(distinct(cookie)) , month,day, grouping_id()  from big14.uv2 group by month,day with cube order by grouping_id()");

        sess.sql("use big14");
        Dataset<Row> sql = sess.sql("show tables");

        sql.show();

    }
}
