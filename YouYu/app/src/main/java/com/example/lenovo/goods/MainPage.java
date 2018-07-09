package com.example.lenovo.goods;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainPage extends MysqlOperator {

    private Spinner missionType_sp;
    private Spinner schoolChoose_sp;
    private ListView missionView;
    private ImageButton home_btn;
    private ImageButton post_btn;
    private ImageButton person_btn;
    private String username;
    private Context context = MainPage.this;
    private List<Map<String, Object>> list;
    private int flag = 0;
    int localTID;
    int localState;


    public class MyAsyncTask extends AsyncTask<Void, Void, List<Map<String, Object>>> {

        @Override
        protected List<Map<String, Object>> doInBackground(Void... voids) {
                if (flag == 1) {
                    list = new ArrayList<>();
                    String sql = "select Tid,name,title,content,state,date from WList order by Tid desc;";
                    Connection con = getConnection();
                    try {
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(sql);
                        while (rs.next()) {
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
                } else if (flag == 2) {
                    list = new ArrayList<>();
                    String sql = "select Tid,name,title,content,state,date from AList order by Tid desc;";
                    Connection con = getConnection();
                    try {
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(sql);
                        while (rs.next()) {
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
                } else {
                    list = new ArrayList<>();
                    String sql = "select Tid,name,title,content,state,date from WList union select Tid,name,title,content,state,date from AList order by Tid desc;";
                    System.out.println("0");
                    Connection con = getConnection();
                    try {
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(sql);
                        while (rs.next()) {
                            int Tid = rs.getInt(1);
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
                            map.put("Tid",Tid);
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
                }
            return list;
        }

        @Override
        protected void onPostExecute(List<Map<String, Object>> list) {
            SimpleAdapter adapter = new SimpleAdapter(context, list, R.layout.item, new String[]{"date","name", "title", "content", "state"}, new int[]{R.id.time,R.id.author, R.id.title, R.id.summary, R.id.state});
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
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            return con;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        username = "";   //用户名初始化
        receiveMsg();   //获取从注册界面传来的用户名
        missionType_sp = findViewById(R.id.mission_sp);
        schoolChoose_sp = findViewById(R.id.school_sp);
        missionView = findViewById(R.id.missionview);
        home_btn = findViewById(R.id.home_btn);
        post_btn = findViewById(R.id.post_btn);
        person_btn = findViewById(R.id.person_btn);
        home_btn.setOnClickListener(listener);
        post_btn.setOnClickListener(listener);
        person_btn.setOnClickListener(listener);
        //new MyAsyncTask().execute();
        missionType_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        flag = 0;
                        new MyAsyncTask().execute();
                        break;
                    case 1:
                        flag = 1;
                        new MyAsyncTask().execute();
                        break;
                    case 2:
                        flag = 2;
                        new MyAsyncTask().execute();
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                flag = 0;
                new MyAsyncTask().execute();
            }
        });

        missionView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> map_item = list.get(position);
                localTID = Integer.parseInt(map_item.get("Tid").toString());
                if(map_item.get("state").toString().equals("(校园生活)"))
                {
                    Bundle bundle = new Bundle();
                    bundle.putString("username", username);
                    bundle.putInt("Tid",localTID);
                    Intent intent = new Intent(MainPage.this, ShowAListInfo.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else
                {
                    Bundle bundle = new Bundle();
                    bundle.putString("username", username);
                    bundle.putInt("Tid",localTID);
                    Intent intent = new Intent(MainPage.this, ShowWListInfo.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }

    private final View.OnClickListener listener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.home_btn:           //跳转到主界面
                    Bundle bundle = new Bundle();
                    bundle.putString("username", username);
                    Intent intent = new Intent(MainPage.this, MainPage.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                case R.id.post_btn:   //跳转到发布界面
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("username", username);
                    Intent intent2 = new Intent(MainPage.this, PublishMain.class);
                    intent2.putExtras(bundle1);
                    startActivity(intent2);
                    break;
                case R.id.person_btn:   //跳转到个人中心
                    Bundle bundle_personal = new Bundle();
                    bundle_personal.putString("username", username);
                    Intent intent_personal = new Intent(MainPage.this, PersonalMain.class);
                    intent_personal.putExtras(bundle_personal);
                    startActivity(intent_personal);
                    break;
                default:
                    break;
            }
        }
    };

    //接收从主界面传来的用户名
    public void receiveMsg()
    {
        Bundle bundle_name = this.getIntent().getExtras();
        username = bundle_name.getString("username");
    }

}


