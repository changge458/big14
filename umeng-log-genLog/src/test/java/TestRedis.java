import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import org.junit.Test;

import java.text.DecimalFormat;

public class TestRedis {

    public static void main(String[] args) throws Exception {

        //初始化客户端
        RedisClient client = RedisClient.create("redis://s101:6379");

        //获取连接
        StatefulRedisConnection<String, String> connect = client.connect();

        //使用异步方式访问
        RedisAsyncCommands<String, String> commands = connect.async();

        //String s = commands.set("key1", "value1").get();

        String s = commands.get("key1").get();

        System.out.println(s);

    }

    @Test
    public void testDecimal(){

        int i = 10;

        DecimalFormat df = new DecimalFormat("00000");

        String format = df.format(i);

        System.out.println(format);


    }




}
