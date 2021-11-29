package com.ashwin.embybyashwin.Fragment.Home;

import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ashwin.embybyashwin.Fragment.Home.Adapter.LandscapeViewAdapter;
import com.ashwin.embybyashwin.Fragment.Home.Adapter.ViewOptions;
import com.ashwin.embybyashwin.Fragment.Home.Model.MyMedia;
import com.ashwin.embybyashwin.R;

import java.util.ArrayList;

public class FragmentContinueWatching extends Fragment {
    private ArrayList<MyMedia> mediaList;
    private String TAG = FragmentContinueWatching.class.getSimpleName();

    public FragmentContinueWatching() {
        // Required empty public constructor
    }

    public static FragmentContinueWatching newInstance() {
        FragmentContinueWatching fragment = new FragmentContinueWatching();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_continue_watching, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.rv_continue_watching);

        if (mediaList == null){mediaList = new ArrayList<>(); }

        ViewOptions viewOptions = new ViewOptions();
        viewOptions.setShowProgressBar(true);
        LandscapeViewAdapter adapter = new LandscapeViewAdapter(mediaList, viewOptions);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));
        return v;

    }

    public void setMediaList(ArrayList<MyMedia> myList) {
        mediaList = myList;
    }
}