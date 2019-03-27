package com.oldboy.spark.sql

import java.util.Properties

import org.apache.spark.SparkConf
import org.apache.spark.sql.{SaveMode, SparkSession}

object DumpAppNewUserToMysql {
    def main(args: Array[String]): Unit = {
        System.setProperty("user.name", "centos")
        System.setProperty("HADOOP_USER_NAME", "centos")

        val conf = new SparkConf()
        conf.set("spark.sql.warehouse.dir", "hdfs://mycluster/user/hive/warehouse")
            .setMaster("local[*]")
            .setAppName("app")


        val sc = SparkSession.builder()
            .config(conf)
            .enableHiveSupport()
            .getOrCreate()


        val df = sc.sql("select * from umeng.app_new_user")

        val url = "jdbc:mysql://localhost:3306/test"
        val table = "app_new_user"

        val prop = new Properties();
        prop.setProperty("user","root")
        prop.setProperty("password","root")

        df.write.mode(SaveMode.Overwrite).jdbc(url,table,prop)

    }


}
