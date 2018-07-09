package com.example.lenovo.goods;

import android.support.v7.app.AppCompatActivity;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.mysql.jdbc.Connection;

public class MysqlOperator extends AppCompatActivity{
    public Connection getConnection() {
        Connection con = null;
        try {
            String UserName = "root"; // 连接数据库的用户名
            String Password = "Haohao@970"; // 连接数据库的密码
            Class.forName("com.mysql.jdbc.Driver"); // 装载jdbc驱动
            con = (Connection) DriverManager.getConnection("jdbc:mysql://47.106.34.165/test?useUnicode=true&characterEncoding=utf-8", UserName, Password); // 建立连接
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        return con;
    }

    public boolean InsertSql(String sql) throws java.sql.SQLException {
        Connection con = getConnection();
        boolean result=false;
        try {
            Statement stmt = con.createStatement();
            int num = stmt.executeUpdate(sql);
            if(num != 0) {
                System.out.println("insert successfully");
                result=true;
            }
            else {
                System.out.println("insert failed");
                result=false;
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return result;
        }
    }

    public void DeleteSql(String sql) throws java.sql.SQLException {
        Connection con = getConnection();
        try {
            Statement stmt = con.createStatement();
            int num = stmt.executeUpdate(sql);
            if(num != 0) {
                System.out.println("delete successfully");
            }
            else {
                System.out.println("delete failed");
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void UpdateSql(String sql) throws java.sql.SQLException {
        Connection con = getConnection();
        try {
            Statement stmt = con.createStatement();
            int num = stmt.executeUpdate(sql);
            if(num != 0) {
                System.out.println("update successfully");
            }
            else {
                System.out.println("update failed");
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void SelectSql(String sql) throws java.sql.SQLException {
        Connection con = getConnection();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            // 此处为你对该结果集的操作
            while(rs.next()) {}
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public int SelectCount(String sql) throws java.sql.SQLException {
        Connection con = getConnection();
        int i=0;
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            // 此处为你对该结果集的操作
            while(rs.next()) {
                i=rs.getInt(1);
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return i;
    }
}
