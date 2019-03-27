import com.alibaba.fastjson.JSON;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class TestJson {

    public static void main(String[] args) throws Exception {

        String[] wife = {"hongyang", "jinbao", "dugang", "wenwu"};

        //Person p = new Person(1,"tom",20,wife);

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("id", 1);
        map.put("name", "tom");
        map.put("age", 20);
        map.put("wife", wife);


        Class clazz = Person.class;
        Object o = clazz.newInstance();

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            field.set(o, map.get(field.getName()));
        }
        System.out.println(JSON.toJSONString(o, true));
    }
}
