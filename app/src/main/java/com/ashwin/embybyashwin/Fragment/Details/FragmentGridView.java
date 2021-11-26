package com.ashwin.embybyashwin.Fragment.Details;

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
import com.ashwin.embybyashwin.R;

import java.util.ArrayList;


public class FragmentGridView extends Fragment {
    private ArrayList<MyMedia> myMediaList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_grid_view, container, false);
        RecyclerView rvMyMedia = (RecyclerView) v.findViewById(R.id.rv_activity_details_main);

        if (myMediaList == null){
            myMediaList = new ArrayList<MyMedia>();
        }

        MyMediaAdapter adapter = new MyMediaAdapter(myMediaList);
        rvMyMedia.setAdapter(adapter);
        rvMyMedia.setLayoutManager(new GridLayoutManager(getContext(),3));

        return v;
    }

    public void setLatestList(ArrayList<MyMedia> myList) {
        myMediaList = myList;
    }
}