package com.oldboy.hbase.assign;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

public class UnassignCopro {
    public static void main(String[] args) throws Exception {

        TableName tableName = TableName.valueOf("ns1:calllog");

        //指定表加载协处理器
        Configuration conf = HBaseConfiguration.create();
        //入口点：建立连接
        Connection conn = ConnectionFactory.createConnection(conf);

        //Table table = conn.getTable(tableName);

        //获取admin
        Admin admin = conn.getAdmin();

        //注册之前首先需要禁用表
        admin.disableTable(tableName);

        //添加协处理器
        HTableDescriptor htd = new HTableDescriptor(tableName);
        htd.addFamily(new HColumnDescriptor("normal".getBytes()));

        //修改表
        admin.modifyTable(tableName, htd);

        //启用表
        admin.enableTable(tableName);

        admin.close();

        conn.close();
    }
}
