package com.example.lenovo.goods;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class myChangeCode extends AppCompatActivity {
    private TextView text_username;
    private ImageButton btn_back;
    private ImageButton btn_save;
    private String username;
    private boolean flagCode = true;
    private boolean flagUpdate = true;
    private String oldpassword;  //旧密码
    private String newpassword;   //新密码
    private EditText edit_oldCode,edit_newCode;
    private MysqlOperator sqlSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_change_code);



        init();
        receiveMsg();

        System.out.println("获取到的用户名"+username);

        //打开读取原密码的线程
        new Thread(runnableCode).start();
    }

    public void init()
    {
        username = "";
        oldpassword="";
        newpassword="";
        sqlSet = new MysqlOperator();
        text_username = findViewById(R.id.text_username);
        btn_back = findViewById(R.id.btn_back);
        btn_save = findViewById(R.id.btn_save);
        edit_oldCode=findViewById(R.id.edit_oldCode);
        edit_newCode=findViewById(R.id.edit_newCode);

        btn_back.setOnClickListener(listener);
        btn_save.setOnClickListener(listener);


    }

    private final View.OnClickListener listener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.btn_back:
                    //跳转到个人中心主页面
                    Bundle bundle = new Bundle();
                    bundle.putString("username",username);
                    Intent intent_back = new Intent(myChangeCode.this,PersonalMain.class);
                    intent_back.putExtras(bundle);
                    startActivity(intent_back);
                    break;
                case R.id.btn_save:
                    //保存修改
                    flagUpdate = true;
                    String edit_oldpassword = edit_oldCode.getText().toString();   //旧密码
                    newpassword = edit_newCode.getText().toString();  //新密码

                    System.out.println("获取到的原密码"+oldpassword);
                    System.out.println("输入的旧密码"+edit_oldpassword);
                    System.out.println("输入的新密码"+newpassword);

                    if("".equals(edit_oldpassword)||"".equals(newpassword))
                    {
                        String msg = "存在信息未填写，保存失败";
                        AlertDialog.Builder showMsg = new AlertDialog.Builder(myChangeCode.this);
                        showMsg.setMessage(msg);
                        AlertDialog showMsgMore=showMsg.create();
                        showMsgMore.show();
                        break;
                    }

                    if(!edit_oldpassword.equals(oldpassword))
                    {
                        String msg = "修改密码失败,未能正确输入原密码";
                        AlertDialog.Builder showMsg = new AlertDialog.Builder(myChangeCode.this);
                        showMsg.setMessage(msg);
                        AlertDialog showMsgMore=showMsg.create();
                        showMsgMore.show();
                        break;
                    }
                    else if(newpassword.equals(edit_oldpassword))
                    {
                        String msg = "新密码与原密码相同，未做修改";
                        AlertDialog.Builder showMsg = new AlertDialog.Builder(myChangeCode.this);
                        showMsg.setMessage(msg);
                        AlertDialog showMsgMore=showMsg.create();
                        showMsgMore.show();
                        break;
                    }
                    else
                    {
                        String msg = "密码修改成功";
                        oldpassword = newpassword;
                        AlertDialog.Builder showMsg = new AlertDialog.Builder(myChangeCode.this);
                        showMsg.setMessage(msg);
                        AlertDialog showMsgMore=showMsg.create();
                        showMsgMore.show();
                        //开启更新密码操作
                        new Thread(runnableUpdate).start();
                    }

                    break;

                default:
                    break;
            }
        }
    };

    //处理接收数据
    Handler myHandler=new Handler()
    {

        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            Bundle data =msg.getData();
            // 从bundle中获取对应key的值 获取原密码
            oldpassword = data.getString("passwordCode");

        }
    };

    //开辟一个线程 用来读取数据库中该用户的旧密码
    Runnable runnableCode =new Runnable() {
        @Override
        public void run() {

            while (flagCode)
            {
                String sql_select = "select password from users where username = '" + username + "'";
                try {
                    SelectSql(sql_select);
                    flagCode=false;
                } catch (java.sql.SQLException e) {
                    e.printStackTrace();
                }

            }
        }
    };

    //开辟一个线程 用来修改用户的密码
    Runnable runnableUpdate =new Runnable() {
        @Override
        public void run() {

            while (flagUpdate)
            {
                String sql_update = "update users set password = '"+newpassword+"'  where username = '" + username + "'";
                try {
                    sqlSet.UpdateSql(sql_update);
                    flagUpdate=false;
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
                Bundle bundle_code = new Bundle();
                bundle_code.putString("passwordCode",rs.getString(1));

                msg.setData(bundle_code);
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

    public void receiveMsg()
    {
        Bundle bundle_name = this.getIntent().getExtras();
        //text_username.setText(String.format("用户名：%s ",bundle_name.getString("username_code")));
        username=bundle_name.getString("username_code");
    }
}
