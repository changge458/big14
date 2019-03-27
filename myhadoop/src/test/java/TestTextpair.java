import com.oldboy.hadoop.hdfs.homework.Textpair;
import org.junit.Test;

public class TestTextpair {

    @Test
    public void test1(){

        Textpair tp1 = new Textpair("tom", "tomson");
        Textpair tp2 = new Textpair("tom", "tomas");

        System.out.println( tp1.compareTo(tp2));

    }


}
