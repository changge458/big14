package com.oldboy.umeng.columnutil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Log_Usage_Column_Util {

    public static ArrayList<String> list = new ArrayList<>();
    static {
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("log_usage.properties");

            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line = null;

            while((line = br.readLine()) != null){
                String col = line.split("=")[1];
                list.add(col);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
