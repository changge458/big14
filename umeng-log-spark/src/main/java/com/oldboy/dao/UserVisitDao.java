package com.oldboy.dao;

import com.oldboy.domain.StartupLog;
import com.oldboy.domain.UserVisit;
import com.oldboy.util.HbaseUtil;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserVisitDao {


    public static UserVisit getUserVisit(StartupLog startupLog) {

        String deviceId = startupLog.getDeviceId();
        String timestamp = startupLog.getCreatedAtMs();
        String appId = startupLog.getAppId();


        Long ts = Long.valueOf(timestamp);


        String firstvisittime = HbaseUtil.get("umeng:uservisit", deviceId, "time", appId+"_firstvisittime");
        String lastvisittime = HbaseUtil.get("umeng:uservisit", deviceId, "time", appId+"_lastvisittime");

        //此deviceid第一次访问hbase
        //timestamp为第一次和最后一次访问
        if (firstvisittime == null) {
            firstvisittime = timestamp;
            HbaseUtil.put("umeng:uservisit", deviceId, "time", appId+"_firstvisittime", firstvisittime);
        }
        if (lastvisittime == null) {
            lastvisittime = timestamp;
            HbaseUtil.put("umeng:uservisit", deviceId, "time", appId+"_lastvisittime", lastvisittime);
        } else {

            long firstvisittimestamp = Long.valueOf(firstvisittime);
            long lastvisittimestamp = Long.valueOf(lastvisittime);

            if (ts < firstvisittimestamp) {
                firstvisittime = timestamp;
                HbaseUtil.put("umeng:uservisit", deviceId, "time", appId+"_firstvisittime", firstvisittime);
            }
            if (ts > lastvisittimestamp) {
                lastvisittime = timestamp;
                HbaseUtil.put("umeng:uservisit", deviceId, "time", appId+"_lastvisittime", lastvisittime);
            }

        }

        UserVisit visit = new UserVisit();
        visit.setDevId(deviceId);
        visit.setFirstVisitTime(firstvisittime);
        visit.setLastVisitTime(lastvisittime);

        return visit;

    }


}

