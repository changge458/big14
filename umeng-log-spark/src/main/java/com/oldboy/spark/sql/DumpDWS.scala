package com.oldboy.spark.sql

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

import scala.io.Source

object DumpDWS {

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


        SparkSQL.runHql("2.regist_function.hql", sc)
        SparkSQL.runHql("4.dws_app_new_user.hql", sc)

    }
}
