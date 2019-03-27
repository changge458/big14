use umeng;

-- 将jar添加到hive临时类路径
add jar hdfs://mycluster/umeng_jars/fastjson-1.2.54.jar;
add jar hdfs://mycluster/umeng_jars/maxmind-db-1.1.0.jar;
add jar hdfs://mycluster/umeng_jars/mysql-connector-java-5.1.44.jar;
add jar hdfs://mycluster/umeng_jars/umeng-log-parseLog.jar;
add jar hdfs://mycluster/umeng_jars/umeng-log-hive.jar;


-- 注册函数
create temporary function parseEvent as 'com.oldboy.umeng.udtf.ParseEvent';
create temporary function parseError as 'com.oldboy.umeng.udtf.ParseError';
create temporary function parseStartup as 'com.oldboy.umeng.udtf.ParseStartup';
create temporary function parsePage as 'com.oldboy.umeng.udtf.ParsePage';
create temporary function parseUsage as 'com.oldboy.umeng.udtf.ParseUsage';
create temporary function parsehbasejson as 'com.oldboy.umeng.udtf.ParseHbaseJson';