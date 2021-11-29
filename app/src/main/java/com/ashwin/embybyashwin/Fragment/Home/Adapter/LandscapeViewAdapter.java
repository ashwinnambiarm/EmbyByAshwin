package com.ashwin.embybyashwin.Fragment.Home.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.RecyclerView;

import com.ashwin.embybyashwin.Fragment.Home.Model.MyMedia;
import com.ashwin.embybyashwin.R;
import com.ashwin.embybyashwin.emby.GlobalClass;

import java.util.ArrayList;

public class LandscapeViewAdapter extends RecyclerView.Adapter<LandscapeViewAdapter.ViewHolder>{
    private String TAG = LandscapeViewAdapter.class.getSimpleName();
    ArrayList<MyMedia> mediaList;
    ViewOptions viewOptions;

    public LandscapeViewAdapter(ArrayList<MyMedia> medialist, ViewOptions viewoptions){
        mediaList = medialist;
        viewOptions = viewoptions;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.list_item_landscape_view, parent, false);

        LandscapeViewAdapter.ViewHolder viewHolder = new LandscapeViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LandscapeViewAdapter.ViewHolder holder, int position) {

        MyMedia myMedia = mediaList.get(position);

        ImageView imgPrimary = holder.imgPrimary;
        ProgressBar progressBar = holder.progressBar;

        GlobalClass.getInstance().LoadImagetoView(myMedia.getThumbanilUrl(), imgPrimary);

        if (viewOptions.isShowProgressBar() && myMedia.getItemDetials().getCanResume()){
            Integer i = (int) (myMedia.getItemDetials().getResumePositionTicks() * 100 /  myMedia.getItemDetials().getRunTimeTicks());
            progressBar.setProgress(i);
        }
    }

    @Override
    public int getItemCount() {
        return mediaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final ImageView imgPrimary;
        private final ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);

            imgPrimary = (ImageView) itemView.findViewById(R.id.img_list_item_landscape_view);
            progressBar = (ProgressBar) itemView.findViewById(R.id.pb_list_item_landscape_view);
        }
    }
}
