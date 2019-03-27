package com.oldboy.hbase.calllog;

import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.coprocessor.BaseRegionObserver;
import org.apache.hadoop.hbase.coprocessor.ObserverContext;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.regionserver.InternalScanner;
import org.apache.hadoop.hbase.regionserver.wal.WALEdit;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class CalllogCopro extends BaseRegionObserver {

    //插入主叫之后，同时插入被叫
    //13843838438,201901,0,13800000000
    @Override
    public void postPut(ObserverContext<RegionCoprocessorEnvironment> e, Put put, WALEdit edit, Durability durability) throws IOException {

        RegionCoprocessorEnvironment env = e.getEnvironment();
        TableName tableName = env.getRegionInfo().getTable();
        //如果表名是calllog
        if (tableName.getNameAsString().equals("ns1:calllog")) {
            Table table = env.getTable(tableName);

            //从put中拿取主叫rowkey
            String callerRow = new String(put.getRow());
            //判断是否是主叫
            if (callerRow.contains(",0,")) {
                String[] callerArr = callerRow.split(",");
                String caller = callerArr[0];
                String date = callerArr[1];
                String callee = callerArr[3];

                String calleeRow = callee + "," + date + ",1," + caller;

                Put calleePut = new Put(calleeRow.getBytes());
                //callee的put有一列
                calleePut.addColumn("normal".getBytes(), "refid".getBytes(), callerRow.getBytes());

                table.put(calleePut);
            }
        }
    }

    @Override
    public boolean postScannerNext(ObserverContext<RegionCoprocessorEnvironment> e, InternalScanner s, List<Result> results, int limit, boolean hasMore) throws IOException {
        boolean b = super.postScannerNext(e, s, results, limit, hasMore);

        RegionCoprocessorEnvironment env = e.getEnvironment();
        TableName tableName = env.getRegionInfo().getTable();
        //如果表名是calllog
        if (tableName.getNameAsString().equals("ns1:calllog")) {
            Table table = env.getTable(tableName);
            //新的List，相当于容易，暂存之前的数据
            List<Result> list = new ArrayList<>();
            list.addAll(results);
            //
            results.clear();

            //扫描中可能有主叫数据，也可能有被叫数据
            for (Result result : list) {
                //基于上述情况，需要判断result是什么数据
                String row = new String(result.getRow());
                //主叫,会元封不动，将数据放进results中
                if (row.contains(",0,")) {
                    results.add(result);
                } else {
                    //得到被叫的refid
                    Cell cell = result.listCells().get(0);
                    String refid = new String(CellUtil.cloneValue(cell));

                    //通过被叫的refid,获取主叫的cell
                    Get get = new Get(refid.getBytes());
                    Result result2 = table.get(get);

                    List<Cell> celllist = new ArrayList<>();

                    //但是，主叫的cell都是以主叫作为rowkey
                    List<Cell> cells = result2.listCells();
                    for (Cell cell1 : cells) {
                        //直接组装cell
                        Cell newCell = CellUtil.createCell(row.getBytes(), CellUtil.cloneFamily(cell1), CellUtil.cloneQualifier(cell1),
                                System.currentTimeMillis(), KeyValue.Type.Minimum, CellUtil.cloneValue(cell1), null);
                        celllist.add(newCell);

                    }
                    Result newResult = Result.create(celllist);
                    results.add(newResult);
                }
            }
        }
        return b;
    }
}
