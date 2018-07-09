package com.example.lenovo.musicplayer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {
    private MusicService musicService;
    private ListView musiclist;
    private ImageButton startorpause_btn;
    private ImageButton stop_btn;
    private ImageButton pre_btn;
    private ImageButton next_btn;
    private static SeekBar seekbar;
    private Timer timer;
    private static TextView currentTime;
    private static TextView fullTime;
    int flag = 0;
    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle data = msg.getData();
            int duration = data.getInt("duration");
            int currentPosition = data.getInt("currentPosition");

            seekbar.setMax(duration);
            seekbar.setProgress(currentPosition);
            currentTime.setText(timeDate(currentPosition));
            fullTime.setText(timeDate(duration));
        }
    };

    int ischoose = 0;
    int index;
    int[] music = new int[10];

    private List<Map<String,Object>> getData() {
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map<String,Object> map = new HashMap<String, Object>();
        map = new HashMap<String, Object>();
        map.put("musicName",this.getResources().getStringArray(R.array.musicName)[0]);
        map.put("autherName",this.getResources().getStringArray(R.array.autherName)[0]);
        map.put("pic",R.drawable.p0);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("musicName",this.getResources().getStringArray(R.array.musicName)[1]);
        map.put("autherName",this.getResources().getStringArray(R.array.autherName)[1]);
        map.put("pic",R.drawable.p1);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("musicName",this.getResources().getStringArray(R.array.musicName)[2]);
        map.put("autherName",this.getResources().getStringArray(R.array.autherName)[2]);
        map.put("pic",R.drawable.p2);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("musicName",this.getResources().getStringArray(R.array.musicName)[3]);
        map.put("autherName",this.getResources().getStringArray(R.array.autherName)[3]);
        map.put("pic",R.drawable.p3);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("musicName",this.getResources().getStringArray(R.array.musicName)[4]);
        map.put("autherName",this.getResources().getStringArray(R.array.autherName)[4]);
        map.put("pic",R.drawable.p4);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("musicName",this.getResources().getStringArray(R.array.musicName)[5]);
        map.put("autherName",this.getResources().getStringArray(R.array.autherName)[5]);
        map.put("pic",R.drawable.p5);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("musicName",this.getResources().getStringArray(R.array.musicName)[6]);
        map.put("autherName",this.getResources().getStringArray(R.array.autherName)[6]);
        map.put("pic",R.drawable.p6);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("musicName",this.getResources().getStringArray(R.array.musicName)[7]);
        map.put("autherName",this.getResources().getStringArray(R.array.autherName)[7]);
        map.put("pic",R.drawable.p7);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("musicName",this.getResources().getStringArray(R.array.musicName)[8]);
        map.put("autherName",this.getResources().getStringArray(R.array.autherName)[8]);
        map.put("pic",R.drawable.p8);
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("musicName",this.getResources().getStringArray(R.array.musicName)[9]);
        map.put("autherName",this.getResources().getStringArray(R.array.autherName)[9]);
        map.put("pic",R.drawable.p9);
        list.add(map);
        return list;
    }

    // 在bindService时会启动
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicService = ((MusicService.MyBinder)service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        musiclist = (ListView)findViewById(R.id.musicList);
        startorpause_btn = (ImageButton)findViewById(R.id.startorpause_btn);
        stop_btn = (ImageButton)findViewById(R.id.stop_btn);
        pre_btn = (ImageButton)findViewById(R.id.pre_btn);
        next_btn = (ImageButton)findViewById(R.id.next_btn);
        currentTime = (TextView)findViewById(R.id.currentTime);
        fullTime = (TextView)findViewById(R.id.fullTime);

        seekbar = (SeekBar)findViewById(R.id.seekbar);

        Intent intent = new Intent(this,MusicService.class);
        bindService(intent,mServiceConnection, Context.BIND_AUTO_CREATE);

        SimpleAdapter adapter = new SimpleAdapter(this,getData(),R.layout.listview,new String[]{"musicName","autherName","pic"},new int[]{R.id.musicName,R.id.autherName,R.id.pic});
        musiclist.setAdapter(adapter);

        music[0] = R.raw.animals;
        music[1] = R.raw.dominion;
        music[2] = R.raw.dyinginthesun;
        music[3] = R.raw.eifersucht;
        music[4] = R.raw.forever;
        music[5] = R.raw.heartbeats;
        music[6] = R.raw.spirit;
        music[7] = R.raw.walkaway;
        music[8] = R.raw.whistle;
        music[9] = R.raw.hzj;

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                musicService.seekToPos(seekBar.getProgress());
            }
        });

        musiclist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ischoose = 1;
                // Prepare(position);
                try {
                    if(musicService.isPlaying()) {
                        musicService.resetPlayer();
                    }
                    musicService.playMusic(position);
                    if(flag == 0) {
                        musicService.updateSeekBar();
                        flag = 1;
                    }
                    if(musicService.isPlaying()) {
                        startorpause_btn.setImageResource(R.drawable.mu_pause);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }



    public void StartOrPause(View view) {
        if(ischoose != 0) {
            if (musicService.isPlaying()) {
                musicService.pauseMusic();
                startorpause_btn.setImageResource(R.drawable.mu_play);
            } else {
                startorpause_btn.setImageResource(R.drawable.mu_pause);
                musicService.restartMusic();
            }
        }
    }

    public void NextMusic(View view) {
        try {
            if(musicService.isPlaying()) {
                musicService.resetPlayer();
            }
            musicService.nextMusic();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void PreMusic(View view) {
        try {
            if(musicService.isPlaying()) {
                musicService.resetPlayer();
            }
            musicService.preMusic();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void StopMusic(View view) {
        musicService.stopMusic();
        startorpause_btn.setImageResource(R.drawable.mu_play);
    }

    public static String timeDate(int a) {
        Date date = new Date(a);
        SimpleDateFormat st = new SimpleDateFormat("mm:ss");
        String time = st.format(date);
        return time;
    }

}
