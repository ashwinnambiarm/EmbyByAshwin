package com.ashwin.embybyashwin.Fragment.Home;

import android.content.Context;
import android.content.Intent;
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

import com.ashwin.embybyashwin.ActivityDetails;
import com.ashwin.embybyashwin.ActivityLogin;
import com.ashwin.embybyashwin.Fragment.Home.Adapter.LatestAdapter;
import com.ashwin.embybyashwin.Fragment.Home.Model.MyMedia;
import com.ashwin.embybyashwin.Fragment.Login.FragmentServers;
import com.ashwin.embybyashwin.R;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import mediabrowser.model.dto.BaseItemDto;


public class FragmentLatest extends Fragment {

    private static final String TAG = FragmentLatest.class.getSimpleName();
    private ArrayList<ArrayList<MyMedia>> latestList;

    FragmentLatest.FragmentLatestOnClickListener listener;

    public interface FragmentLatestOnClickListener{
        void OnClickFragmentLatestViewAll(String itemID);
    }

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

                textView.setText("Latest " + libItem.get(0).getName());
                textView.setTag(libItem.get(0).getId());

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String id = (String) view.getTag();
                        listener.OnClickFragmentLatestViewAll(id);
                    }
                });

                root.addView(textView);
                root.addView(child);

                LatestAdapter adapter = new LatestAdapter(libItem);
                child.setAdapter(adapter);
                child.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));
            }
        }

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentLatest.FragmentLatestOnClickListener){
            listener = (FragmentLatest.FragmentLatestOnClickListener) context;
        }else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentLatestOnClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public void setLatestList (ArrayList mediaList){
        latestList = mediaList;
    }
}