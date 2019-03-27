package com.oldboy.flume.sink;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.flume.*;
import org.apache.flume.conf.Configurable;
import org.apache.flume.sink.AbstractSink;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TemptagsSink extends AbstractSink implements Configurable {

    String url;
    String username;
    String password;
    String table;
    Connection conn;
    Statement st;
    PreparedStatement ppst;


    public void configure(Context context) {
        //获取参数并赋值
        url = context.getString("url");
        username = context.getString("username");
        password = context.getString("password");
        table = context.getString("table");
    }


    @Override
    public synchronized void start() {
        //初始化sql连接
        try {
            conn = DriverManager.getConnection(url, username, password);
            //st = conn.createStatement();
            ppst = conn.prepareStatement("insert into "+ table + " values(?,?)");
            super.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Status process() throws EventDeliveryException {

        Status result = Status.READY;
        Channel channel = getChannel();
        Transaction transaction = channel.getTransaction();
        Event event = null;

        try {
            transaction.begin();
            event = channel.take();

            if (event != null) {
                //需要进行业务处理
                //第一步，将json数据解析出来
                String line = new String(event.getBody());

                String[] arr = line.split("\t");

                //第二步，获取商家id
                int busid = Integer.parseInt(arr[0]);

                //第三步，获取tags
                List<String> tags = parseJson(arr[1]);

                if (tags != null && tags.size() > 0) {
                    for (String tag : tags) {
                        //st.execute("insert into " + table + " values(" + busid + ",'" + tag + "')");
                        ppst.setInt(1,busid);
                        ppst.setString(2,tag);
                        ppst.execute();
                    }
                }

            } else {
                // No event found, request back-off semantics from the sink runner
                result = Status.BACKOFF;
            }
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            throw new EventDeliveryException("Failed to log event: " + event, ex);
        } finally {
            transaction.close();
        }
        return result;
    }

    @Override
    public synchronized void stop() {
        try {
            ppst.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private static List<String> parseJson(String json) {

        JSONObject jo = JSON.parseObject(json);

        JSONArray jArray = jo.getJSONArray("extInfoList");

        List<String> list = new ArrayList<String>();

        if (jArray != null && jArray.size() > 0) {
            JSONObject jo2 = (JSONObject) jArray.get(0);
            JSONArray jArray2 = jo2.getJSONArray("values");

            for (Object o : jArray2) {
                list.add(o.toString());
            }
            return list;
        }
        return null;

    }

}
