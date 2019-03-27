import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class TestHadoop {
    public static void main(String[] args) throws Exception {

        System.setProperty("HADOOP_USER_NAME","hdfs");
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","hdfs://s203:8020/");

        FileSystem fs = FileSystem.get(conf);


        FSDataOutputStream fos = fs.create(new Path("/a/b/1.txt"));

        fos.write("helloworld".getBytes());

        fos.close();
        System.out.println("ok");



    }
}
