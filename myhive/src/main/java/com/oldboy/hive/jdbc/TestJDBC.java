package com.oldboy.hive.jdbc;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestJDBC {

    public static void main(String[] args) throws Exception {

        //驱动类路径
        Class.forName("org.apache.hive.jdbc.HiveDriver");

        String url = "jdbc:hive2://s101:10000/big14";

        Connection conn = DriverManager.getConnection(url);

        Statement st = conn.createStatement();

        ResultSet rs = st.executeQuery("select * from employee2");
        while (rs.next()) {
            String name = rs.getString(1);
            Object city = rs.getObject(2);
            Object sex_age = rs.getObject(3);
            Object score = rs.getObject(4);
            Object job = rs.getObject(5);

            System.out.println(name + "/" + city + "/" + sex_age + "/" + score + "/" + job);

        }
        st.close();
        conn.close();


    }

    @Test
    public void testInsert() throws Exception {

        //驱动类路径
        Class.forName("org.apache.hive.jdbc.HiveDriver");

        String url = "jdbc:hive2://s101:10000/big14";

        Connection conn = DriverManager.getConnection(url);

        Statement st = conn.createStatement();

        st.execute("insert into users values(10,'tom10',100)");

        st.close();
        conn.close();


    }

    @Test
    /**
     * 不行
     */
    public void testDelete() throws Exception {

        //驱动类路径
        Class.forName("org.apache.hive.jdbc.HiveDriver");

        String url = "jdbc:hive2://s101:10000/big14";

        Connection conn = DriverManager.getConnection(url);

        Statement st = conn.createStatement();

        st.execute("delete from users where id=1");

        st.close();
        conn.close();


    }




}
