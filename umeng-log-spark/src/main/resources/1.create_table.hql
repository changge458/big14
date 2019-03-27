create table if not exists umeng.ODS_UMENG_APP(line String) partitioned by(YEAR string,MONTH string, DAY string);

--创建error表，如果不存在的话

create  table if not exists DW_LOG_UMENG_ERROR(
    DEVICE_ID string,
    DEVICE_MODEL string,
    CLIENT_IP string,
    CLIENT_TIME string,
    SERVER_TIME string,
    LOG_CREATE_TIME string,
    ERROR_BRIEF string,
    ERROR_DETAIL string,
    APP_VERSION string,
    APP_STORE string,
    APP_PLATFORM string,
    APP_OSTYPE string,
    APP_ID string,
    DEVICE_BRAND string,
    TENANT_ID string
)partitioned by(YEAR string,MONTH string, DAY string)
stored as orc;

--创建event表，如果不存在的话

create  table if not exists DW_LOG_UMENG_EVENT(
    DEVICE_ID string,
    DEVICE_MODEL string,
    CLIENT_IP string,
    CLIENT_TIME string,
    SERVER_TIME string,
    LOG_CREATE_TIME string,
    EVENT_TYPE string,
    EVENT_DURATION string,
    APP_VERSION string,
    APP_STORE string,
    APP_PLATFORM string,
    APP_OSTYPE string,
    APP_ID string,
    DEVICE_BRAND string,
    TENANT_ID string
)partitioned by(YEAR string,MONTH string, DAY string)
stored as orc;

--创建page表，如果不存在的话

create  table if not exists DW_LOG_UMENG_PAGE(
    DEVICE_ID string,
    DEVICE_MODEL string,
    CLIENT_IP string,
    CLIENT_TIME string,
    SERVER_TIME string,
    LOG_CREATE_TIME string,
    PAGE_ID string,
    PAGE_NEXT string,
    PAGE_VIEW_CNT string,
    PAGE_DURATION string,
    PAGE_VISIT_INDEX string,
    APP_VERSION string,
    APP_STORE string,
    APP_PLATFORM string,
    APP_OSTYPE string,
    APP_ID string,
    DEVICE_BRAND string,
    TENANT_ID string
)partitioned by(YEAR string,MONTH string, DAY string)
stored as orc;

--创建startup表，如果不存在的话

create  table if not exists DW_LOG_UMENG_STARTUP(
    DEVICE_ID string,
    DEVICE_MODEL string,
    DEVICE_SCREENSIZE string,
    DEVICE_CARRIER string,
    CLIENT_IP string,
    CLIENT_COUNTRY string,
    CLIENT_PROVINCE string,
    CLIENT_TIME string,
    SERVER_TIME string,
    LOG_CREATE_TIME string,
    CLIENT_NETWORK string,
    APP_VERSION string,
    APP_STORE string,
    APP_PLATFORM string,
    APP_OSTYPE string,
    APP_ID string,
    DEVICE_BRAND string,
    TENANT_ID string
)partitioned by(YEAR string,MONTH string, DAY string)
stored as orc;

--创建usage表，如果不存在的话

create  table if not exists DW_LOG_UMENG_USAGE(
    DEVICE_ID string,
    DEVICE_MODEL string,
    CLIENT_IP string,
    CLIENT_TIME string,
    SERVER_TIME string,
    LOG_CREATE_TIME string,
    APP_VERSION string,
    APP_STORE string,
    APP_PLATFORM string,
    APP_OSTYPE string,
    ONCE_DOWNLOAD_TRAFFIC string,
    ONCE_UPLOAD_TRAFFIC string,
    ONCE_USE_DURATION string,
    APP_ID string,
    DEVICE_BRAND string,
    TENANT_ID string
)partitioned by(YEAR string,MONTH string, DAY string)
stored as orc;

