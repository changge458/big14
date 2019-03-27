package com.oldboy.umeng.phone;


import com.oldboy.umeng.genlog.GenLogAgg;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 模拟音乐手机客户端手机日志生成主类
 * 生成方式：
 * 我：0-99
 * 一组：100-199
 * 二组：200-299
 * .....
 */
public class DataSender {

    public static void main(String[] args) throws Exception {

        for (int i = 0; i < 10000; i++) {

            String s = GenLogAgg.genLogAgg(1000, 3);

            System.out.println(s);

            doSend(s);

            try {
                //Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private static void doSend(String json) {

        try {
            String strUrl = "http://localhost:8089";

            URL url = new URL(strUrl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //设置请求方式
            conn.setRequestMethod("POST");
            //设置可以传输数据
            conn.setDoOutput(true);

            conn.setRequestProperty("client_time", System.currentTimeMillis() + "");

            OutputStream os = conn.getOutputStream();

            //Thread.sleep(20);

            os.write(json.getBytes());

            os.flush();
            os.close();
            System.out.println(conn.getResponseCode());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
