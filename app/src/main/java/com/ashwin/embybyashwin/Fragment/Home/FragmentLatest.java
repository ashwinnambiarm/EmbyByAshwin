package com.ashwin.embybyashwin.Fragment.Home;

import android.os.Bundle;

import android.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ashwin.embybyashwin.Fragment.Home.Adapter.LatestAdapter;
import com.ashwin.embybyashwin.Fragment.Home.Model.MyMedia;
import com.ashwin.embybyashwin.R;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import mediabrowser.model.dto.BaseItemDto;


public class FragmentLatest extends Fragment {

    private static final String TAG = "FragmentLatest";
    private ArrayList<ArrayList<MyMedia>> latestList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_latest, container, false);

        LinearLayout root = (LinearLayout) v.findViewById(R.id.ll_latest_root);


        if (latestList == null){
            latestList = new ArrayList<>();
        }

        for (ArrayList<MyMedia> libItem: latestList){
            if (libItem.size()>0){
                TextView textView = new TextView(this.getContext());
                RecyclerView child = new RecyclerView(this.getContext());

                textView.setText(libItem.get(0).getName());
                root.addView(textView);
                root.addView(child);

                LatestAdapter adapter = new LatestAdapter(libItem);
                child.setAdapter(adapter);
                child.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));
            }
        }

        return v;
    }

    public void setLatestList (ArrayList mediaList){
        latestList = mediaList;
    }
}