package com.oldboy.flume.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.HostInterceptor;
import org.apache.flume.interceptor.Interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TemptagsInterceptor implements Interceptor {


    /**
     * Only {@link HostInterceptor.Builder} can build me
     */
    private TemptagsInterceptor() {

    }

    public void initialize() {
        // no-op
    }

    /**
     * Modifies events in-place.
     */
    public Event intercept(Event event) {

        if (tagExist(event)) {
            return event;
        }
        return null;
    }


    /**
     * value为空的数据不输出
     * 第一种方法：events.remove(i);      //看似简单，实现较难
     * 第二种方法：使用空list承接events中的非空值并返回
     *
     * @param events
     * @return
     */
    public List<Event> intercept(List<Event> events) {

        List<Event> list = new ArrayList<>();

        for (Event event : events) {
            if (intercept(event) != null) {
                list.add(event);
            }
        }
        events.clear();
        return list;
    }

    public void close() {
        // no-op
    }

    /**
     * Builder which builds new instance of the StaticInterceptor.
     */
    public static class Builder implements Interceptor.Builder {

        @Override
        public void configure(Context context) {

        }

        @Override
        public Interceptor build() {

            return new TemptagsInterceptor();
        }

    }

    //解析event，如果event中含有tag，返回true，否则false
    private boolean tagExist(Event event) {

        String line = new String(event.getBody());
        String json = line.split("\t")[1];


        JSONObject jo = JSON.parseObject(json);

        JSONArray jArray = jo.getJSONArray("extInfoList");

        if (jArray != null && jArray.size() > 0) {
            JSONObject jo2 = (JSONObject) jArray.get(0);
            JSONArray jArray2 = jo2.getJSONArray("values");
            if (jArray2 != null && jArray2.size() > 0) {
                return true;
            }
        }
        return false;
    }
}