package com.oldboy.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HbaseUtil {
    static Connection conn = null;

    static {

        try {
            Configuration conf = HBaseConfiguration.create();
            conn = ConnectionFactory.createConnection(conf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void putMap(String table, String row, String family, Map<String, String> map) {

        Set<Map.Entry<String, String>> entries = map.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            String column = entry.getKey();
            String value = entry.getValue();
            put(table, row, family, column, value);
        }
    }


    public static void put(String table, String row, String family, String column, String value) {
        try {
            Table tab = conn.getTable(TableName.valueOf(table));

            Put put = new Put(Bytes.toBytes(row));
            put.addColumn(Bytes.toBytes(family), Bytes.toBytes(column), Bytes.toBytes(value));

            tab.put(put);

            //System.out.println("ok");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //getVisitTime是将解析出来的startupLog中的日志创建时间
    //和hbase中数据进行对比
    //如果存在，则判断并更新
    //如果不存在则插入数据

    public static Result getVisitTime(String table, String devId, String family) {

        Result result = null;

        try {
            Table tab = conn.getTable(TableName.valueOf(table));

            Get get = new Get(Bytes.toBytes(devId));
            get.addFamily(Bytes.toBytes(family));

            result = tab.get(get);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;


    }

    public static boolean exists(String table, String devId, String family) {

        boolean exists = false;

        try {
            Table tab = conn.getTable(TableName.valueOf(table));

            Get get = new Get(Bytes.toBytes(devId));
            get.addFamily(Bytes.toBytes(family));

            Result result = tab.get(get);

            if (result.size() != 0) {
                exists = true;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return exists;


    }


    public static String get(String table, String devId, String family, String column) {

        String value = null;

        try {
            Table tab = conn.getTable(TableName.valueOf(table));
            // 将字符串转换成byte[]
            byte[] rowkeybyte = Bytes.toBytes(devId);
            Get get = new Get(rowkeybyte);
            Result result = tab.get(get);
            byte[] resultbytes = result.getValue(family.getBytes(), column.getBytes());
            if (resultbytes != null) {
                value = new String(resultbytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }


    /**
     * 洪阳代码
     * @param
     */

    public static HashMap<String,String> get(String table, String row, String family) {
        HashMap<String, String> map = new HashMap<>();
        try {
            Table tab = conn.getTable(TableName.valueOf(table));

            Get get = new Get(Bytes.toBytes(row));
            get.addFamily(Bytes.toBytes(family));
            Result result = tab.get(get);
            List<Cell> cells = result.listCells();
            //result是是什么？是一行数据
            //cell中包括一个单元格内所有数据
            //包括row、cf、col、ts、value等等
            for (Cell cell : cells) {
                String col = new String(CellUtil.cloneQualifier(cell));
                String val = new String(CellUtil.cloneValue(cell));
                map.put(col,val);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  map;

    }








    public static void main(String[] args) {


    }

}
