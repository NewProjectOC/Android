package com.example.lenovo.goods;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mysql.jdbc.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginMain extends MysqlOperator {

    private Button button_Register, button_login;
    private EditText input_username,input_password;

    public String username;
    public String password;
    public String tvusername;
    public String tvpassword;
    public String usingusername;


    public void SelectSql1(String sql1) throws java.sql.SQLException {
        com.mysql.jdbc.Connection con = getConnection();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs1 = stmt.executeQuery(sql1);
            // 此处为你对该结果集的操作
            if (rs1.next()) {
                usingusername=rs1.getString(1);
                Looper.prepare();
                Toast.makeText(LoginMain.this, "该用户已登陆", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
            rs1.close();
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

    @Override
    public void SelectSql(String sql) throws java.sql.SQLException {
        com.mysql.jdbc.Connection con = getConnection();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            // 此处为你对该结果集的操作
            if(rs.next())
            {
                tvusername=rs.getString(1);
                tvpassword=rs.getString(2);
                if(tvpassword.equals(password)) {

                    String sql1 = "select * from used where username='"+username+"'";       //查询表名为“users”的所有内容
                    try {
                        SelectSql1(sql1);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    String sql2="insert into used(username) values ('"+tvusername+"')";
                    try {
                        InsertSql_M(sql2);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString("username", username);
                    Intent intent2 = new Intent(LoginMain.this, MainPage.class);
                    intent2.putExtras(bundle);
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    startActivity(intent2);
                }
                else
                {
                    Looper.prepare();
                    Toast.makeText(LoginMain.this, "密码错误", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }

            }
            else {
                Looper.prepare();
                Toast.makeText(LoginMain.this, "用户名不存在", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
            rs.close();
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

    public void InsertSql_M(String sql) throws java.sql.SQLException {
        com.mysql.jdbc.Connection con = getConnection();
        try {
            Statement stmt = con.createStatement();
            int num = stmt.executeUpdate(sql);
            if(num != 0) {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);


        button_Register = findViewById(R.id.register);
        button_login = findViewById(R.id.login);
        input_username= findViewById(R.id.username);
        input_password=findViewById(R.id.password);

        button_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(LoginMain.this, RegisterMain.class);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                startActivity(intent1);
            }
        });

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(input_password.length()!=0&&input_username.length()!=0)
                {
                    username=input_username.getText().toString();
                    password=input_password.getText().toString();
                    new Thread(runnable).start();
                }
                else {
                    Toast.makeText(LoginMain.this, "用户名或密码为空", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    Runnable runnable = new Runnable() {
        private Connection con = null;

        @Override
        public void run() {
            // TODO Auto-generated method stub
            String sql = "select * from users where username='"+username+"'";       //查询表名为“users”的所有内容
            try {
                SelectSql(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };
}
