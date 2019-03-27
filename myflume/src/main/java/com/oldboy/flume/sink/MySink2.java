package com.oldboy.flume.sink;

import org.apache.flume.*;
import org.apache.flume.conf.Configurable;
import org.apache.flume.sink.AbstractSink;

import java.util.Map;
import java.util.Set;

public class MySink2 extends AbstractSink implements Configurable {


    @Override
    public synchronized void start() {
        super.start();
    }

    //向配置文件中获取配置属性
    public void configure(Context context) {
    }

    //处理数据
    public Status process() throws EventDeliveryException {
        Status result = Status.READY;
        Channel channel = getChannel();
        Transaction transaction = channel.getTransaction();
        Event event = null;

        try {
            transaction.begin();
            event = channel.take();

            if (event != null) {

                String words = new String(event.getBody());
                Map<String, String> headers = event.getHeaders();

                Set<Map.Entry<String, String>> entrySet = headers.entrySet();

                for (Map.Entry<String, String> entry : entrySet) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    System.out.println("[" + key+":" + value + "]: " + words);
                }

            } else {
                // No event found, request back-off semantics from the sink runner
                result = Status.BACKOFF;
            }
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            throw new EventDeliveryException("Failed to log event: " + event, ex);
        } finally {
            transaction.close();
        }
        return result;
    }

    @Override
    public synchronized void stop() {
        super.stop();
    }
}
