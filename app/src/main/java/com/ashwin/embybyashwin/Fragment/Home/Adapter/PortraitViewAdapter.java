package com.ashwin.embybyashwin.Fragment.Home.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ashwin.embybyashwin.R;
import com.ashwin.embybyashwin.emby.GlobalClass;

import java.util.ArrayList;

import mediabrowser.apiinteraction.android.AndroidApiClient;
import mediabrowser.model.dto.BaseItemDto;
import mediabrowser.model.dto.ImageOptions;

public class PortraitViewAdapter extends RecyclerView.Adapter<PortraitViewAdapter.ViewHolder>{
    private final AndroidApiClient apiClient;
    private String TAG = PortraitViewAdapter.class.getSimpleName();
    ArrayList<BaseItemDto> mediaList;
    ViewOptions viewOptions;
    ImageOptions options;

    public PortraitViewAdapter(ArrayList<BaseItemDto> medialist, ViewOptions viewoptions){
        mediaList = medialist;
        viewOptions = viewoptions;
        apiClient = GlobalClass.getInstance().getApiClient();

        options = new ImageOptions();
        options.setImageType(viewoptions.getImageType());
        options.setFormat(viewoptions.getImageFormat());
        options.setMaxWidth(viewoptions.getMaxWidth());
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.list_item_portrait_view, parent, false);

        Double viewItem = Double.valueOf(context.getResources().getString(viewOptions.getMediaColumnsRes()));

        Double _itemWidth =(parent.getMeasuredWidth()/ viewItem);
        Double _itemHeight = _itemWidth  * 1.45;
        Integer itemWidth = _itemWidth.intValue();
        Integer itemHeight = _itemHeight.intValue();
        Integer margin = context.getResources().getDimensionPixelSize(R.dimen.media_item_margin);
        Log.e(TAG, "itemWidth->" + itemWidth);

        view.setLayoutParams(new ViewGroup.LayoutParams(itemWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
        PortraitViewAdapter.ViewHolder viewHolder = new PortraitViewAdapter.ViewHolder(view);

        viewHolder.imgPrimary.setLayoutParams(new RelativeLayout.LayoutParams(itemWidth - (margin*2), itemHeight - (margin*2)));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PortraitViewAdapter.ViewHolder holder, int position) {

        BaseItemDto item = mediaList.get(position);

        ImageView imgPrimary = holder.imgPrimary;
        ProgressBar progressBar = holder.progressBar;
        TextView txtMain = holder.txtName;

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

        if (!item.getName().equals("")){
            txtMain.setText(item.getName());
            txtMain.setVisibility(View.VISIBLE);
        }


        if (viewOptions.isShowProgressBar() && item.getCanResume()){
            Integer i = (int) (item.getResumePositionTicks() * 100 /  item.getRunTimeTicks());
            progressBar.setProgress(i);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mediaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final ImageView imgPrimary;
        private final ProgressBar progressBar;
        private final TextView txtName;

        public ViewHolder(View itemView) {
            super(itemView);

            imgPrimary = (ImageView) itemView.findViewById(R.id.img_list_item_portrait_view);
            txtName = (TextView) itemView.findViewById(R.id.txt_list_item_portrait_view_main);
            progressBar = (ProgressBar) itemView.findViewById(R.id.pb_list_item_portrait_view);
        }
    }
}
