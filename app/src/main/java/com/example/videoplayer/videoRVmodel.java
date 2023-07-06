package com.example.videoplayer;

import android.graphics.Bitmap;

public class videoRVmodel {
    private String videoName;
    private String videoPath;
    private Bitmap THumNail;

    public videoRVmodel(String videoName, String videoPath, Bitmap THumNail) {
        this.videoName = videoName;
        this.videoPath = videoPath;
        this.THumNail = THumNail;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public Bitmap getTHumNail() {
        return THumNail;
    }

    public void setTHumNail(Bitmap THumNail) {
        this.THumNail = THumNail;
    }
}
