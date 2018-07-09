package com.example.lenovo.goods;

import android.os.Bundle;
import android.os.Message;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Messages;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class MysqlOperatorSon extends MysqlOperator {
    public myAccount myAccountSon = new myAccount();

    @Override
    public void SelectSql(String sql) throws java.sql.SQLException
    {
        Connection con = getConnection();
        System.out.println("66666677777777777777777777");
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            // 此处为你对该结果集的操作
            while(rs.next())
            {
                Message msg = new Message();
                Bundle bundle_select = new Bundle();
                bundle_select.putString("nameAccount",rs.getString(1));
                msg.setData(bundle_select);
                myAccountSon.myHandler.sendMessage(msg);
            }
            stmt.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (con != null)
            {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
