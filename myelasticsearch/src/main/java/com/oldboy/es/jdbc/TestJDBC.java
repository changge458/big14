package com.oldboy.es.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class TestJDBC {

    public static void main(String[] args) throws Exception {

        String driver = "org.elasticsearch.xpack.sql.jdbc.EsDriver";
        String address = "jdbc:es://http://192.168.79.102:9200/";
        Properties properties = new Properties();


        Connection conn = DriverManager.getConnection(address,properties);

        Statement st = conn.createStatement();

        ResultSet rs = st.executeQuery("show tables");

        while (rs.next()){
            String s = rs.toString();
            System.out.println(s);
        }
    }
}
