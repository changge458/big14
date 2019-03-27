package com.oldboy.hadoop.hdfs.rack;

import org.apache.hadoop.net.DNSToSwitchMapping;

import java.util.ArrayList;
import java.util.List;

public class RackAware implements DNSToSwitchMapping {

    /**
     * 输入：ip地址或者主机名.是一个List
     * 输出：网络路径，如/rack1/192.168.79.101:50070
     *
     * @param names
     * @return
     */


    public List<String> resolve(List<String> names) {

        List<String> netPaths = new ArrayList<String>();

        for (String name : names) {
            //判断是ip或主机名
            //主机名的情况
            if (name.startsWith("s")) {
                //获取后缀s101 -> 101
                Integer prefix = Integer.parseInt(name.substring(1));
                if (prefix >= 104) {
                    netPaths.add("/rack2");
                } else {
                    netPaths.add("/rack1");
                }

            } else {
                //获取后缀s101 -> 101
                Integer prefix = Integer.parseInt(name.substring(name.lastIndexOf(".") + 1));
                if (prefix >= 104) {
                    netPaths.add("/rack2");
                } else {
                    netPaths.add("/rack1");
                }
            }
        }
        return netPaths;


    }

    public void reloadCachedMappings() {

    }

    public void reloadCachedMappings(List<String> names) {

    }
}
