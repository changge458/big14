package com.oldboy;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestZK {


    /**
     * 查看/下的结点
     */
    @Test
    public void testList() throws Exception {

        String conn = "s102:2181,s103:2181,s104:2181";

        //入口点，参数分别为连接串，连接超时时间，null
        ZooKeeper zk = new ZooKeeper(conn, 5000, null);

        //
        List<String> children = zk.getChildren("/", null);

        for (String child : children) {
            System.out.println(child);
        }
        zk.close();


    }

    /**
     * 查看/下的结点数据
     */
    @Test
    public void testGet() throws Exception {

        String conn = "s102:2181,s103:2181,s104:2181";

        //入口点，参数分别为连接串，连接超时时间，null
        ZooKeeper zk = new ZooKeeper(conn, 5000, null);

        //
        byte[] data = zk.getData("/", null, null);

        System.out.println(new String(data));

        zk.close();
    }

    /**
     * 创建新结点
     */
    @Test
    public void testCreate() throws Exception {

        String conn = "s102:2181,s103:2181,s104:2181";

        //入口点，参数分别为连接串，连接超时时间，null
        ZooKeeper zk = new ZooKeeper(conn, 5000, null);

        //使用ACL控制权限
        ACL acl = new ACL(ZooDefs.Perms.ALL, ZooDefs.Ids.ANYONE_ID_UNSAFE);
        ArrayList<ACL> list = new ArrayList<ACL>();
        list.add(acl);

        //String s = zk.create("/d", "helloworld".getBytes(), list,CreateMode.PERSISTENT);

        String s = zk.create("/d", "helloworld".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        System.out.println("ok");

        zk.close();
    }

    //delete，注意version
    @Test
    public void testDelete() throws Exception {

        String conn = "s102:2181,s103:2181,s104:2181";

        //入口点，参数分别为连接串，连接超时时间，null
        ZooKeeper zk = new ZooKeeper(conn, 5000, null);

        Stat stat = new Stat();

        byte[] data = zk.getData("/a", null, stat);

        int version = stat.getVersion();

        System.out.println(version);

        zk.delete("/a", version);
        zk.close();
    }


    //set

    @Test
    public void testSet() throws Exception {

        String conn = "s102:2181,s103:2181,s104:2181";

        //入口点，参数分别为连接串，连接超时时间，null
        ZooKeeper zk = new ZooKeeper(conn, 5000, null);

        Stat stat = new Stat();

        byte[] data = zk.getData("/b", null, stat);

        int version = stat.getVersion();

        System.out.println(version);

        zk.setData("/b","helloworld".getBytes(),version);

        zk.close();
    }

    //对于zk结点，有以下两个情况
    //1、递归删除rmr
    //2、递归创建
    //3、递归列出 + get


}
