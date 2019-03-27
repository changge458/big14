package com.oldboy.music164.util;

import java.io.InputStream;
import java.util.Properties;

public class PropUtil {

    private static Properties prop;

    static {

        try {
            prop = new Properties();
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("jdbc.properties");
            prop.load(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getValue(String key) {

        try {
            String value = prop.getProperty(key);
            return value;

        } catch (Exception e) {
            return null;
        }


    }

    public static Integer getIntValue(String key) {

        try {
            String value = prop.getProperty(key);

            int val = Integer.parseInt(value);

            return val;

        } catch (Exception e) {
            return 0;
        }


    }

}
