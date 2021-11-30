package com.ashwin.embybyashwin.Fragment.Home;

import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.ashwin.embybyashwin.Fragment.Home.Adapter.LandscapeViewAdapter;
import com.ashwin.embybyashwin.Fragment.Home.Adapter.MyMediaAdapter;
import com.ashwin.embybyashwin.Fragment.Home.Adapter.ViewOptions;
import com.ashwin.embybyashwin.Fragment.Home.Model.MyMedia;
import com.ashwin.embybyashwin.Fragment.Login.Model.Server;
import com.ashwin.embybyashwin.R;

import java.util.ArrayList;

import mediabrowser.model.dto.BaseItemDto;
import mediabrowser.model.entities.ImageType;

public class FragmentMyMedia extends Fragment {
    ArrayList<BaseItemDto> myMediaList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_my_media, container, false);

        RecyclerView rvMyMedia = (RecyclerView) v.findViewById(R.id.rv_my_media);

        if (myMediaList == null){
            myMediaList = new ArrayList<BaseItemDto>();
        }


        ViewOptions option =  new ViewOptions();
        option.setShowProgressBar(false);
        option.setImageType(ImageType.Primary);

        LandscapeViewAdapter adapter = new LandscapeViewAdapter(myMediaList, option);

        rvMyMedia.setAdapter(adapter);
        rvMyMedia.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));

        return v;
    }

    public void setMyMediaList (ArrayList<BaseItemDto> mediaList){
        myMediaList = mediaList;
    }
}