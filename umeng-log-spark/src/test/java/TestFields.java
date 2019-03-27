

import com.oldboy.domain.StartupLog;

import java.lang.reflect.Field;

public class TestFields {
    public static void main(String[] args) {



        Class clazz = StartupLog.class;
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            System.out.println(field.getName());
        }


    }


}
