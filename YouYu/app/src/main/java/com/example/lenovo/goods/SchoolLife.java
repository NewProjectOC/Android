package com.example.lenovo.goods;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SchoolLife extends AppCompatActivity {
    private EditText name,phone,title,place,content,time;
    private ImageButton previous;
    private ImageButton submit;
    private String message;
    private MysqlOperator mq;
    private String sql;
    private boolean flag,flag1,flag2;
    private int Tid=0;
    Bundle bundle,bundle1;
    private String Iusername,Iname,Iphone,Ititle,Iplace,Icontent,Itime;
    Date date = new Date();
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS");
    String str = df.format(date);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_life);

        submit=findViewById(R.id.submit);
        previous=findViewById(R.id.previous);
        name=findViewById(R.id.name);
        phone=findViewById(R.id.phone);
        title=findViewById(R.id.title);
        place=findViewById(R.id.place);
        content=findViewById(R.id.content);
        time=findViewById(R.id.time);
        bundle=this.getIntent().getExtras();
        Iusername=bundle.getString("username");
        bundle1=new Bundle();
        bundle1.putString("username",Iusername);
        mq=new MysqlOperator();
        flag=false;
        flag1=false;
        flag2=true;
        submit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(name.getText().toString().equals("")){
                    message="姓名不能为空";
                    Toast.makeText(SchoolLife.this,message, Toast.LENGTH_LONG).show();
                }
                else if(phone.getText().toString().equals("")){
                    message="联系方式不能为空";
                    Toast.makeText(SchoolLife.this,message, Toast.LENGTH_LONG).show();

                }
                else if(place.getText().toString().equals("")){
                    message="地点不能为空";
                    Toast.makeText(SchoolLife.this,message, Toast.LENGTH_LONG).show();

                }
                else if(content.getText().toString().equals("")){
                    message="内容不能为空";
                    Toast.makeText(SchoolLife.this,message, Toast.LENGTH_LONG).show();

                }
                else if(time.getText().toString().equals("")){
                    message="时间不能为空";
                    Toast.makeText(SchoolLife.this,message, Toast.LENGTH_LONG).show();
                }else{
                    Iname=name.getText().toString();
                    Iphone=phone.getText().toString();
                    Iplace=place.getText().toString();
                    Ititle=title.getText().toString();
                    Icontent=content.getText().toString();
                    Itime=time.getText().toString();
                    sql="INSERT INTO TList (state,username) VALUES ('1','"+Iusername+"')";
                    Thread t1 = new Thread(runnable);
                    t1.start();
                }
            }
        });
        previous.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent1=new Intent(SchoolLife.this,PublishMain.class);
                intent1.putExtras(bundle1);
                startActivity(intent1);
            }
        });
    }

    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            while (flag2){
                try {
                    flag=mq.InsertSql(sql);
                    if(flag){
                        sql="SELECT max(Tid) from TList where username='"+Iusername+"'";
                        Tid=mq.SelectCount(sql);
                        if(Tid!=0)
                        {
                            sql="INSERT INTO AList (Tid,state,name,phone,title,place,content,time,date) VALUES ('"+Tid+"','1','"+Iname+"','"+Iphone+"','"+Ititle+"','"+Iplace+"','"+Icontent+"','"+Itime+"','"+str+"')";
                            flag1=mq.InsertSql(sql);
                            flag2=false;
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(flag1){
                Looper.prepare();
                Toast.makeText(SchoolLife.this,"提交成功", Toast.LENGTH_LONG).show();
                previous.callOnClick();
                Looper.loop();

            }
        }
    };
}
