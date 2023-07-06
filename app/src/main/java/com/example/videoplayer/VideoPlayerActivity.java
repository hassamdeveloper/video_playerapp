package com.example.videoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

public class VideoPlayerActivity extends AppCompatActivity {
private TextView videoNameTV,videoTimeTV;
private ImageButton backIB,forwardIB,playPauseIB;
private SeekBar videoSeekBar;
private VideoView videoView;
private RelativeLayout controlsRL,videoRl;
boolean isOpen=true;
private String videoName,videoPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        videoName=getIntent().getStringExtra("videoName");
        videoPath=getIntent().getStringExtra("videoPath");
        videoNameTV=findViewById(R.id.idTVVideoTitle);
        videoTimeTV=findViewById(R.id.idTVTime);
        backIB=findViewById(R.id.IdIBBack);
        playPauseIB=findViewById(R.id.IdIBPlay);
        forwardIB=findViewById(R.id.IdIBForward);
        videoSeekBar=findViewById(R.id.IdSeekBarProgrees);
        videoView=findViewById(R.id.idVideoView);
        controlsRL=findViewById(R.id.idRLControl);
        videoRl=findViewById(R.id.idRLVideo);

        videoView.setVideoURI(Uri.parse(videoPath));
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                videoSeekBar.setMax(videoView.getDuration());
                videoView.start();
            }
        });

        videoNameTV.setText(videoName);
        backIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.seekTo(videoView.getDuration()-10000);
            }
        });

        forwardIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
videoView.seekTo(videoView.getDuration()+10000);
            }
        });
        playPauseIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (videoView.isPlaying()) {
                    videoView.pause();
                    playPauseIB.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
                }
                else {
                    videoView.start();
                    playPauseIB.setImageDrawable(getResources().getDrawable(R.drawable.baseline_pause_24));
                }
            }
        });
        videoRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOpen){
                    hidecontrol();
                    isOpen=false;
                }
                else {
                    showcontrol();
                    isOpen=true;
                }
            }
        });
        sethandler();
        initilizationseekbar();
    }
    private void sethandler() {
        Handler handler = new Handler();
        Runnable runable = new Runnable() {
            @Override
            public void run() {
if(videoView.getDuration()>0){
    int curPos=videoView.getCurrentPosition();
    videoSeekBar.setProgress(curPos);
    videoTimeTV.setText(""+convertTime(videoView.getDuration()-curPos));
}
handler.postDelayed(this,0);
            }
        };
        handler.postDelayed(runable,500);

    }
    private String convertTime(int ms){
String time;
int x,seconds,minutes,hours;
x=ms/1000;
seconds=x%60;
x/=60;
        minutes=x%60;
        x/=60;
        hours=x%24;
        if(hours!=0){
            time=String.format("%02d",hours)+":"+String.format("%02d",minutes)+":"+String.format("%02D",minutes);

        }
        else {
            time=String.format("%02D",minutes)+":"+String.format("%02D",minutes);

        }
        return time;
    }
    private void initilizationseekbar(){
videoSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(videoSeekBar.getId()==R.id.IdSeekBarProgrees){
if(fromUser){
    videoView.seekTo(progress);
    videoView.start();
    int curPos=videoView.getCurrentPosition();
    videoTimeTV.setText(""+convertTime(videoView.getDuration()-curPos));
}

        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
});
    }

    private void showcontrol() {
        controlsRL.setVisibility(View.VISIBLE);
        final Window window=this.getWindow();
        if(window==null)
        {
            return;
        }
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        View decorview=window.getDecorView();
        if(decorview!=null){
            int uiOption=decorview.getSystemUiVisibility();
            if(Build.VERSION.SDK_INT>=14){
                uiOption&=~View.SYSTEM_UI_FLAG_LOW_PROFILE;
            }
            if(Build.VERSION.SDK_INT>=16){
                uiOption&=~View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            }
            if(Build.VERSION.SDK_INT>=19){
                uiOption&=~View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            }
            decorview.setSystemUiVisibility(uiOption);
        }
    }

    private void hidecontrol() {
        controlsRL.setVisibility(View.GONE);
        final Window window=this.getWindow();
        if(window==null)
        {
            return;
        }
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        View decorview=window.getDecorView();
        if(decorview!=null){
            int uiOption=decorview.getSystemUiVisibility();
            if(Build.VERSION.SDK_INT>=14){
                uiOption|=View.SYSTEM_UI_FLAG_LOW_PROFILE;
            }
            if(Build.VERSION.SDK_INT>=16){
                uiOption|=View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            }
            if(Build.VERSION.SDK_INT>=19){
                uiOption|=View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            }
            decorview.setSystemUiVisibility(uiOption);
        }
    }
}