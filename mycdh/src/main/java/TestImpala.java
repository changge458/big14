import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestImpala {


    public static void main(String[] args) throws Exception{

        String url = "jdbc:hive2://192.168.15.208:21050/big14;auth=noSasl";
        String user = "hive";
        String passwd = "";

        Connection conn = DriverManager.getConnection(url,user,passwd);

        Statement st = conn.createStatement();

        ResultSet rs = st.executeQuery("select pass,count(*) as c from big14.duowan_user group by pass order by c desc limit 100");
        
        while (rs.next()){
            String pass = rs.getString(1);
            int count = rs.getInt(2);
            System.out.println(pass + "\t" + count);
        }
        st.close();

        conn.close();


    }

}
