package com.oldboy.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestDumpData {

    public static void main(String[] args) throws Exception {
        String url = "jdbc:mysql:///music164";
        String user = "root";
        String pass = "root";

        ArrayList<String> list = new ArrayList<String>();

        Connection conn = DriverManager.getConnection(url, user, pass);

        Statement st = conn.createStatement();

        //通过infoemation_schema库中的COLUMNS表获取user对应的字段
        ResultSet rs = st.executeQuery("select COLUMN_NAME from information_schema.COLUMNS where TABLE_NAME='user' and TABLE_SCHEMA='music164' ");

        while (rs.next()) {
            String column = rs.getString(1);
            list.add(column);
        }


        Configuration conf = HBaseConfiguration.create();
        //入口点：建立连接
        org.apache.hadoop.hbase.client.Connection conn2 = ConnectionFactory.createConnection(conf);

        Table table = conn2.getTable(TableName.valueOf("ns1:t1"));


        ResultSet resultSet = st.executeQuery("select * from `user`");

        List<Put> list2 = new ArrayList<Put>();

        long start = System.currentTimeMillis();
        while (resultSet.next()) {

            String userId = resultSet.getString(1);
            //将数据导入到hbase，其中主键为userID
            Put put = new Put(userId.getBytes());

            for (int i = 1; i < list.size(); i++) {

                String val = resultSet.getString(i + 1);
                put.addColumn("f1".getBytes(), list.get(i).getBytes(), val.getBytes());
            }
            list2.add(put);


        }
        table.put(list2);

        System.out.println(System.currentTimeMillis() - start);
        conn2.close();


    }

}
