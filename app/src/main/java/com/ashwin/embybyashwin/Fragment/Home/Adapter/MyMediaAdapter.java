package com.ashwin.embybyashwin.Fragment.Home.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ashwin.embybyashwin.Fragment.Home.Model.MyMedia;
import com.ashwin.embybyashwin.R;
import com.ashwin.embybyashwin.emby.GlobalClass;

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

        View view = inflater.inflate(R.layout.list_library_item, parent, false);

        MyMediaAdapter.ViewHolder viewHolder = new MyMediaAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        MyMedia myMedia = mediaList.get(position);

        TextView txtMediaName = holder.txtMediaName;
        ImageView imgPrimary = holder.imgPrimary;

        txtMediaName.setText(myMedia.getItemDetials().getName());

        GlobalClass.getInstance().LoadImagetoView(myMedia.getThumbanilUrl(), imgPrimary);

    }

    @Override
    public int getItemCount() {
        return mediaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView txtMediaName;
        private final ImageView imgPrimary;

        public ViewHolder(View itemView) {
            super(itemView);
            txtMediaName = (TextView) itemView.findViewById(R.id.lbl_my_media_name);
            imgPrimary = (ImageView) itemView.findViewById(R.id.img_my_media_primary);
        }
    }
}
