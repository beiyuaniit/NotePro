package Utils;

import java.sql.*;
import java.util.ResourceBundle;

public class JDBC {
    //jdbc.properties还是放在src下。不然真的找不到了
    private static ResourceBundle bundle = ResourceBundle.getBundle("mysql");

    private static String driver = bundle.getString("driver");
    private static String url = bundle.getString("url");
    private static String user = bundle.getString("username");
    private static String password = bundle.getString("password");

    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection con = DriverManager.getConnection(url, user, password);
        return con;
    }


    //释放资源
    public static void clos1e(Connection con, Statement ps, ResultSet rs) {
        if (rs != null) {//先释放小的
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
