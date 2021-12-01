package com.ashwin.embybyashwin.Fragment.Home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import android.app.Fragment;

import android.util.Log;
import android.util.TypedValue;
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
import com.ashwin.embybyashwin.Fragment.Home.Adapter.PortraitViewAdapter;
import com.ashwin.embybyashwin.Fragment.Home.Adapter.ViewOptions;
import com.ashwin.embybyashwin.Fragment.Home.Model.MyMedia;
import com.ashwin.embybyashwin.Fragment.Login.FragmentServers;
import com.ashwin.embybyashwin.R;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import mediabrowser.model.dto.BaseItemDto;


public class FragmentLatest extends Fragment {

    private static final String TAG = FragmentLatest.class.getSimpleName();
    private ArrayList<ArrayList<BaseItemDto>> latestList;
    private ArrayList<BaseItemDto> parentList;

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

        if (latestList.size() == parentList.size()){
            for (Integer count = 0; count < latestList.size() ; count++) {
                ArrayList<BaseItemDto> latestItem = latestList.get(count);
                BaseItemDto parentItem = parentList.get(count);

                Log.e(TAG, parentItem.getName());

                TextView textView = new TextView(this.getContext());
                RecyclerView child = new RecyclerView(this.getContext());

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                layoutParams.setMargins(0, getResources().getDimensionPixelSize(R.dimen.main_margin), 0, 0);

                textView.setText("Latest " + parentItem.getName());
                textView.setTag(parentItem.getId());
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimensionPixelSize(R.dimen.H1));
                textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
                textView.setTextColor(getResources().getColor(R.color.colorWhite));

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String id = (String) view.getTag();
                        listener.OnClickFragmentLatestViewAll(id);
                    }
                });

                root.addView(textView, layoutParams);
                root.addView(child);
                ViewOptions options = new ViewOptions();
                options.setMaxWidth(240);
                PortraitViewAdapter adapter = new PortraitViewAdapter(latestItem, options);
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
    public void setParentList(ArrayList<BaseItemDto> parentlist) {
        parentList = parentlist;
    }
}