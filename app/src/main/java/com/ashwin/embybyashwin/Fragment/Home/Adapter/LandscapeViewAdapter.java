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

import mediabrowser.apiinteraction.android.AndroidApiClient;
import mediabrowser.model.drawing.ImageFormat;
import mediabrowser.model.dto.BaseItemDto;
import mediabrowser.model.dto.ImageOptions;
import mediabrowser.model.entities.ImageType;
import mediabrowser.model.entities.MediaType;
import mediabrowser.model.querying.ItemFilter;

public class LandscapeViewAdapter extends RecyclerView.Adapter<LandscapeViewAdapter.ViewHolder>{
    private final AndroidApiClient apiClient;
    private String TAG = LandscapeViewAdapter.class.getSimpleName();
    ArrayList<BaseItemDto> mediaList;
    ViewOptions viewOptions;
    ImageOptions options;

    public LandscapeViewAdapter(ArrayList<BaseItemDto> medialist, ViewOptions viewoptions){
        mediaList = medialist;
        viewOptions = viewoptions;
        apiClient = GlobalClass.getInstance().getApiClient();

        options = new ImageOptions();
        options.setImageType(ImageType.Backdrop);
        options.setFormat(ImageFormat.Png);
        options.setMaxWidth(240);
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

        BaseItemDto item = mediaList.get(position);

        ImageView imgPrimary = holder.imgPrimary;
        ProgressBar progressBar = holder.progressBar;

        Log.e(TAG, item.getName());

        try {

            String itemId = item.getId();

            if (item.getType().equals("Episode")){
                itemId = item.getSeriesId();
            }

            String url_img = apiClient.GetImageUrl(itemId, options);
            GlobalClass.getInstance().LoadImagetoView(url_img, imgPrimary);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.getLocalizedMessage());
        }

        if (viewOptions.isShowProgressBar() && item.getCanResume()){
            Integer i = (int) (item.getResumePositionTicks() * 100 /  item.getRunTimeTicks());
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
