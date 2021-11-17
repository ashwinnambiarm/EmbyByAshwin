package com.ashwin.embybyashwin.Fragment.Home;

import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ashwin.embybyashwin.Fragment.Home.Adapter.LatestAdapter;
import com.ashwin.embybyashwin.Fragment.Home.Model.MyMedia;
import com.ashwin.embybyashwin.R;

import java.util.ArrayList;


public class FragmentLatest extends Fragment {

    private ArrayList<MyMedia> TvShowList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_latest, container, false);

        RecyclerView rvLatestTV = (RecyclerView) v.findViewById(R.id.rv_latest_tv);

        if (TvShowList == null){
            TvShowList = new ArrayList<MyMedia>();
        }

        LatestAdapter adapterTv = new LatestAdapter(TvShowList);

        rvLatestTV.setAdapter(adapterTv);
        rvLatestTV.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));


        return v;
    }

    public void setTvShowList (ArrayList<MyMedia> mediaList){
        TvShowList = mediaList;
    }
}