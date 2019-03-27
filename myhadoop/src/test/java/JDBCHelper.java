import java.sql.Connection;
import java.sql.DriverManager;
import java.util.LinkedList;

public class JDBCHelper {


    private static JDBCHelper instance = null;

    public static JDBCHelper getInstance() {

        if (instance == null) {
            synchronized (JDBCHelper.class) {

                if (instance == null) {
                    instance = new JDBCHelper();

                }
            }

        }
        return instance;
    }

    private LinkedList<Connection> datasource = new LinkedList<Connection>();


    private JDBCHelper() {
        int datasourceSize = ConfigManager.getInteger(Constants.JDBC_DATASOURCE_SIZE);
        String driver = ConfigManager.getProp(Constants.JDBC_DRIVER);
        String url = ConfigManager.getProp(Constants.JDBC_URL);
        String user = ConfigManager.getProp(Constants.JDBC_USER);
        String pass = ConfigManager.getProp(Constants.JDBC_PASS);

        for (int i = 0; i < datasourceSize; i++) {
            try {
                Connection conn = DriverManager.getConnection(url, user, pass);
                datasource.push(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized Connection getConnection() {
        while (datasource.size() == 0) {
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return datasource.poll();

    }


}
