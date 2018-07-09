package com.example.lenovo.musicplayer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;

public class MusicService extends Service {
    private MyBinder iBinder;
    private MediaPlayer player;
    int[] music = new int[10];
    int index = -1;

    public MusicService() {
    }

    public class MyBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        iBinder = new MyBinder();
        //Toast.makeText(this,"MusicService onBind",Toast.LENGTH_SHORT).show();
        return iBinder;
    }
    @Override
    public void onCreate() {
        super.onCreate();
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
    }

    public void playMusic(int position) throws IOException {
        //Toast.makeText(this,"MusicService listChoose",Toast.LENGTH_SHORT).show();
        // player.setDataSource(this, Uri.parse("android.resource://" + getPackageName() + "/" + music[position]));

        player = MediaPlayer.create(getApplicationContext(),music[position]);
        index = position;
        player.setLooping(true);
        player.start();
    }

    public void pauseMusic() {
        if(player==null)
            return;
        if(player.isPlaying()) {
            //Toast.makeText(this,"MusicService pauseMusic",Toast.LENGTH_SHORT).show();
            player.pause();
        }
    }

    public void restartMusic() {
        if(player==null)
            return;
        if(!player.isPlaying()) {
            //Toast.makeText(this,"MusicService restartMusic",Toast.LENGTH_SHORT).show();
            player.start();
        }
    }

    public void nextMusic() throws IOException {
        if(player==null || index==-1)
            return;
        if(index < 9) {
            playMusic(++index);
        }
        else if(index == 9) {
            index = 0;
            playMusic(index);
        }
    }

    public void preMusic() throws IOException {
        if(player==null || index==-1)
            return;
        if(index > 0) {
            playMusic(--index);
        }
        else if(index == 0) {
            index = 9;
            playMusic(index);
        }
    }

    public void stopMusic() {
        if(player==null)
            return;
        player.seekTo(0);
        player.pause();
    }

    public boolean isPlaying() {
        if(player==null)
            return false;
        if(player.isPlaying()) {
            return true;
        } else {
            return false;
        }
    }

    public void resetPlayer() {
        player.reset();

    }

    public void updateSeekBar() {
        Thread mThread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int duration = player.getDuration();
                    int currentPosition = player.getCurrentPosition();

                    Message message = Message.obtain();
                    Bundle bundle = new Bundle();
                    bundle.putInt("duration",duration);
                    bundle.putInt("currentPosition",currentPosition);
                    message.setData(bundle);

                    MainActivity.handler.sendMessage(message);
                }
            }
        };
        mThread.start();
    }

    public void seekToPos(int pos) {
        player.seekTo(pos);
    }
}
