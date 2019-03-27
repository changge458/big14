package com.oldboy.umeng.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oldboy.umeng.constant.Constants;
import com.oldboy.umeng.jdbc.JDBCPool;

import java.sql.ResultSet;
import java.util.*;

public class ParseKeyUtil {

    static HashMap<String, HashMap<String, LinkedHashMap<String, String>>> total_map = new HashMap<>();

    //将SQL数据变为<appEventLogs,<deviceId,1>> 的情况
    //

    static {
        try {

            JDBCPool pool = JDBCPool.getInstance();

            String tablenames = PropUtil.getValue(Constants.TABLE_NAMES);

            String[] arr = tablenames.split(",");

            for (String tablename : arr) {
                HashMap<String, LinkedHashMap<String, String>> map = new HashMap<>();
                pool.executeQuery("select * from " + tablename, new Object[]{}, new JDBCPool.QueryCallback() {
                    @Override
                    public void process(ResultSet rs) throws Exception {
                        String table_key = null;
                        LinkedHashMap<String, String> map2 = new LinkedHashMap<>();
                        while (rs.next()) {
                            table_key = rs.getString(2);
                            String key = rs.getString(3);
                            String type = rs.getString(4);
                            map2.put(key, type);
                        }
                        map.put(table_key, map2);
                    }
                });
                total_map.put(tablename, map);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static List<Object[]> parseKey(String line, String tablename) {

        List<Object[]> list;

        String[] arr = line.split("#");

        if (arr.length > 4 && arr[4] != null) {
            HashMap<String, LinkedHashMap<String, String>> map = total_map.get(tablename);

            //正式开始解析
            Map.Entry<String, LinkedHashMap<String, String>> entry = map.entrySet().iterator().next();
            String table_key = entry.getKey();

            LinkedHashMap<String, String> key_types = entry.getValue();

            Set<Map.Entry<String, String>> entrySet = key_types.entrySet();

            String newJson = arr[4].replaceAll("\\\\", "");

            try {
                JSONObject jo = JSON.parseObject(newJson);
                JSONArray jsonArray = jo.getJSONArray(table_key);

                list = new ArrayList<>();

                //迭代jsonArray，获取返回的行数
                if (jsonArray != null) {
                    for (Object o : jsonArray) {

                        int i = 0;
                        Object[] objs = new Object[entrySet.size()];
                        for (Map.Entry<String, String> key_type : entrySet) {

                            String key = key_type.getKey();
                            String type = key_type.getValue();

                            int typeNum;
                            try {

                                typeNum = Integer.parseInt(type);

                                //解析type为1的字段
                                if (typeNum == 1) {
                                    if (key.equals("country")) {
                                        objs[i] = "";
                                    } else if (key.equals("province")) {
                                        objs[i] = "";
                                    } else {
                                        objs[i] = JsonUtil.parseJson(arr[4], key);
                                    }
                                }
                                //解析type为2的字段
                                else if (typeNum == 2) {
                                    objs[i] = JsonUtil.parseJson(o.toString(), key);
                                }
                            } catch (Exception e) {
                                //e.printStackTrace();
                                //解析type为0的字段
                                typeNum = Integer.parseInt(type.split("_")[1]);

                                objs[i] = arr[typeNum];
                            }
                            i++;
                        }
                        list.add(objs);
                    }

                }
                return list;
            } catch (Exception e) {
            }

        }
        return null;

    }


}
