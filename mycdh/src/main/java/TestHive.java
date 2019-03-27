import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestHive {


    public static void main(String[] args) throws Exception{

        String url = "jdbc:hive2://s208:10000/big14";
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
