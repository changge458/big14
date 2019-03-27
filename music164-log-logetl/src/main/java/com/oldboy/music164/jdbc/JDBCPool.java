package com.oldboy.music164.jdbc;

import com.oldboy.music164.util.PropUtil;
import com.oldboy.music164.constant.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

public class JDBCPool {


    private static JDBCPool instance = null;


    //实现线程安全
    public static JDBCPool getInstance() {
        if (instance == null) {

            synchronized (JDBCPool.class) {

                if (instance == null) {
                    instance = new JDBCPool();
                }
            }
        }
        return instance;
    }


    //数据库连接池
    private LinkedList<Connection> dataSource = new LinkedList<>();

    private JDBCPool() {

        int datasourceSize = PropUtil.getIntValue(Constants.DS_SIZE);

        String driver = PropUtil.getValue(Constants.JDBC_DRIVER);

        String url = PropUtil.getValue(Constants.JDBC_URL);

        String username = PropUtil.getValue(Constants.JDBC_USER);

        String password = PropUtil.getValue(Constants.JDBC_PASS);

        for (int i = 0; i < datasourceSize; i++) {

            try {
                Class.forName(driver);
                Connection conn = DriverManager.getConnection(url, username, password);
                dataSource.push(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized Connection getConnection() {

        while (dataSource.size() == 0) {

            try {
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return dataSource.poll();

    }


    public void executeQuery(String sql, Object[] params, QueryCallback callback) {

        Connection conn = null;
        PreparedStatement ppst = null;
        ResultSet rs = null;


        try {

            conn = getConnection();
            ppst = conn.prepareStatement(sql);

            for (int i = 0; i < params.length; i++) {
                ppst.setObject(i + 1, params[i]);
            }

            rs = ppst.executeQuery();
            callback.process(rs);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (conn != null){
                dataSource.push(conn);
            }
        }

    }

    public interface QueryCallback {
        void process(ResultSet rs) throws Exception;
    }


}
