package com.oldboy.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;
import sun.net.spi.nameservice.NameServiceDescriptor;

import java.util.ArrayList;
import java.util.List;

public class TestHbase {

    @Test
    public void createDatabase() throws Exception {

        Configuration conf = HBaseConfiguration.create();
        //入口点：建立连接
        Connection conn = ConnectionFactory.createConnection(conf);


        //ddl必须先得到admin
        Admin admin = conn.getAdmin();


        //构造namespace对象
        NamespaceDescriptor ns1 = NamespaceDescriptor.create("ns1").build();
        //admin才可以建库
        admin.createNamespace(ns1);

        admin.close();
        conn.close();

    }

    @Test
    public void createTable() throws Exception {

        Configuration conf = HBaseConfiguration.create();
        //入口点：建立连接
        Connection conn = ConnectionFactory.createConnection(conf);


        //ddl必须先得到admin
        Admin admin = conn.getAdmin();


        //构造namespace对象
        HTableDescriptor htd = new HTableDescriptor(TableName.valueOf("ns1:t1"));
        htd.addFamily(new HColumnDescriptor("f1"));
        htd.addFamily(new HColumnDescriptor("f2"));


        //admin才可以建库
        admin.createTable(htd);

        admin.close();
        conn.close();

    }

    @Test
    public void testPut() throws Exception {

        Configuration conf = HBaseConfiguration.create();
        //入口点：建立连接
        Connection conn = ConnectionFactory.createConnection(conf);

        HTable table = (HTable) conn.getTable(TableName.valueOf("ns1:test"));

        table.setAutoFlush(false, false);


        List<Put> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Put put = new Put(Bytes.toBytes(i));
            put.setDurability(Durability.SKIP_WAL);

            put.addColumn("f1".getBytes(), "name".getBytes(), ("tom" + i).getBytes());

            list.add(put);

        }

        table.put(list);

        table.flushCommits();


        conn.close();

    }

    @Test
    public void testDelete() throws Exception {

        Configuration conf = HBaseConfiguration.create();
        //入口点：建立连接
        Connection conn = ConnectionFactory.createConnection(conf);

        Table table = conn.getTable(TableName.valueOf("ns1:test"));


        Delete delete = new Delete("1".getBytes());


        table.delete(delete);


        conn.close();

    }

    @Test
    public void testScan() throws Exception {

        Configuration conf = HBaseConfiguration.create();
        //入口点：建立连接
        Connection conn = ConnectionFactory.createConnection(conf);

        Table table = conn.getTable(TableName.valueOf("ns1:t1"));

        Scan scan = new Scan();
        //scan.setCaching(5000);
        scan.setBatch(100);

        long start = System.currentTimeMillis();
        ResultScanner scanner = table.getScanner(scan);

        for (Result result : scanner) {
            //result是是什么？是一行数据
            //cell中包括一个单元格内所有数据
            //包括row、cf、col、ts、value等等
            List<Cell> cells = result.listCells();
            for (Cell cell : cells) {
                String row = new String(CellUtil.cloneRow(cell));
                String cf = new String(CellUtil.cloneFamily(cell));
                String col = new String(CellUtil.cloneQualifier(cell));
                String val = new String(CellUtil.cloneValue(cell));
                System.out.println(row + "/" + cf + "/" + col + "/" + val);
            }
            System.out.println("========================================================");
        }
        System.out.println(System.currentTimeMillis() - start);

        scanner.close();

        conn.close();

    }

    @Test
    public void testGet() throws Exception {

        Configuration conf = HBaseConfiguration.create();
        //入口点：建立连接
        Connection conn = ConnectionFactory.createConnection(conf);

        Table table = conn.getTable(TableName.valueOf("ns1:test"));

        Get get = new Get("1".getBytes());

        Result result = table.get(get);

        System.out.println(result.size());

        //result是是什么？是一行数据
        //cell中包括一个单元格内所有数据
        //包括row、cf、col、ts、value等等
//        List<Cell> cells = result.listCells();
//        if(cells.size() > 0){
//            for (Cell cell : cells) {
//                String row = new String(CellUtil.cloneRow(cell));
//                String cf = new String(CellUtil.cloneFamily(cell));
//                String col = new String(CellUtil.cloneQualifier(cell));
//                String val = new String(CellUtil.cloneValue(cell));
//                System.out.println(row + "/" + cf + "/" + col + "/" + val);
//
//            }
//        }


        conn.close();

    }


}
