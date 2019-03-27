import com.hadoop.compression.lzo.LzoIndexer;
import org.apache.hadoop.conf.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;

public class TestIP {
    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new FileReader("H:\\X-Data\\aiwen_free_district_v2.0.3.txt"));

        String line = null;

        for (int i = 0; i < 10000; i++) {

            System.out.println(br.readLine());

        }
        System.out.println(0 / 0);

//        LzoIndexer indexer = new LzoIndexer(new Configuration());
//        indexer.index();


    }
}
