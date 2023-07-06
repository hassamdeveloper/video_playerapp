package com.example.videoplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements VideoRVAdapter.VideoClickInterface {

    private RecyclerView videoRV;
    private ArrayList<videoRVmodel> videoRVmodelArrayList;
    private VideoRVAdapter videoRVAdapter;
    private static final int STORAGE_PERMISSION = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoRV = findViewById(R.id.id_RVVideos);
        videoRVmodelArrayList = new ArrayList<>();
        videoRVAdapter = new VideoRVAdapter(videoRVmodelArrayList, this, this::onVideoClick);
        videoRV.setLayoutManager(new GridLayoutManager(this, 2));
        videoRV.setAdapter(videoRVAdapter);
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION);
        } else {
            getvideos();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION) {
if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
    Toast.makeText(this, "Permissions Granted", Toast.LENGTH_SHORT).show();
    getvideos();
   
}
else {
    Toast.makeText(this, "The App Will Not Work Without Permission..", Toast.LENGTH_SHORT).show();
    finish();
}
        }
    }

        private void getvideos () {
            ContentResolver ContentResolver=getContentResolver();
            Uri uri= MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            Cursor cursor= ContentResolver.query(uri,null,null,null,null);
            if(cursor!=null && cursor.moveToFirst()){

                do {
               String videoTitle=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
                    String videoPath=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                    Bitmap videoThumbnail= ThumbnailUtils.createVideoThumbnail(videoPath,MediaStore.Images.Thumbnails.MINI_KIND);
                    videoRVmodelArrayList.add(new videoRVmodel(videoTitle,videoPath,videoThumbnail));
                }
                while (cursor.moveToNext());
            }
videoRVAdapter.notifyDataSetChanged();

        }

        @Override
        public void onVideoClick ( int position){
//Start krna ha
            Intent i=new Intent(MainActivity.this,VideoPlayerActivity.class);
            i.putExtra("videoName",videoRVmodelArrayList.get(position).getVideoName());
            i.putExtra("videoPath",videoRVmodelArrayList.get(position).getVideoPath());
            startActivity(i);
        }
    }
