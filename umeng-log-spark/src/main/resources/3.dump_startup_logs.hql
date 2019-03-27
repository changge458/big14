-- 设置hive分区非严格模式
set hive.exec.dynamic.partition.mode=nonstrict;
SET hive.exec.mode.local.auto=true;
SET hive.exec.mode.local.auto.inputbytes.max=500000000;
SET hive.exec.mode.local.auto.input.files.max=50;

-- 正式转储
insert overwrite table DW_LOG_UMENG_STARTUP partition(year,month, day)
select
    a.DEVICE_ID,
    a.DEVICE_MODEL,
    a.DEVICE_SCREENSIZE,
    a.DEVICE_CARRIER,
    a.CLIENT_IP,
    a.CLIENT_COUNTRY,
    a.CLIENT_PROVINCE,
    a.CLIENT_TIME,
    a.SERVER_TIME,
    a.LOG_CREATE_TIME,
    a.CLIENT_NETWORK,
    a.APP_VERSION,
    a.APP_STORE,
    a.APP_PLATFORM,
    a.DEVICE_OSTYPE,
    a.APP_ID,
    a.DEVICE_BRAND,
    a.TENANT_ID,
    from_unixtime(cast(substr(log_create_time,1,10) as bigint),'yyyy'),
    from_unixtime(cast(substr(log_create_time,1,10) as bigint),'MM'),
    from_unixtime(cast(substr(log_create_time,1,10) as bigint),'dd')
from
    (select parseStartup(line) from ODS_UMENG_APP) a;