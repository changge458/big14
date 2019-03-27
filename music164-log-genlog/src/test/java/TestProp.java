import com.oldboy.umeng.constant.Constants;
import com.oldboy.umeng.util.PropUtil;

public class TestProp {
    public static void main(String[] args) {

        String va = PropUtil.getValue(Constants.JDBC_DRIVER);
        System.out.println(va);


    }

}
