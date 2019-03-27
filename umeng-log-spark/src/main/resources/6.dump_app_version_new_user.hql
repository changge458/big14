--
use umeng;

-- 创建表
create table app_version_new_user
(
  app_id string,
  app_version string,
  day string,
  month string,
  count    bigint,
  group_id int
);

-- 从dws层转储
insert into app_version_new_user
select app_id,
       app_version,
       day,
       month,
       count,
       group_id
from dws_app_new_user
where app_version is not null;