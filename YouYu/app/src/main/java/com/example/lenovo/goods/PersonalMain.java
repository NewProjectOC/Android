package com.example.lenovo.goods;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.mysql.jdbc.Connection;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PersonalMain extends AppCompatActivity {
    private ImageViewPlus img_Photo;
    private ImageButton btn_account,btn_order,btn_collection,btn_address,btn_message,btn_code,home_btn,post_btn,person_btn;
    private TextView text_username;
    private String username;
    private EditText edit_put;
    private static final int PHOTO_REQUEST_CAREMA  = 1;
    public static final int CROP_PHOTO = 2;
    public static final int CROP_PHOTO_SEF =3;
    private Uri imageUri;
    public static File tempFile;
    public boolean flagUpdate = true;
    private MysqlOperator sqlSet;
    private  boolean flagSelect = true;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_main);



        receiveMsg();
        init();
        //开启读取用户头像线程
        new Thread(runnableHeader).start();

    }

    public void init(){

        sqlSet = new MysqlOperator();
        img_Photo=findViewById(R.id.img_Photo);
        text_username = findViewById(R.id.text_username);
        btn_account=findViewById(R.id.btn_account);
        btn_order = findViewById(R.id.btn_order);
        btn_collection=findViewById(R.id.btn_collection);
        btn_address = findViewById(R.id.btn_address);
        btn_message=findViewById(R.id.btn_message);
        btn_code = findViewById(R.id.btn_code);
        home_btn= findViewById(R.id.home_btn);
        post_btn= findViewById(R.id.post_btn);
        person_btn= findViewById(R.id.person_btn);
        text_username.setText("欢迎你："+username+"!");
        img_Photo.setOnClickListener(listener);
        btn_account.setOnClickListener(listener);
        btn_order.setOnClickListener(listener);
        btn_collection.setOnClickListener(listener);
        btn_address.setOnClickListener(listener);
        btn_message.setOnClickListener(listener);
        btn_code.setOnClickListener(listener);
        home_btn.setOnClickListener(listener);
        post_btn.setOnClickListener(listener);
    }

    private final View.OnClickListener listener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.img_Photo:           //点击进行头像的修改
                    String msg ="请选择头像上传方式";
                    AlertDialog.Builder showMsg = new AlertDialog.Builder(PersonalMain.this);
                    showMsg.setMessage(msg);
                    showMsg.setPositiveButton("相机拍摄", select_cam);
                    showMsg.setNegativeButton("图库上传", select_local);
                    AlertDialog showMsgMore=showMsg.create();
                    showMsgMore.show();
                    break;
                case R.id.btn_account:
                    //跳转到编辑资料页面  并且传入用户ID
                    Bundle bundle = new Bundle();
                    bundle.putString("username",username);
                    Intent intent_account = new Intent(PersonalMain.this,myAccount.class);
                    intent_account.putExtras(bundle);
                    startActivity(intent_account);
                    break;
                case R.id.btn_order:   //我的发布 增加删除订单功能
                    //跳转到编辑资料页面  并且传入用户ID
                    Bundle bundle_out = new Bundle();
                    bundle_out.putString("username",username);
                    Intent intent_out = new Intent(PersonalMain.this,myOutput.class);
                    intent_out.putExtras(bundle_out);
                    startActivity(intent_out);
                    break;
                case R.id.btn_collection:
                    break;
                case R.id.btn_address:
                    // btn_address.setImageURI(imageUri);

                    break;
                case R.id.btn_message:  //切换到登陆界面

                    new Thread(runnable1).start();
                    Intent intent_logout = new Intent(PersonalMain.this,LoginMain.class);
                    startActivity(intent_logout);

                    break;
                case R.id.btn_code:

                    //跳转到编辑资料页面  并且传入用户ID

                    Bundle bundle_code = new Bundle();
                    bundle_code.putString("username_code",username);
                    Intent intent_code = new Intent(PersonalMain.this,myChangeCode.class);
                    intent_code.putExtras(bundle_code);
                    startActivity(intent_code);
                    break;

                case R.id.home_btn:  //跳转到主页面

                    System.out.println("GOODS 777777777MainPage 发布 username  = "+username);

                    Bundle bundle_per = new Bundle();
                    bundle_per.putString("username", username);
                    Intent intent2 = new Intent(PersonalMain.this, MainPage.class);
                    intent2.putExtras(bundle_per);
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    startActivity(intent2);

                    break;

                case R.id.post_btn:  //跳转到发布页面
                    // btn_address.setImageURI(imageUri);


                    System.out.println("GOODS 777777777MainPage 发布 username  = "+username);
                    Bundle bundle_post = new Bundle();
                    bundle_post.putString("username", username);
                    Intent intent_post = new Intent(PersonalMain.this, PublishMain.class);
                    intent_post.putExtras(bundle_post);
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    startActivity(intent_post);

                    break;
                case R.id.person_btn:


                    break;

                default:
                    break;
            }
        }
    };

    private DialogInterface.OnClickListener select_cam=new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface arg0,int arg1)  //调用相机拍摄图片
        {

            flagUpdate =true;
            openCamera(PersonalMain.this);  //调用相机拍摄图片

        }
    };
    private DialogInterface.OnClickListener select_local=new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface arg0,int arg1)   //从图库上传图片
        {
            flagUpdate =true;
            openGallery();   //打开相册
        }
    };
    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO_REQUEST_CAREMA:
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, CROP_PHOTO); // 启动裁剪程序
                }
                break;
            case CROP_PHOTO:     //从相机拍摄图片
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        img_Photo.setImageBitmap(bitmap);
                        //开启用户头像更新的线程
                        new Thread(runnableUpdate).start();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CROP_PHOTO_SEF:     //从本地上传图片
                if (resultCode == RESULT_OK) {
                    try {
                        if(data != null) {
                            Uri uri = data.getData();
                            imageUri = uri;
                        }
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));

                        img_Photo.setImageBitmap(bitmap);

                        //开启用户头像更新的线程
                        new Thread(runnableUpdate).start();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

    public void openCamera(Activity activity) {
        //捕获系统版本
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        // 激活相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard())
        {
            SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                    "yyyy_MM_dd_HH_mm_ss");
            String filename = timeStampFormat.format(new Date());
            tempFile = new File(Environment.getExternalStorageDirectory(),
                    filename + ".jpg");
            if (currentapiVersion < 24)
            {
                // 从文件中创建uri
                imageUri = Uri.fromFile(tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            } else
            {
                //兼容android7.0 使用共享文件的形式
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
                //检查是否有存储权限，以免崩溃
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    Toast.makeText(this,"请开启存储权限",Toast.LENGTH_SHORT).show();
                    return;
                }
                imageUri = activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            }
        }
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
        activity.startActivityForResult(intent,PHOTO_REQUEST_CAREMA);
    }

    /*
     * 判断sdcard是否被挂载
     */
    public static boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    //从图库上传相册
    public void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, CROP_PHOTO_SEF);
    }

    Runnable runnable1 = new Runnable() {
        private Connection con = null;

        @Override
        public void run() {
            // TODO Auto-generated method stub
            String sql6 = "delete from used where username='" + username + "'";       //查询表名为“users”的所有内容
            try {
                sqlSet.DeleteSql(sql6);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };

    //开辟一个线程 用来更新用户的头像
    Runnable runnableUpdate =new Runnable() {
        @Override
        public void run() {

            while (flagUpdate)
            {
                String sql_update = "update users set imageUri = '"+imageUri+"' where username = '" +username+ "' ";
                System.out.println(sql_update);
                try {
                    sqlSet.UpdateSql(sql_update);
                    flagUpdate = false;
                } catch (java.sql.SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    //开辟一个线程 用来读取数据库中的头像信息
    Runnable runnableHeader =new Runnable() {
        @Override
        public void run() {

            while (flagSelect)
            {
                String sql_select = "select imageUri from users where username = '" + username + "'";

                try {
                    SelectSql(sql_select);
                    flagSelect=false;
                } catch (java.sql.SQLException e) {
                    e.printStackTrace();
                }

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
            // 从bundle中获取对应key的值
            imageUri = Uri.parse(data.getString("imageUri"));

          //  imageUri = Uri.parse("android.resource://"+getPackageName()+"/"+R.drawable.defaultpic);
            //读取数据库头像资源
            System.out.println("默认 imageUri  = "+imageUri);

            img_Photo.setImageURI(imageUri);

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
                bundle_select.putString("imageUri",rs.getString(1));
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

    //接收从主界面传来的用户名
    public void receiveMsg()
    {
        Bundle bundle_name = this.getIntent().getExtras();
        username = bundle_name.getString("username");
    }
}
