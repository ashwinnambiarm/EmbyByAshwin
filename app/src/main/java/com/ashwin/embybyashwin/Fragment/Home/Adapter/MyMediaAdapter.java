package com.ashwin.embybyashwin.Fragment.Home.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.ashwin.embybyashwin.Fragment.Home.Model.MyMedia;
import com.ashwin.embybyashwin.Fragment.Login.Adapter.ServerAdapter;
import com.ashwin.embybyashwin.R;

import java.util.ArrayList;

public class MyMediaAdapter extends RecyclerView.Adapter<MyMediaAdapter.ViewHolder> {

    ArrayList<MyMedia> mediaList;

    public MyMediaAdapter(ArrayList<MyMedia> medialist){
        mediaList = medialist;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.list_media_item, parent, false);

        MyMediaAdapter.ViewHolder viewHolder = new MyMediaAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        MyMedia myMedia = mediaList.get(position);
    }

    @Override
    public int getItemCount() {
        return mediaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder( View itemView) {
            super(itemView);
        }
    }
}
