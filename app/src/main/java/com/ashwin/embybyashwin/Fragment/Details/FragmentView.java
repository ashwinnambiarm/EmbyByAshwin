package com.ashwin.embybyashwin.Fragment.Details;

import android.os.Bundle;

import android.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ashwin.embybyashwin.Fragment.Home.Adapter.MyMediaAdapter;
import com.ashwin.embybyashwin.Fragment.Home.Model.MyMedia;
import com.ashwin.embybyashwin.R;
import com.ashwin.embybyashwin.emby.GlobalClass;

import java.util.ArrayList;

import mediabrowser.apiinteraction.Response;
import mediabrowser.apiinteraction.android.AndroidApiClient;
import mediabrowser.model.drawing.ImageFormat;
import mediabrowser.model.dto.BaseItemDto;
import mediabrowser.model.dto.ImageOptions;
import mediabrowser.model.entities.ImageType;
import mediabrowser.model.entities.SortOrder;
import mediabrowser.model.querying.ItemQuery;
import mediabrowser.model.querying.ItemsResult;


public class FragmentView extends Fragment {
    private ArrayList<MyMedia> myMediaList;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_VIEW = "VIEW";
    private static final String ARG_PARENT_ID = "PARENT_ID";

    private String m_VIEW;
    private String m_PARENT_ID;
    private AndroidApiClient apiClient;
    private RecyclerView rvMyMedia;
    private String TAG = FragmentView.class.getSimpleName();

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

        apiClient = GlobalClass.getInstance().getApiClient();

        LoadGridView(m_PARENT_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_grid_view, container, false);
        rvMyMedia = (RecyclerView) v.findViewById(R.id.rv_activity_details_main);

        if (myMediaList == null){
            myMediaList = new ArrayList<MyMedia>();
        }
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
        LoadGridView(m_PARENT_ID);
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
                ImageOptions options = new ImageOptions();
                options.setImageType(ImageType.Primary);
                options.setFormat(ImageFormat.Png);
                options.setMaxWidth(240);

                ArrayList<MyMedia> myMediaList = new ArrayList<MyMedia>();
                for (BaseItemDto item: response.getItems()) {
                    MyMedia myMedia = new MyMedia();
                    myMedia.setItemDetials(item);
                    myMedia.setName(item.getName());
                    myMedia.setId(item.getId());
                    myMedia.setThumbanilUrl(apiClient.GetImageUrl(item,options));
                    myMediaList.add(myMedia);
                }
                updatedGridView(myMediaList);
            }
        });
    }

    private void updatedGridView(ArrayList<MyMedia> myList){
        MyMediaAdapter adapter = new MyMediaAdapter(myList);
        rvMyMedia.setAdapter(adapter);
        rvMyMedia.setLayoutManager(new GridLayoutManager(getContext(),3));
    }
}