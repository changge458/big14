package com.oldboy.umeng.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.maxmind.db.Reader;

import java.io.InputStream;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class ParseIPUtil {

    private static Reader reader;
    private static Map<String,String> map = new HashMap<>();

    static {
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("GeoLite2-City.mmdb");
            reader = new Reader(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String processIp(String ip){
        try {
            JsonNode jsonNode = reader.get(InetAddress.getByName(ip));
            String country = jsonNode.get("country").get("names").get("zh-CN").asText();
            String province = jsonNode.get("subdivisions").get(0).get("names").get("zh-CN").asText();
            //String city = jsonNode.get("city").get("names").get("zh-CN").asText();

            map.put(ip,country+","+province);


        } catch (Exception e) {
            map.put(ip,"unknown,unknown");
        }
        return map.get(ip);

    }

    public static String getCountry(String ip){
        return processIp(ip).split(",")[0];
    }

    public static String getProvince(String ip){
        return processIp(ip).split(",")[1];
    }
}
