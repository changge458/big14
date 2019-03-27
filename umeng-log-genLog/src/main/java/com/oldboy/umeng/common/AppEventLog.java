package com.oldboy.umeng.common;

import java.util.Map;

/**
 * 应用上报的事件相关信息
 */
public class AppEventLog extends AppBaseLog {

    private String eventId;                                 //事件唯一标识
    private String eventDurationSecs;                       //事件持续时长
    private Map<String, String> paramKeyValueMap;           //参数名/值对


    public AppEventLog() {
        setLogType(LOGTYPE_EVENT);
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventDurationSecs() {
        return eventDurationSecs;
    }

    public void setEventDurationSecs(String eventDurationSecs) {
        this.eventDurationSecs = eventDurationSecs;
    }

    public Map<String, String> getParamKeyValueMap() {
        return paramKeyValueMap;
    }

    public void setParamKeyValueMap(Map<String, String> paramKeyValueMap) {
        this.paramKeyValueMap = paramKeyValueMap;
    }
    //getter/setter

}
