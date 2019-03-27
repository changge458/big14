package com.oldboy.spark.sql

import java.net.URL

import org.apache.hadoop.fs.FsUrlStreamHandlerFactory
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

import scala.io.Source

object SparkSQL {

    def runHql(filename: String, sc: SparkSession): Unit = {

        URL.setURLStreamHandlerFactory(new FsUrlStreamHandlerFactory())

        val is = Thread.currentThread().getContextClassLoader.getResourceAsStream(filename)
        val file = Source.fromInputStream(is)

        val str = file.mkString

        val arr = str.split("--.*\r\n")

        for (sql <- arr) {
            if (!sql.trim.isEmpty && !sql.startsWith("--")) {
                sc.sql(sql.replaceAll(";", ""))
            }
        }
    }
}
