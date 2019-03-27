package com.oldboy.umeng.util;

import com.oldboy.umeng.constant.Constants;
import com.oldboy.umeng.jdbc.JDBCPool;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MusicUtil {

    public static final Map<String, List<Map<String, String>>> MUSIC_MAP_LIST = new HashMap<>();

    //初始化时候，将所有Music信息放在一个Map中
    static {

        JDBCPool pool = JDBCPool.getInstance();

        Object[] objs = {};

        String tablenames = PropUtil.getValue(Constants.MUSIC_TABLENAME);

        String[] tablenameArr = tablenames.split(",");

        for (String tablename : tablenameArr) {
            List<Map<String, String>> list = new ArrayList<>();
            pool.executeQuery("select mname,mtime from " + tablename, objs, new JDBCPool.QueryCallback() {
                @Override
                public void process(ResultSet rs) throws Exception {
                    while (rs.next()) {
                        Map<String, String> map = new HashMap<>();
                        map.put("mname", rs.getString("mname"));
                        map.put("mtime", rs.getString("mtime"));
                        list.add(map);
                    }
                }
            });
            MUSIC_MAP_LIST.put(tablename, list);
        }

    }

    public static final Map<String, String> MARK_MAPPING = new HashMap<>();

    static {
        MARK_MAPPING.put("share", "4");
        MARK_MAPPING.put("favourite", "3");
        MARK_MAPPING.put("play", "2");
        MARK_MAPPING.put("listen", "1");
        MARK_MAPPING.put("skip", "-1");
        MARK_MAPPING.put("black", "-5");
        MARK_MAPPING.put("nofavourite", "-3");
        MARK_MAPPING.put("null", "0");
    }

    public static void main(String[] args) {
        System.out.println();
    }


}
