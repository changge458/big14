package com.oldboy

import com.oldboy.PropUtil
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object Generate_full_file {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf()
        conf.setAppName("Generate_full_file")
        conf.setMaster("local[6]")
        val spark = SparkSession.builder().config(conf).getOrCreate()
        val prop = PropUtil.getProp("D:/prop.txt")
        val path = prop.getProperty("rule_file_path")

        val df1=spark.read.format("com.crealytics.spark.excel").option("dataAddress", "'金属打磨'!")
            .option("useHeader", "true")
            .option("treatEmptyValuesAsNulls", "false")
            .option("inferSchema", "false")
            .option("addColorColumns", "False")
            .load(path)
        println(df1.columns)
        df1.show(false)
//        df1.rdd.map()
        spark.close()
    }
}
