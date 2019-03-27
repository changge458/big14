package com.oldboy.hbase.coprocessor;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.CoprocessorEnvironment;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Durability;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.coprocessor.BaseRegionObserver;
import org.apache.hadoop.hbase.coprocessor.ObserverContext;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.regionserver.wal.WALEdit;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;

public class TestCopro extends BaseRegionObserver {

    private static final String PATH = "/home/centos/copro.log";
    FileOutputStream fos;

    @Override
    public void start(CoprocessorEnvironment e) throws IOException {
        //env中可以得到
        fos = new FileOutputStream(PATH, true);
        fos.write("这是一个start操作\n".getBytes());
    }

    @Override
    public void stop(CoprocessorEnvironment e) throws IOException {
        fos = new FileOutputStream(PATH, true);
        fos.write("这是一个stop操作\n".getBytes());
    }

    @Override
    public void postOpen(ObserverContext<RegionCoprocessorEnvironment> e) {
        try {
            String tableName = e.getEnvironment().getRegionInfo().getTable().getNameAsString();
            fos = new FileOutputStream(PATH, true);
            fos.write("这是一个open操作\t表名：".getBytes());
            fos.write(tableName.getBytes());
            fos.write("\n".getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void postClose(ObserverContext<RegionCoprocessorEnvironment> e, boolean abortRequested) {
        try {
            String tableName = e.getEnvironment().getRegionInfo().getTable().getNameAsString();
            fos = new FileOutputStream(PATH, true);
            fos.write("这是一个close操作\t表名：".getBytes());
            fos.write(tableName.getBytes());
            fos.write("\n".getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void postFlush(ObserverContext<RegionCoprocessorEnvironment> e) throws IOException {
        try {
            String tableName = e.getEnvironment().getRegionInfo().getTable().getNameAsString();
            fos = new FileOutputStream(PATH, true);
            fos.write("这是一个flush操作\t表名：".getBytes());
            fos.write(tableName.getBytes());
            fos.write("\n".getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void postPut(ObserverContext<RegionCoprocessorEnvironment> e, Put put, WALEdit edit, Durability durability) throws IOException {
        try {

            fos = new FileOutputStream(PATH, true);

            String tableName = e.getEnvironment().getRegionInfo().getTable().getNameAsString();
            String row = new String(put.getRow());
            //获取put中的数据
            NavigableMap<byte[], List<Cell>> cellMap = put.getFamilyCellMap();
            //
            Map.Entry<byte[], List<Cell>> entry = cellMap.firstEntry();

            String cf = new String(entry.getKey());

            List<Cell> cells = entry.getValue();
            for (Cell cell : cells) {
                String cf2 = new String(CellUtil.cloneFamily(cell));
                String col = new String(CellUtil.cloneQualifier(cell));
                String val = new String(CellUtil.cloneValue(cell));
                fos.write("这是一个put操作\t表名：".getBytes());
                fos.write(tableName.getBytes());
                fos.write("\trow：".getBytes());
                fos.write(row.getBytes());
                fos.write("\tcf:".getBytes());
                fos.write(cf.getBytes());
                fos.write("\tcf2:".getBytes());
                fos.write(cf2.getBytes());
                fos.write("\tcol:".getBytes());
                fos.write(col.getBytes());
                fos.write("\tval:".getBytes());
                fos.write(val.getBytes());
                fos.write("\n".getBytes());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void postDelete(ObserverContext<RegionCoprocessorEnvironment> e, Delete delete, WALEdit edit, Durability durability) throws IOException {
        try {

            fos = new FileOutputStream(PATH, true);

            String tableName = e.getEnvironment().getRegionInfo().getTable().getNameAsString();
            String row = new String(delete.getRow());
            //获取put中的数据
            NavigableMap<byte[], List<Cell>> cellMap = delete.getFamilyCellMap();
            //
            Map.Entry<byte[], List<Cell>> entry = cellMap.firstEntry();

            String cf = new String(entry.getKey());

            List<Cell> cells = entry.getValue();
            for (Cell cell : cells) {
                String cf2 = new String(CellUtil.cloneFamily(cell));
                String col = new String(CellUtil.cloneQualifier(cell));
                String val = new String(CellUtil.cloneValue(cell));
                fos.write("这是一个put操作\t表名：".getBytes());
                fos.write(tableName.getBytes());
                fos.write("\trow：".getBytes());
                fos.write(row.getBytes());
                fos.write("\tcf:".getBytes());
                fos.write(cf.getBytes());
                fos.write("\tcf2:".getBytes());
                fos.write(cf2.getBytes());
                fos.write("\tcol:".getBytes());
                fos.write(col.getBytes());
                fos.write("\tval:".getBytes());
                fos.write(val.getBytes());
                fos.write("\n".getBytes());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
