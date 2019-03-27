package com.oldboy.flume.interceptor;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.HostInterceptor;
import org.apache.flume.interceptor.Interceptor;

import java.util.List;
import java.util.Map;

public class MyInterceptor implements Interceptor {

    private final String keyword;
    private final String name;

    /**
     * Only {@link HostInterceptor.Builder} can build me
     */
    private MyInterceptor(String name, String keyword) {
        this.keyword = keyword;
        this.name = name;
    }

    public void initialize() {
        // no-op
    }

    /**
     * Modifies events in-place.
     */
    public Event intercept(Event event) {
        Map<String, String> headers = event.getHeaders();

        //如果name的key的value为hongyang，则屏蔽屎
        //如果是其他人，则不屏蔽
        if (headers.containsKey("name")) {
            //
            if (headers.get("name").equals(name)) {
                String word = new String(event.getBody());
                String newWord = word.replaceAll(keyword, "*");
                event.setBody(newWord.getBytes());
            }
        }
        //匿名用户不屏蔽
        return event;
    }

    /**
     * Delegates to {@link #intercept(Event)} in a loop.
     *
     * @param events
     * @return
     */
    public List<Event> intercept(List<Event> events) {
        for (Event event : events) {
            intercept(event);
        }
        return events;
    }

    public void close() {
        // no-op
    }

    /**
     * Builder which builds new instance of the StaticInterceptor.
     */
    public static class Builder implements Interceptor.Builder {
        private String name;
        private String keyword;

        @Override
        public void configure(Context context) {
            name = context.getString(Constants.NAME, Constants.NAME_DEFAULT);
            keyword = context.getString(Constants.KEY, Constants.KEY_DEFAULT);
        }

        @Override
        public Interceptor build() {

            return new MyInterceptor(name, keyword);
        }

    }

    public static class Constants {
        public static final String KEY = "keyword";
        public static final String KEY_DEFAULT = "江泽民";

        public static final String NAME = "name";
        public static final String NAME_DEFAULT = "tom";

    }
}