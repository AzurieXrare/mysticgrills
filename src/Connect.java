import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connect implements AutoCloseable {
    private final String USERNAME = "root";
    private final String PASSWORD = "";
    private final String DATABASE = "mysticgrills";
    private final String HOST = "localhost:3306";
    private final String CONNECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);

    private Connection con;
    private ResultSet rs;
    private Statement st;
    private PreparedStatement ps;

    private static Connect connect;

    private Connect() {
        try {
            con = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
            st = con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to connect to the database, the system is terminated!");
            System.exit(0);
        }
    }

    public static synchronized Connect getConnection() {
        if (connect == null) {
            synchronized (Connect.class) {
                if (connect == null) connect = new Connect();
            }
        }
        return connect;
    }

    // retrieve data from db using query
    public ResultSet executeStatementQuery(String query) {
        rs = null;
        try {
            rs = st.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    // retrieve data using prepare statement
    public PreparedStatement prepare(String query) {
        PreparedStatement s = null;
        try {
            s = con.prepareStatement(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return s;
    }
    

    // execute prepared statement update
    public void executePreparedStatementUpdate(PreparedStatement ps) {
        try {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // manipulate statement update
    public void executeStatementUpdate(String query) {
        try {
            st.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // register user
    public void registerUser(String username, String email, String password) {
        String query = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePreparedStatement();
        }
    }

    private void closePreparedStatement() {
        // Close PreparedStatement in a separate method
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void close() throws Exception {
        // Close the connection in the AutoCloseable interface's close method
        if (con != null && !con.isClosed()) {
            con.close();
        }
    }

    public PreparedStatement prepared(String query, int returnGeneratedKeys) {
        PreparedStatement ps = null;
        try {
            if (returnGeneratedKeys == Statement.RETURN_GENERATED_KEYS) {
                ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            } else {
                ps = con.prepareStatement(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ps;
    }
    
 // retrieve data using prepare statement
    public PreparedStatement prepare1(String query) {
        PreparedStatement s = null;
        try {
            s = con.prepareStatement(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return s;
    }

    

}
