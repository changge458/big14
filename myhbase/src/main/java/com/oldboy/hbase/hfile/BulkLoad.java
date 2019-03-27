package com.oldboy.hbase.hfile;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.mapreduce.LoadIncrementalHFiles;

public class BulkLoad {

    public static void main(String[] args) throws Exception {

        Configuration conf = HBaseConfiguration.create();
        Connection conn = ConnectionFactory.createConnection(conf);
        LoadIncrementalHFiles incr = new LoadIncrementalHFiles(conf);

        incr.doBulkLoad(new Path("/aaa"), conn.getAdmin(), conn.getTable(TableName.valueOf("ns1:temp")),
                conn.getRegionLocator(TableName.valueOf("ns1:temp")));

        conn.close();

    }
}
