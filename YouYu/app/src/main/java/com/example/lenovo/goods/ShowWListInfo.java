package com.example.lenovo.goods;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowWListInfo extends AppCompatActivity {

    private Context context = ShowWListInfo.this;
    Map<String, Object> map = new HashMap<>();
    private String username;
    private int locatTid;
    private TextView tvname;
    private TextView tvphone;
    private TextView tvtitle;
    private TextView tvcontent;
    private TextView tvmoney;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_wlist_info);
        Bundle bundle = this.getIntent().getExtras();
        tvname = findViewById(R.id.name);
        tvphone = findViewById(R.id.phone);
        tvtitle = findViewById(R.id.title);
        tvcontent = findViewById(R.id.content);
        tvmoney = findViewById(R.id.money);

        username = bundle.getString("username");
        locatTid = bundle.getInt("Tid");
        new MyAsyncTask().execute();
    }

    public void backToMainPage(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        Intent intent = new Intent(ShowWListInfo.this, MainPage.class);
        intent.putExtras(bundle);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        startActivity(intent);
    }

    public class MyAsyncTask extends AsyncTask<Void, Void, Map<String, Object>> {

        @Override
        protected Map<String, Object> doInBackground(Void... voids) {
            String sql = "select * from WList where Tid = '"+locatTid+"';";
            Connection con = getConnection();
            try {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    String name = rs.getString(2);
                    String title = rs.getString(3);
                    String content = rs.getString(4);
                    String phone = rs.getString(5);
                    String money = rs.getString(6);
                    map.put("name",name);
                    map.put("title",title);
                    map.put("content",content);
                    map.put("phone",phone);
                    map.put("money",money);
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
            return map;
        }

        @Override
        protected void onPostExecute(Map<String, Object> map) {
            tvname.setText(map.get("name").toString());
            tvtitle.setText(map.get("title").toString());
            tvcontent.setText(map.get("content").toString());
            tvphone.setText(map.get("phone").toString());
            tvmoney.setText(map.get("money").toString());
        }

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
    }
}
