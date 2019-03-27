import org.apache.flink.api.java.utils.ParameterTool;

public class TestParamTools {

    public static void main(String[] args) {

        args = new String[]{"--topic", "test","--group.id","g1"};

        ParameterTool params = ParameterTool.fromArgs(args);

        String topic = params.get("group.id");

        System.out.println(topic);



    }
}
