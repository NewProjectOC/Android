package com.example.lenovo.goods;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mysql.jdbc.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RegisterMain extends MysqlOperator {
    private Button button_back,button_register;
    private EditText username,password,repassword,phone,name;
    private RadioButton female,male;
    private RadioGroup sex;
    public int i=0,j=2;
    String Username;
    public String un,pw,rpw,ph,na,se="男";

    @Override
    public void SelectSql(String sql) throws java.sql.SQLException {
        com.mysql.jdbc.Connection con = getConnection();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            // 此处为你对该结果集的操作
            while(rs.next()) {
                Username=rs.getString(1);
                if(Username!=null)
                {
                    if(Username.equals(un))
                    {
                        Looper.prepare();
                        Toast.makeText(RegisterMain.this, "该用户名已存在!", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                        break;
                    }
                }
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


    public void InsertSql_M(String sql) throws java.sql.SQLException {
        com.mysql.jdbc.Connection con = getConnection();
        try {
            Statement stmt = con.createStatement();
            int num = stmt.executeUpdate(sql);
            if(num != 0) {
                Looper.prepare();
                Toast.makeText(RegisterMain.this, "注册成功!", Toast.LENGTH_SHORT).show();
                Intent mainIntent = new Intent(RegisterMain.this, LoginMain.class);
                startActivity(mainIntent);
                Looper.loop();
            }
            else {
                Looper.prepare();
                Toast.makeText(RegisterMain.this, "注册失败!", Toast.LENGTH_SHORT).show();
                Looper.loop();
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
        setContentView(R.layout.activity_register_main);

        button_back=findViewById(R.id.back);
        button_register=findViewById(R.id.register);
        username=findViewById(R.id.username_msg);
        password=findViewById(R.id.pwd_msg);
        repassword=findViewById(R.id.rpwd_msg);
        phone=findViewById(R.id.phone);
        sex=findViewById(R.id.rg_sex);
        name=findViewById(R.id.name_msg);
        female=findViewById(R.id.sex_female);
        male=findViewById(R.id.sex_male);

        sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.sex_female:
                        se="女";
                        break;
                    case R.id.sex_male:
                        se="男";
                        break;
                }
            }
        });

        button_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button_register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(username.length()!=0&&password.length()!=0&&repassword.length()!=0&&phone.length()!=0&&name.length()!=0)
                {
                    un=username.getText().toString();
                    pw=password.getText().toString();
                    rpw=repassword.getText().toString();
                    ph=phone.getText().toString();
                    na=name.getText().toString();
                    if(pw.equals(rpw)==false){
                        Toast.makeText(RegisterMain.this, "两次密码不一致!", Toast.LENGTH_SHORT).show();
                    }
                    new Thread(runnable).start();
                }
                else
                {
                    Toast.makeText(RegisterMain.this, "请填写完整信息!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    Runnable runnable = new Runnable() {
        private Connection con = null;

        @Override
        public void run() {
            // TODO Auto-generated method stub
            String sql1="select * from users";
            try {
                SelectSql(sql1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(Username==null||Username.equals(un)!=true)
            {
                String sql2="insert into users(username,password,name,sex,phone) values ('"+un+"','"+pw+"','"+na+"','"+se+"','"+ph+"')";
                try {
                    InsertSql_M(sql2);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    };

}
