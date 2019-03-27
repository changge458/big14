import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TestJDBC {


    public static void main(String[] args){

        String url = "jdbc:mysql://localhost:3306/big14";
        String username = "root";
        String password = "root";

        Connection conn = null;
        Statement st = null;
        try {
            conn = DriverManager.getConnection(url,username,password);
            st = conn.createStatement();



        //st.execute("create table xxx(id int, name varchar(20), age int )");

        st.execute("insert into yyy values(1,'tom')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                st.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }




    }



}
