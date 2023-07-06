package com.example.videoplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VideoRVAdapter extends RecyclerView.Adapter<VideoRVAdapter.ViewHolder>{

    private ArrayList<videoRVmodel>videoRVmodelArrayList;
    private Context context;
    private VideoClickInterface  VideoClickInterface;

    public VideoRVAdapter(ArrayList<videoRVmodel> videoRVmodelArrayList, Context context, VideoRVAdapter.VideoClickInterface videoClickInterface) {
        this.videoRVmodelArrayList = videoRVmodelArrayList;
        this.context = context;
        VideoClickInterface = videoClickInterface;
    }

//    public VideoRVAdapter() {
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.video_rv_item,parent,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoRVAdapter.ViewHolder holder, int position) {
videoRVmodel  videoRVmodel=videoRVmodelArrayList.get(position);
holder.thumbnailIV.setImageBitmap(videoRVmodel.getTHumNail());
holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
//        videoClickInterface.onVideoCLick(position);
        VideoClickInterface.onVideoClick(position);
    }
});
    }

    @Override
    public int getItemCount() {
        return videoRVmodelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView thumbnailIV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnailIV=itemView.findViewById(R.id.idIVThumbNail);
        }
    }
    public interface VideoClickInterface{
        void onVideoClick(int position);
    }
}
