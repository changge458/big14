import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

public class TestJson {

    //{"name":"tom","age":20}

    @Test
    public void test1(){

        String str = "{\"name\":\"tom\",\"age\":20}";

        //将json文本转换成json对象
        JSONObject jo = JSON.parseObject(str);

        Object name = jo.get("name");

        System.out.println(name);


    }

    @Test
    public void test2(){
        String str = "{\"boss\":\"赵洪洋\",\"age\":20,\"wife\":[\"feng\",\"bing\",\"lu\"]}";
        JSONObject jo = JSON.parseObject(str);

        Object name = jo.get("boss");
        JSONArray wife = jo.getJSONArray("wife");
        for (Object o : wife) {
            System.out.println(name + ":" + o);
        }
    }

    @Test
    public void test3(){
        String str = "{\"boss\":\"赵洪洋\",\"age\":20,\"wife\":[\"feng\",\"bing\",\"lu\"],\"employee\":[{\"name\":\"gang\",\"age\":20,\"wife\":\"yui\"},{\"name\":\"wu\",\"age\":21,\"wife\":\"kong\"},{\"name\":\"bao\",\"age\":22,\"wife\":\"niu\"}]}";

        //找出boss的第一个wife和第一个employee的所有信息
        JSONObject jo = JSON.parseObject(str);

        Object boss = jo.get("boss");

        Object wife1 = jo.getJSONArray("wife").get(0);

        JSONObject employee = (JSONObject)jo.getJSONArray("employee").get(0);

        Object name = employee.get("name");
        Object age = employee.get("age");
        Object wife = employee.get("wife");

        System.out.println(boss + "/" + wife1 + "/" + name + "/" + age + "/" +  wife);

    }


}
