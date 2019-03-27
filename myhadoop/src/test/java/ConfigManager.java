import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

    private static Properties prop = new Properties();

    static {
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("my.properties");
            prop.load(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String getProp(String key) {
        return prop.getProperty(key);
    }

    public static Integer getInteger(String key) {
        String value = getProp(key);

        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

}
