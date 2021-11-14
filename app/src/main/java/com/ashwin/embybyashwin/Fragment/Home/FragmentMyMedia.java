package com.ashwin.embybyashwin.Fragment.Home;

import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ashwin.embybyashwin.Fragment.Home.Adapter.MyMediaAdapter;
import com.ashwin.embybyashwin.Fragment.Home.Model.MyMedia;
import com.ashwin.embybyashwin.Fragment.Login.Model.Server;
import com.ashwin.embybyashwin.R;

import java.util.ArrayList;

public class FragmentMyMedia extends Fragment {
    ArrayList<MyMedia> myMedias;

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

        if (myMedias == null){
            myMedias = new ArrayList<MyMedia>();
        }
        MyMedia myMedia = new MyMedia();

        myMedias.add(myMedia);
        myMedias.add(myMedia);
        myMedias.add(myMedia);
        myMedias.add(myMedia);
        myMedias.add(myMedia);

        MyMediaAdapter adapter = new MyMediaAdapter(myMedias);

        rvMyMedia.setAdapter(adapter);
        rvMyMedia.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));

        return v;
    }
}