package com.example.lenovo.goods;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class PublishMain extends AppCompatActivity {

    private ImageButton submit;
    private Spinner  sp;
    private int index;
    private String username="ccc";
    //    private Bundle bundle1=this.getIntent().getExtras();
    private Bundle bundle2=new Bundle();
    private ImageButton home_btn,post_btn,person_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_main);

        submit=findViewById(R.id.submit);
        home_btn = findViewById(R.id.home_btn);
        post_btn = findViewById(R.id.post_btn);
        person_btn = findViewById(R.id.person_btn);

        home_btn.setOnClickListener(listener);
        post_btn.setOnClickListener(listener);
        person_btn.setOnClickListener(listener);

        receiveMsg();

        sp =  findViewById(R.id.list_item);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                index = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        submit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                bundle2.putString("username",username);
                if(index==0){
                    Intent intent=new Intent(PublishMain.this,GoodsBusiness.class);
                    intent.putExtras(bundle2);
                    startActivity(intent);
                }
                else if(index==1){
                    Intent intent=new Intent(PublishMain.this,SchoolLife.class);
                    intent.putExtras(bundle2);
                    startActivity(intent);
                }
                /*                    Toast.makeText(MainActivity.this,index+"",Toast.LENGTH_LONG).show();*/
            }
        });


    }

    //接收从主界面传来的用户名
    public void receiveMsg()
    {
        Bundle bundle_name = this.getIntent().getExtras();
        //text_username.setText(String.format("用户名：%s ",bundle_name.getString("username")));
        username = bundle_name.getString("username");
        System.out.println("GOODS 777777777publish 接收 username  = "+username);
    }


    private final View.OnClickListener listener = new View.OnClickListener() {


        @Override
        public void onClick(View v) {
            // ImageViewPlus btn = (ImageViewPlus) v;
            switch (v.getId()) {
                case R.id.home_btn:           //跳转到主界面

                    System.out.println("GOODS 777777777MainPage 发布 username  = "+username);

                    Bundle bundle = new Bundle();
                    bundle.putString("username", username);
                    Intent intent2 = new Intent(PublishMain.this, MainPage.class);
                    intent2.putExtras(bundle);
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    startActivity(intent2);

                    break;
                case R.id.post_btn:   //跳转到发布界面



                    break;
                case R.id.person_btn:   //跳转到个人中心

                    System.out.println("GOODS 777777777MainPage 发布 username  = "+username);
                    Bundle bundle_personal = new Bundle();
                    bundle_personal.putString("username", username);
                    Intent intent_personal = new Intent(PublishMain.this, PersonalMain.class);
                    intent_personal.putExtras(bundle_personal);
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    startActivity(intent_personal);

                    break;

                default:
                    break;
            }
        }
    };
}
