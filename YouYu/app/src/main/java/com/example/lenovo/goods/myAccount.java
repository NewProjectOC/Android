package com.example.lenovo.goods;


import android.content.Intent;
import android.database.SQLException;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class myAccount extends AppCompatActivity {

    private TextView text_username;
    private ImageButton btn_back;
    private ImageButton btn_save;
    private RadioGroup gdp_sex;
    private RadioButton rb_man,rb_woman;
    private EditText edit_changeName,edit_changePhone,edit_share;
    private String username = "cjh";
    private int schoolID;
    private MysqlOperator sqlSet;
    private Spinner schoolChoose;
    private String nameAccount_put,phoneAccount_put,sexAccount_put,shareinfoAccount_put;
    private int index;  //记录Spinner 选择位置
    private boolean flagAccount = true;
    private boolean flagUpdate = true;
    private boolean flag=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        init();
        receiveMsg();

        //开启线程
        new Thread(runnableAccount).start();
    }
    public void init()
    {
        schoolID = 0;
        sexAccount_put="男";
        sqlSet = new MysqlOperatorSon();
        text_username = findViewById(R.id.text_username);
        btn_back= findViewById(R.id.btn_back);
        btn_save = findViewById(R.id.btn_save);
        edit_changeName= findViewById(R.id.edit_changeName);
        edit_changePhone= findViewById(R.id.edit_changePhone);
        gdp_sex = findViewById(R.id.gdp_sex);
        rb_man = findViewById(R.id.rb_man);
        rb_woman = findViewById(R.id.rb_woman);
        edit_share = findViewById(R.id.edit_share);
        schoolChoose = findViewById(R.id.schoolChoose);


        //设置监听事件
        btn_back.setOnClickListener(listener);
        btn_save.setOnClickListener(listener);
        gdp_sex.setOnCheckedChangeListener(rdgcc);

        schoolChoose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                index = position;
                switch (position)
                {
                    case 0:
                        schoolID=0;
                        break;
                    case 1:
                        schoolID=1;
                        break;
                    case 3:
                        schoolID=3;
                        break;
                    case 4:
                        schoolID=4;
                        break;
                    case 5:
                        schoolID=5;
                        break;
                    case 6:
                        schoolID=6;
                        break;
                    case 7:
                        schoolID=7;
                        break;
                    case 8:
                        schoolID=8;
                        break;
                    case 9:
                        schoolID=9;
                        break;
                    case 10:
                        schoolID=10;
                        break;
                    default:
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });


    }

    private final View.OnClickListener listener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // ImageViewPlus btn = (ImageViewPlus) v;
            switch (v.getId()) {
                case R.id.btn_back:
                    //跳转到个人中心主页面
                    Bundle bundle = new Bundle();
                    bundle.putString("username",username);
                    Intent intent_back = new Intent(myAccount.this,PersonalMain.class);
                    intent_back.putExtras(bundle);
                    startActivity(intent_back);
                    break;
                case R.id.btn_save:
                    //保存修改
                    //获取用户填写的内容
                    flagUpdate = true;
                    nameAccount_put=edit_changeName.getText().toString();
                    phoneAccount_put = edit_changePhone.getText().toString();
                    shareinfoAccount_put = edit_share.getText().toString();

                    if("".equals(nameAccount_put)||"".equals(phoneAccount_put))
                    {
                        String msg = "存在信息未填写，保存失败";
                        AlertDialog.Builder showMsg = new AlertDialog.Builder(myAccount.this);
                        showMsg.setMessage(msg);
                        AlertDialog showMsgMore=showMsg.create();
                        showMsgMore.show();
                    }
                    else
                    {
                        String msg = "保存成功";
                        AlertDialog.Builder showMsg = new AlertDialog.Builder(myAccount.this);
                        showMsg.setMessage(msg);
                        AlertDialog showMsgMore=showMsg.create();
                        showMsgMore.show();
                        new Thread(runnableUpdate).start();  //开始更新的线程
                        flagUpdate = true;
                    }

                    break;
                default:
                    break;
            }
        }
    };

    private RadioGroup.OnCheckedChangeListener rdgcc=new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            // TODO Auto-generated method stub
            if(checkedId==rb_man.getId())
            {
                sexAccount_put="男";
            }
            else if(checkedId==rb_woman.getId())
            {
                sexAccount_put="女";
            }
        }
    };

    //接收从主界面传来的用户名
    public void receiveMsg()
    {
        Bundle bundle_name = this.getIntent().getExtras();
        //text_username.setText(String.format("用户名：%s ",bundle_name.getString("username")));
        username = bundle_name.getString("username");
    }


    //处理接收数据
    Handler myHandler=new Handler()
    {

        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            Bundle data =msg.getData();
            // 从bundle中获取对应key的值
            String nameAccount = data.getString("nameAccount");
            String phoneAccount = data.getString("phoneAccount");
            String sexAccount = data.getString("sexAccount");
            String shareinfoAccount = data.getString("shareinfoAccount");
            schoolID = data.getInt("schoolID");

            // 显示到UI中
            edit_changeName.setText(nameAccount);
            edit_changePhone.setText(phoneAccount);
            if (sexAccount.equals("男"))
            {
                rb_man.setChecked(true);
                sexAccount_put = ("男");
            }
            else
            {
                rb_woman.setChecked(true);
                sexAccount_put = ("女");
            }
            //if(shareinfoAccount.length() == 0)
            if(shareinfoAccount==null)
            {
                System.out.println("yyyyyyyyyyyyyyyyyy");
                edit_share.setHint("和大家分享一点东西吧".toString());
                //edit_share.setText("说一说，介绍自己吧！");
            }
            else
            {
                edit_share.setText(shareinfoAccount);
            }

            schoolChoose.setSelection(schoolID);

        }
    };

    //开辟一个线程 用来读取数据库中的信息并设置hint信息
    Runnable runnableAccount =new Runnable() {
        @Override
        public void run() {

            while (flagAccount)
            {
                String sql_select = "select name,phone,sex,shareinfo,school from users where username = '" + username + "'";
                try {
                    SelectSql(sql_select);
                    flagAccount=false;
                } catch (java.sql.SQLException e) {
                    e.printStackTrace();
                }

            }
        }
    };

    //开辟一个线程 用来更新数据库中的用户表信息
    Runnable runnableUpdate =new Runnable() {
        @Override
        public void run() {

            while (flagUpdate)
            {
                System.out.println("11111111111111111111   nameAccount_put ="+nameAccount_put);
                System.out.println("11111111111111111111  sexAccount_put ="+sexAccount_put);
                System.out.println("11111111111111111111   phoneAccount_put ="+phoneAccount_put);
                System.out.println("11111111111111111111   shareinfoAccount_put "+shareinfoAccount_put);
                System.out.println("11111111111111111111  schoolID = "+schoolID);
                System.out.println("11111111111111111111  username="+username);
                String sql_update = "update users set name = '" +nameAccount_put+ "',sex = '" +sexAccount_put+ "',phone = '" +phoneAccount_put+ "',shareinfo = '" +shareinfoAccount_put+ "',school = '" +schoolID+ "' where username = '" +username+ "' ";
                // String sql_insert = "insert into TList(username,state) values('"+nameAccount_put+"','1')";
                // System.out.println(sql_insert);
                System.out.println(sql_update);
                try {
                    sqlSet.UpdateSql(sql_update);
                    // sqlSet.InsertSql(sql_insert);
                    flagUpdate = false;
                } catch (java.sql.SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    };


    public void SelectSql(String sql) throws java.sql.SQLException
    {
        Connection con = getConnection();
        System.out.println("查看数据库操作");
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            // 此处为你对该结果集的操作
            while(rs.next())
            {
                Message msg = new Message();
                Bundle bundle_select = new Bundle();
                bundle_select.putString("nameAccount",rs.getString(1));
                bundle_select.putString("phoneAccount",rs.getString(2));
                bundle_select.putString("sexAccount",rs.getString(3));
                bundle_select.putString("shareinfoAccount",rs.getString(4));
                bundle_select.putInt("schoolID",rs.getInt(5));
                msg.setData(bundle_select);
                myHandler.sendMessage(msg);
            }
            stmt.close();
        }
        catch (java.sql.SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (con != null)
            {
                try {
                    con.close();
                } catch (java.sql.SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Connection getConnection() {
        Connection con = null;
        try {
            System.out.println("已经进入连接数据库操作");
            String UserName = "root"; // 连接数据库的用户名
            String Password = "Haohao@970"; // 连接数据库的密码
            Class.forName("com.mysql.jdbc.Driver"); // 装载jdbc驱动
            //con = (Connection) DriverManager.getConnection("jdbc:mysql://47.106.34.165/test?useUnicode=true&characterEncoding=utf-8 ", UserName, Password);
            con = (Connection) DriverManager.getConnection("jdbc:mysql://47.106.34.165/test?useUnicode=true&characterEncoding=utf-8 ", UserName, Password);
        } catch (java.sql.SQLException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e1)
        {
            e1.printStackTrace();
        }
        return con;
    }





}
