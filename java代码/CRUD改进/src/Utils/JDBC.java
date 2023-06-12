package Utils;

import java.sql.*;
import java.util.ResourceBundle;

/**
 * @author: beiyuan
 * @className: JDBC
 * @date: 2022/3/27  17:49
 */
public class JDBC {

    //Resource是包，可以用Resource.jdbc
    private static ResourceBundle bundle = ResourceBundle.getBundle("jdbc");

    private static String driver = bundle.getString("driver");
    private static String url = bundle.getString("url");
    private static String user = bundle.getString("user");
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
    public static void close(Connection con, Statement ps, ResultSet rs) {
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


