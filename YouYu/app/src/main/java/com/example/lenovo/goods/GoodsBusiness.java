package com.example.lenovo.goods;

import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.SQLException;

public class GoodsBusiness extends AppCompatActivity {

    private EditText name,phone,title,content,money;
    private ImageButton previous;
    private ImageButton submit;
    Bundle bundle,bundle1;
    private String message;
    private MysqlOperator mq;
    private String sql;
    private boolean flag,flag1,flag2;
    int Tid=0;
    private String Iusername,Iname,Iphone,Ititle,Icontent,Imoney;
    Date date = new Date();
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS");
    String str = df.format(date);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_business);

        bundle=this.getIntent().getExtras();
        Iusername=bundle.getString("username");
        bundle1=new Bundle();
        bundle1.putString("username",Iusername);
        submit=findViewById(R.id.submit);
        previous=findViewById(R.id.previous);
        name=findViewById(R.id.name);
        phone=findViewById(R.id.phone);
        title=findViewById(R.id.title);
        content=findViewById(R.id.content);
        money=findViewById(R.id.money);
        mq=new MysqlOperator();
        flag=false;
        flag1=false;
        flag2=true;
        final Thread t1 = new Thread(runnable);
        submit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(name.getText().toString().equals("")){
                    message="姓名不能为空";
                    Toast.makeText(GoodsBusiness.this,message, Toast.LENGTH_LONG).show();
                }
                else if(phone.getText().toString().equals("")){
                    message="联系方式不能为空";
                    Toast.makeText(GoodsBusiness.this,message, Toast.LENGTH_LONG).show();

                }
                else if(title.getText().toString().equals("")){
                    message="标题不能为空";
                    Toast.makeText(GoodsBusiness.this,message, Toast.LENGTH_LONG).show();

                }
                else if(content.getText().toString().equals("")){
                    message="内容不能为空";
                    Toast.makeText(GoodsBusiness.this,message, Toast.LENGTH_LONG).show();

                }
                else if(money.getText().toString().equals("")){
                    message="金额不能为空";
                    Toast.makeText(GoodsBusiness.this,message, Toast.LENGTH_LONG).show();

                }else{
                    Iname=name.getText().toString();
                    Iphone=phone.getText().toString();
                    Ititle=title.getText().toString();
                    Icontent=content.getText().toString();
                    Imoney=money.getText().toString();
                    sql="INSERT INTO TList (username,state) VALUES ('"+Iusername+"','0')";
                    t1.start();
                }
            }
        });

        previous.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent1=new Intent(GoodsBusiness.this,PublishMain.class);
                intent1.putExtras(bundle1);
                startActivity(intent1);
            }
        });
    }

    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            while(flag2){
                try {
                    flag=mq.InsertSql(sql);
                    if(flag){
                        sql="SELECT max(Tid) from TList where username='"+Iusername+"'";
                        Tid=mq.SelectCount(sql);
                        if(Tid!=0)
                        {
                            sql="INSERT INTO WList (Tid,state,name,phone,title,content,money,date) VALUES ('"+Tid+"','0','"+Iname+"','"+Iphone+"','"+Ititle+"','"+Icontent+"','"+Imoney+"','"+str+"')";
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
                Toast.makeText(GoodsBusiness.this,"提交成功", Toast.LENGTH_LONG).show();
                previous.callOnClick();
                Looper.loop();
            }
        }
    };


}
