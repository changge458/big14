package com.oldboy.umeng.phone;

import com.oldboy.umeng.genlog.GenLogAgg;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Random;

/**
 * 模拟音乐手机客户端手机日志生成主类
 * 生成方式：
 * 我：0-99
 * 一组：100-199
 * 二组：200-299
 * .....
 *
 */
public class DataSender {

    public static void main(String[] args) throws Exception {
        DecimalFormat df = new DecimalFormat("00");
        //生成2018年12月份1号到20号的日志
        for (int i = 1; i <= 30; i++) {
            genUser(100, "2018-12-" + df.format(i), 100);
        }
    }

    /**
     * 产生指定日期的日志
     *
     * @param userNum 用户总数
     * @param date    指定日期
     * @param logNum  每个用户生成日志包数（日志包作为上传到服务端日志的最小单元）
     */

    public static void genUser(int userNum, final String date, final int logNum) {

        Random r = new Random();

        //产生
        for (int i = 0; i < userNum; i++) {

            DecimalFormat df = new DecimalFormat("000000");
            final String deviceID = "Device" + df.format(i);

            //表映射 eg:1 => music_mix，参见TypeUtil
            final int type = (i % 9) + 1;


            Thread t1 = new Thread() {
                @Override
                public void run() {
                    try {
                        genData(deviceID, type, date, logNum);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            };
            t1.start();
        }
    }

    /**
     * 为指定用户，根据用户喜欢歌曲类型生成带有音乐偏好的指定数目的日志包
     *
     * @param deviceID 用户id或用户设备id
     * @param type     用户喜欢歌曲类型
     * @param date     指定日期
     * @param num      指定用户生成日志包个数
     */
    public static void genData(String deviceID, int type, String date, int num) {

        for (int i = 0; i < num; i++) {
            //生成日志工具类
            String logAgg = GenLogAgg.genLogAgg(type, deviceID, date);
            //
            System.out.println(logAgg);
            //doSend(logAgg);
            try {
                //Thread.sleep(20);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void doSend(String json) {

        try {
            String strUrl = "http://s204:80";

            URL url = new URL(strUrl);

            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            //设置请求方式
            conn.setRequestMethod("POST");
            //设置可以传输数据
            conn.setDoOutput(true);

            conn.setRequestProperty("client_time",System.currentTimeMillis() +"");

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
