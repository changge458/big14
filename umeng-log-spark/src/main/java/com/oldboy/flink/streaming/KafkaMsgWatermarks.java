package com.oldboy.flink.streaming;

import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;

import javax.annotation.Nullable;

public class KafkaMsgWatermarks implements AssignerWithPeriodicWatermarks<String> {

    private static final long serialVersionUID = -742759155861320823L;

    private long currentTimestamp = Long.MIN_VALUE;

    @Override
    public long extractTimestamp(String element, long previousElementTimestamp) {
        return System.currentTimeMillis();
    }

    @Nullable
    @Override
    public Watermark getCurrentWatermark() {
        return new Watermark(currentTimestamp == Long.MIN_VALUE ? Long.MIN_VALUE : currentTimestamp - 1);
    }

}
