package com.oldboy.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.junit.Test;

import java.util.List;

public class TestFilter {
    @Test
    public void testScan() throws Exception {

        Configuration conf = HBaseConfiguration.create();
        //入口点：建立连接
        Connection conn = ConnectionFactory.createConnection(conf);

        Table table = conn.getTable(TableName.valueOf("ns1:t1"));

        Scan scan = new Scan();
        //scan添加过滤器
        //scan.setFilter(new ValueFilter(CompareFilter.CompareOp.GREATER, new BinaryComparator("9".getBytes())));
        Filter f1 = new SingleColumnValueFilter("f1".getBytes(), "birthday".getBytes(), CompareFilter.CompareOp.LESS, new BinaryComparator("2009".getBytes()));
        Filter f2 = new SingleColumnValueFilter("f1".getBytes(), "brand".getBytes(), CompareFilter.CompareOp.EQUAL, new BinaryComparator("华为".getBytes()));
        FilterList fl = new FilterList(f1, f2);

        Filter f3 = new QualifierFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator("nickname".getBytes()));
        Filter f4 = new QualifierFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator("age".getBytes()));
        Filter f5 = new QualifierFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator("brand".getBytes()));
        FilterList fl2 = new FilterList(FilterList.Operator.MUST_PASS_ONE, f3, f4, f5);


        //SingleColumnValueFilter fi = new SingleColumnValueFilter("f1".getBytes(), "age".getBytes(), CompareFilter.CompareOp.EQUAL, new RegexStringComparator(".*9$"));


        //FilterList，可以使得全量数据向部分数据靠拢
        FilterList filterList = new FilterList(fl, fl2);


        scan.setFilter(filterList);

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
        }

        conn.close();

    }
}
