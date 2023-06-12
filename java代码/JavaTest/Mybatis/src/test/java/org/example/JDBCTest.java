package org.example;

import Utils.JDBC;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author: beiyuan
 * @className: JDBCTest
 * @date: 2022/6/13  22:06
 */
public class JDBCTest {
    @Test
    public void testJDBC(){
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;

        try {
            con=JDBC.getConnection();
            String sql="select * from st";
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            while (rs.next()){
                String email=rs.getString("email");
                System.out.println(email);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBC.clos1e(con,ps,rs);
        }


    }
}
