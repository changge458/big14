--
use umeng;

-- 建表
create table if not exists dws_app_new_user
(
  app_id string,
  app_version string,
  day string,
  month string,
  count    bigint,
  group_id int
) stored as orc;

-- 转储
insert into dws_app_new_user
select appid, appversion, fday, fmonth, count(*), grouping__id
from (select appid,
             appversion,
             from_unixtime(cast(substr(ftime, 1, 10) as bigint), 'yyyyMMdd') as fday,
             from_unixtime(cast(substr(ftime, 1, 10) as bigint), 'yyyyMM')   as fmonth
      from (select device_id, appid, appversion, min(cast(ftime as bigint)) as ftime
            from (select device_id, xxx.appid, xxx.appversion, xxx.ftime, xxx.ltime
                  from temp_visit_info lateral view parsehbasejson(ftime, ltime) xxx as appid, appversion, ftime, ltime) a
            group by device_id, appid, appversion) b) c
group by appid, appversion, fday,
         fmonth grouping sets((appid, appversion, fday), (appid, appversion, fmonth), (appid, fday), (appid, fmonth))
order by grouping__id;
