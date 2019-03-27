package com.oldboy.hbase.calllog;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;

public class GenCallerMain {
    public static void main(String[] args) throws Exception {


        Configuration conf = HBaseConfiguration.create();
        //入口点：建立连接
        Connection conn = ConnectionFactory.createConnection(conf);

        Table table = conn.getTable(TableName.valueOf("ns1:calllog"));

        Put put = new Put("13800000001,201901,0,13800000000".getBytes());

        put.addColumn("normal".getBytes(), "caller".getBytes(), "13800000001".getBytes());
        put.addColumn("normal".getBytes(), "callee".getBytes(), "13800000000".getBytes());
        put.addColumn("normal".getBytes(), "time".getBytes(), "2019-1-28 14:57:00".getBytes());
        put.addColumn("normal".getBytes(), "duration".getBytes(), "00:40".getBytes());
        put.addColumn("normal".getBytes(), "status".getBytes(), "ok".getBytes());

        table.put(put);
        conn.close();

    }
}
