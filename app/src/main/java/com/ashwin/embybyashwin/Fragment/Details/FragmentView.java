package com.ashwin.embybyashwin.Fragment.Details;

import android.content.res.Configuration;
import android.os.Bundle;

import android.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ashwin.embybyashwin.Fragment.Home.Adapter.PortraitViewAdapter;
import com.ashwin.embybyashwin.Fragment.Home.Adapter.ViewOptions;
import com.ashwin.embybyashwin.R;
import com.ashwin.embybyashwin.emby.GlobalClass;

import java.util.ArrayList;

import mediabrowser.apiinteraction.Response;
import mediabrowser.apiinteraction.android.AndroidApiClient;
import mediabrowser.model.dto.BaseItemDto;
import mediabrowser.model.entities.SortOrder;
import mediabrowser.model.querying.ItemQuery;
import mediabrowser.model.querying.ItemsResult;


public class FragmentView extends Fragment {
    private ArrayList<BaseItemDto> myMediaList;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_VIEW = "VIEW";
    private static final String ARG_PARENT_ID = "PARENT_ID";

    private String m_VIEW;
    private String m_PARENT_ID;
    private AndroidApiClient apiClient;
    private RecyclerView rvMyMedia;
    private String TAG = FragmentView.class.getSimpleName();
    private Integer spanCount = 3;

    public FragmentView() {
        // Required empty public constructor
    }

    public static FragmentView newInstance(String view, String parentID) {
        FragmentView fragment = new FragmentView();
        Bundle args = new Bundle();
        args.putString(ARG_VIEW, view);
        args.putString(ARG_PARENT_ID, parentID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            m_VIEW = getArguments().getString(ARG_VIEW);
            m_PARENT_ID = getArguments().getString(ARG_PARENT_ID);
        }

        spanCount = Integer.valueOf(getResources().getString(R.string.media_grid_columns));
        apiClient = GlobalClass.getInstance().getApiClient();

        if (myMediaList == null){
            myMediaList = new ArrayList<BaseItemDto>();
        }

        LoadGridView(m_PARENT_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_grid_view, container, false);
        rvMyMedia = (RecyclerView) v.findViewById(R.id.rv_activity_details_main);

        if (myMediaList == null){
            myMediaList = new ArrayList<BaseItemDto>();
        }
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
        spanCount = Integer.valueOf(getResources().getString(R.string.media_grid_columns));
        updateGridView(myMediaList);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        spanCount = Integer.valueOf(getResources().getString(R.string.media_grid_columns));
        updateGridView(myMediaList);
    }

    private void LoadGridView(String parentID) {

        ItemQuery query_2 = new ItemQuery();
        query_2.setRecursive(true);
        query_2.setIncludeItemTypes(new String[]{"Movie","Series"});
        query_2.setSortBy(new String[]{"DateCreated"});
        query_2.setSortOrder(SortOrder.Descending);
        query_2.setParentId(parentID);
        query_2.setUserId(apiClient.getCurrentUserId());

        apiClient.GetItemsAsync(query_2, new Response<ItemsResult>() {
            @Override
            public void onResponse(ItemsResult response) {

                for (BaseItemDto item: response.getItems()) {
                    myMediaList.add(item);
                }
                updateGridView(myMediaList);
            }
        });
    }

    private void updateGridView(ArrayList<BaseItemDto> myList){
        ViewOptions options = new ViewOptions();
        options.setMaxWidth(240);
        options.setMediaColumnsRes(R.string.media_grid_columns);

        PortraitViewAdapter adapter = new PortraitViewAdapter(myList,options);
        rvMyMedia.setAdapter(adapter);
        rvMyMedia.setLayoutManager(new GridLayoutManager(getContext(), spanCount));

    }
}