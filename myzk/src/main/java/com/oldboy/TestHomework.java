package com.oldboy;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;

import java.util.ArrayList;
import java.util.List;

public class TestHomework {
    static List<String> list = new ArrayList<>();

    public static void main(String[] args) {
        try {
            String conn = "s102:2181,s103:2181,s104:2181";

            //入口点，参数分别为连接串，连接超时时间，null
            ZooKeeper zk = new ZooKeeper(conn, 50000, null);


            //testCreate("/a/b/c/d", zk);
            //testList("/a",zk);
            testDelete("/a", zk, list);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //递归创建

    public static void testCreate(String path, ZooKeeper zk) throws Exception {

        String[] arr = path.split("/");
        if (arr.length > 1) {
            String newPath = "";
            for (int i = 1; i < arr.length; i++) {
                newPath = newPath + "/" + arr[i];

                zk.create(newPath, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

                System.out.println("ok");
            }


        }
        zk.close();
    }


    //递归列出
    public static void testList(String path, ZooKeeper zk) throws Exception {

        List<String> children = zk.getChildren(path, false);
        for (String child : children) {
            String newPath;
            if (path.equals("/")) {
                newPath = path + child;
            } else {
                newPath = path + "/" + child;
            }
            System.out.println(newPath);
            testList(newPath, zk);
        }
        zk.close();
    }


    //递归删除

    /**
     *
     */

    //递归列出
    public static void testDelete(String path, ZooKeeper zk, List list) throws Exception {

        List<String> children = zk.getChildren(path, false);
        //判断叶子结点是否含有子结点

        if (children.size() > 0) {

            for (String child : children) {
                String newPath;
                if (path.equals("/")) {
                    newPath = path + child;
                } else {
                    newPath = path + "/" + child;
                }
                System.out.println("newPath:" + newPath);
                testDelete(newPath, zk, list);

            }
        }
//        zk.delete(path, -1);
        System.out.println("path:" + path);
    }

}
