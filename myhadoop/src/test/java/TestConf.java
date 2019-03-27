import org.apache.hadoop.conf.Configuration;

public class TestConf {

    public static void main(String[] args) {

        Configuration conf = new Configuration();
        String s = conf.get("name", "xxx");

        System.out.println((-100)%3);

        String str = "0123456789";
        char[] chars = str.toCharArray();
        for (char c : chars) {
            int intNum = c;
            System.out.println(intNum);
        }


    }


}
