package com.example.lenovo.goods;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class myOutput extends AppCompatActivity {

    private ListView missionView;
    private Context context = myOutput.this;
    private String username;    //传入的用户名
    private ImageButton btn_back;
    private Button btn_delete;
    private Boolean flagDelete = true;
    private Boolean flagUpdate = true;
    private MysqlOperator setSql;
    private int localTID,localState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_output);

        init();  //控件初始化
        receiveMsg();  //接受主界面传来的信息
        new MyAsyncTask().execute();  //开启线程,读取数据库中的信息



    }


    //绑定对话框的确定以及取消按钮事件
    private DialogInterface.OnClickListener select_ture=new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface arg0,int arg1)  //确定删除
        {
            //开启删除线程
            flagDelete = true;

            new MyAsyncTask_delete().execute();
            String msg = "删除成功";
            AlertDialog.Builder showMsg = new AlertDialog.Builder(myOutput.this);
            showMsg.setMessage(msg);
            AlertDialog showMsgMore=showMsg.create();
            showMsgMore.show();

        }
    };

    private DialogInterface.OnClickListener select_false=new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface arg0,int arg1)   //取消删除
        {
            arg0.cancel();
        }
    };


    public void init()  //控件的初始化
    {
        setSql = new MysqlOperator();
        missionView = findViewById(R.id.list_out);
        btn_back = findViewById(R.id.btn_back);
        username = "";
        localTID = 0;
        localState= -1;
        btn_back.setOnClickListener(listener);
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
                    Intent intent_back = new Intent(myOutput.this,PersonalMain.class);
                    intent_back.putExtras(bundle);
                    startActivity(intent_back);

                    break;
                default:
                    break;
            }
        }
    };

    public class MyAsyncTask extends AsyncTask<Void, Void, List<Map<String, Object>>> {   //查看用户发布的订单

        List<Map<String, Object>> list = new ArrayList<>();
        @Override
        protected List<Map<String, Object>> doInBackground(Void... voids) {
            String sql = "select W.Tid,W.name,W.title,W.content,W.state,W.date from WList W,TList T where W.Tid = T.Tid and T.username = '"+username+"'union select A.Tid,A.name,A.title,A.content,A.state,A.date from AList A , TList T where  A.Tid = T.Tid  and T.username = '"+username+"' order by Tid";
            Connection con = getConnection();
            try {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next())
                {
                    String Tid = rs.getString(1);
                    String name = rs.getString(2);
                    String title = rs.getString(3);
                    String content = rs.getString(4);
                    int stateFlag = rs.getInt(5);
                    String date = rs.getString(6);
                    String state;
                    if (stateFlag == 0)
                        state = "(闲置交易)";
                    else
                        state = "(校园生活)";
                    if (content.length()>10) {
                        content = content.substring(0,10)+"...";
                    }
                    Map<String, Object> map = new HashMap<>();
                    map.put("Tid", Tid);
                    map.put("name", name);
                    map.put("title", title);
                    map.put("content", content);
                    map.put("state", state);
                    map.put("date",date);
                    list.add(map);
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

            //listView子菜单选择事件
            missionView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Map<String, Object> map_item = list.get(position);
                    localTID = Integer.parseInt(map_item.get("Tid").toString());
                    if(map_item.get("state").toString().equals("(校园生活)"))
                    {
                        localState = 1;
                    }
                    else
                    {
                        localState = 0;
                    }
                    String msg = "是否确定删除";
                    AlertDialog.Builder showMsg = new AlertDialog.Builder(myOutput.this);
                    showMsg.setMessage(msg);
                    showMsg.setPositiveButton("确定", select_ture);
                    showMsg.setNegativeButton("取消", select_false);
                    AlertDialog showMsgMore=showMsg.create();
                    showMsgMore.show();
                }
            });
            return list;
        }

        @Override
        protected void onPostExecute(List<Map<String, Object>> list)
        {
            SimpleAdapter adapter = new SimpleAdapter(context, list, R.layout.item_out, new String[]{"date","name", "title", "content", "state"}, new int[]{R.id.time,R.id.author, R.id.title, R.id.summary, R.id.state});
            missionView.setAdapter(adapter);
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
            } catch (ClassNotFoundException e1)
            {
                e1.printStackTrace();
            }
            return con;
        }
    }

    //接收从主界面传来的用户名
    public void receiveMsg()
    {
        Bundle bundle_name = this.getIntent().getExtras();
        //text_username.setText(String.format("用户名：%s ",bundle_name.getString("username")));
        username = bundle_name.getString("username");
    }

    public class MyAsyncTask_delete extends AsyncTask<Void, Void, List<Map<String, Object>>> {   //查看用户发布的订单

        List<Map<String, Object>> list = new ArrayList<>();

        @Override
        protected List<Map<String, Object>> doInBackground(Void... voids) {

            if(localState == 1)  // AList
            {
                String sql_delete = "delete from AList where Tid = '" + localTID + "'";
                String sql_delete_main = "delete from TList where Tid = '" + localTID + "'";
                try {
                    setSql.DeleteSql(sql_delete);
                    setSql.DeleteSql(sql_delete_main);
                } catch (java.sql.SQLException e) {
                    e.printStackTrace();
                }
            }
            if(localState == 0)  // WList
            {
                String sql_delete = "delete from WList where Tid = '" + localTID + "'";
                String sql_delete_main = "delete from TList where Tid = '" + localTID + "'";
                try {
                    setSql.DeleteSql(sql_delete);
                    setSql.DeleteSql(sql_delete_main);
                }
                catch (java.sql.SQLException e)
                {
                    e.printStackTrace();
                }
            }

            return list;
        }

        @Override
        protected void onPostExecute(List<Map<String, Object>> list)
        {
            new MyAsyncTask().execute();
        }

    }

}
