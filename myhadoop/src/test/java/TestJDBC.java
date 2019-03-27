import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class TestJDBC {

    @Test
    public void testInsert() throws Exception{

        Class.forName("com.mysql.jdbc.Driver");

        String url = "jdbc:mysql://localhost:3306/big14";

        String username = "root";

        String password = "root";

        Connection conn = DriverManager.getConnection(url, username, password);

        //sql预处理
        PreparedStatement ppst = conn.prepareStatement("insert into temp values(?)");

        //读取所有文件
        File f = new File("D:/teaching/temp2");
        File[] files = f.listFiles();
        for (File file : files) {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = null;
            while((line = br.readLine()) != null){
                ppst.setString(1,line);
                ppst.execute();
            }
            br.close();
        }
        ppst.close();
        conn.close();
    }
}
