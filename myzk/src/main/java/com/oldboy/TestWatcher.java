package com.oldboy;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.util.List;

public class TestWatcher {

    public static void main(String[] args) throws Exception {

        String conn = "s102:2181,s103:2181,s104:2181";

        //入口点，参数分别为连接串，连接超时时间，null
        ZooKeeper zk = new ZooKeeper(conn, 5000, null);

        Stat stat = new Stat();

        byte[] data = zk.getData("/b", new Watcher() {
            public void process(WatchedEvent event) {
                System.out.println(event.toString());
            }
        }, stat);

        System.out.println(new String(data));

        Thread.sleep(Integer.MAX_VALUE);

        zk.close();
    }

    @Test
    public void test2() throws Exception {
        String conn = "s102:2181,s103:2181,s104:2181";

        //入口点，参数分别为连接串，连接超时时间，null
        ZooKeeper zk = new ZooKeeper(conn, 5000, null);

        Stat stat = new Stat();

        List<String> children = zk.getChildren("/b", new Watcher() {
            public void process(WatchedEvent event) {
                System.out.println(event.toString());
            }
        }, stat);

        for (String child : children) {
            System.out.println(child);
        }

        Thread.sleep(Integer.MAX_VALUE);

        zk.close();

    }

    /**
     * 观察者重复注册
     * @throws Exception
     */
    @Test
    public void test3() throws Exception {
        String conn = "s102:2181,s103:2181,s104:2181";

        //入口点，参数分别为连接串，连接超时时间，null
        ZooKeeper zk = new ZooKeeper(conn, 5000, null);

        Stat stat = new Stat();

        Watcher w = new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                try {
                    System.out.println(event.toString());
                    List<String> children = zk.getChildren("/b", this, stat);
                    for (String child : children) {
                        System.out.println(child);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        List<String> children = zk.getChildren("/b", w, stat);
        for (String child : children) {
            System.out.println(child);
        }

        Thread.sleep(Integer.MAX_VALUE);
        zk.close();
    }
}
