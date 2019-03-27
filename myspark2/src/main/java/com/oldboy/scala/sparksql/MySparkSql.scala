package com.oldboy.scala.sparksql

import java.util

import org.apache.spark.SparkConf
import org.apache.spark.sql.types._
import org.apache.spark.sql.{Row, SparkSession}

object MySparkSql extends Serializable {

    def main(args: Array[String]): Unit = {
        System.setProperty("user.name", "centos");
        System.setProperty("HADOOP_USER_NAME", "centos");

        val conf = new SparkConf()
        conf.set("spark.yarn.jars", "hdfs://mycluster/spark/jars/*")
        conf.setMaster("spark://s101:7077")
            .setAppName("app")

        val sess = SparkSession.builder()
            .config(conf)
            .enableHiveSupport()
            .getOrCreate()

        import scala.collection.JavaConversions._
        import sess.implicits._
        val list: util.List[Row] = List(
            Row(1, "tom", 20),
            Row(2, "tomas", 40),
            Row(3, "tomson", 60),
            Row(4, "tomason", 80),
            Row(5, "tomasLee", 90)
        )

        val schema = StructType(List[StructField](
            StructField("id", IntegerType, false),
            StructField("name", StringType, true),
            StructField("age", IntegerType, true))
        )
        val df1 = sess.createDataFrame(list, schema)
        df1.createOrReplaceTempView("_user")
        //df1.show()


        val df2 = sess
            .read
            .option("header", "false") //处理csv文件首行问题
            .option("delimiter", "\t") //处理分隔符问题，默认是 ","
            .option("charset", "UTF-8")
            .csv("/orders.txt")
            .toDF("oid", "orderno", "oprice", "uid")

        df2.createTempView("_order")

        val df = sess.sql("select a.id, a.name, a.age,b.orderno,b.oprice from `_user` a left join `_order` b on a.id = b.uid")
        df.show()
    }
}
