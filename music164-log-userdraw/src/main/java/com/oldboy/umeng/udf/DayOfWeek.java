package com.oldboy.umeng.udf;

import org.apache.hadoop.hive.ql.exec.UDF;

import java.util.Calendar;
import java.util.Date;

public class DayOfWeek extends UDF {

    //1543631040000

    //将时间戳转换成周几
    public String evaluate(String time) {

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(new Date(Long.parseLong(time)));

        int i = calendar.get(Calendar.DAY_OF_WEEK);

        if (i == 1 || i == 7) {
            return "week_time";
        } else {
            return "work_time";
        }


    }

    public static void main(String[] args) {

        String evaluate = new DayOfWeek().evaluate("1543631040000");
        System.out.println(evaluate);
    }


}
