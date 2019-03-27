package com.oldboy.umeng.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class DictUtil {
    static Random r = new Random();

    private static Map<String, List<String>> map = new HashMap<String, List<String>>();


    static {
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("dictionary.dat");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line = null;
            List<String> list = null;
            while ((line = br.readLine()) != null) {


                //将带有[的解析为map的key
                if (line.startsWith("[")) {

                    list = new ArrayList<String>();

                    String key = line.substring(1, line.length() - 1);

                    map.put(key, list);

                }

                //其他的添加到List中
                else {
                    list.add(line);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static String getRandomValue(String key) {

        List<String> values = map.get(key);
        if (values == null) {
            return null;
        }
        return values.get(r.nextInt(values.size()));
    }

    public static String getRandomOstype(String platform) {
        List<String> list = map.get("ostype");
        String[] os = null;
        for (String s : list) {
            if (s.startsWith(platform)) {
                os = s.split("=")[1].split(",");
            }
        }
        if (os != null) {
            return os[r.nextInt(os.length)];
        }
        return null;
    }

    public static void main(String[] args) {
        String ios = getRandomOstype("ios");
        System.out.println(ios);


    }


}
