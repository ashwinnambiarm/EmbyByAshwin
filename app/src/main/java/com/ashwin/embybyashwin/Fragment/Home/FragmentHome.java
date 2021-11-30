package com.ashwin.embybyashwin.Fragment.Home;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import android.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import mediabrowser.model.querying.ItemFilter;
import mediabrowser.model.querying.ItemQuery;
import mediabrowser.model.querying.ItemsResult;


public class FragmentHome extends Fragment {


    private AndroidApiClient apiClient;
    private String TAG = FragmentHome.class.getSimpleName();

    public FragmentHome() {
        // Required empty public constructor
    }

    public static FragmentHome newInstance() {
        FragmentHome fragment = new FragmentHome();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        apiClient = GlobalClass.getInstance().getApiClient();
//        LoadMyMedia();
//        loadContinueWatching();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
        LoadMyMedia();
        loadContinueWatching();
    }

    private void LoadMyMedia(){
        FragmentMyMedia fragmentMyMedia = new FragmentMyMedia();

        ArrayList<MyMedia> myMediaList = new ArrayList<MyMedia>();

        apiClient.GetUserViews(apiClient.getCurrentUserId(), new Response<ItemsResult>(){
            @Override
            public void onResponse(ItemsResult response) {
                ImageOptions options = new ImageOptions();
                options.setImageType(ImageType.Primary);
                options.setFormat(ImageFormat.Png);
                options.setMaxWidth(280);

                ArrayList<BaseItemDto> ParentIds = new ArrayList();

                for (BaseItemDto item: response.getItems()) {
                    Log.e(TAG, "Library  ->" + item.getName()
                            + " Library image url ->" + apiClient.GetImageUrl(item,options));
                    MyMedia myMedia = new MyMedia();
                    myMedia.setItemDetials(item);
                    myMedia.setThumbanilUrl(apiClient.GetImageUrl(item,options));

                    ParentIds.add(item);
                    myMediaList.add(myMedia);


                }

                LoadLatest(ParentIds);

                fragmentMyMedia.setMyMediaList(myMediaList);

                loadFragment(fragmentMyMedia, R.id.fl_home_section_1);
            }
        });
    }


    private void LoadLatest(ArrayList<BaseItemDto> parentIds){
        // TODO: Added Latest Items

        FragmentLatest fragmentLatest = new FragmentLatest();

        ArrayList myLatestList = new ArrayList<>();

        for (Integer count = 0; count < parentIds.size() ; count++) {

            String parentId = parentIds.get(count).getId();

//            Log.e(TAG, "LoadLatest loading Parent ID " + parentId);

            ItemQuery query_2 = new ItemQuery();
//            query_2.setFilters(new ItemFilter[]{ItemFilter.IsNotFolder});
            query_2.setLimit(10);
            query_2.setRecursive(true);
            query_2.setIncludeItemTypes(new String[]{"Movie","Series"});
            query_2.setSortBy(new String[]{"DateCreated"});
            query_2.setSortOrder(SortOrder.Descending);
            query_2.setParentId(parentId);
            query_2.setUserId(apiClient.getCurrentUserId());

            Integer finalCount = count;
            apiClient.GetItemsAsync(query_2, new Response<ItemsResult>(){
                @Override
                public void onResponse(ItemsResult response) {

                    ImageOptions options = new ImageOptions();
                    options.setImageType(ImageType.Primary);
                    options.setFormat(ImageFormat.Png);
                    options.setMaxWidth(240);

                    ArrayList<MyMedia> mytList = new ArrayList<MyMedia>();

                    for (BaseItemDto item: response.getItems()) {
//                        Log.e(TAG, "Parent ID " + parentId + " " + item.getName());
//                        Log.e(TAG, "getType " + item.getType());
//                        Log.e(TAG, "getMediaType " + item.getMediaType());

                        MyMedia myMedia = new MyMedia();
                        myMedia.setItemDetials(item);
                        myMedia.setName(parentIds.get(finalCount).getName());
                        myMedia.setId(parentIds.get(finalCount).getId());
                        myMedia.setThumbanilUrl(apiClient.GetImageUrl(item,options));
                        mytList.add(myMedia);

                    }
                    myLatestList.add(mytList);

                    if (finalCount == (parentIds.size()-1)){
                        Log.e(TAG, "LoadLatest Last Item->" + finalCount);
                        fragmentLatest.setLatestList(myLatestList);
                        loadFragment(fragmentLatest, R.id.fl_home_section_2);
                    }
                }
            });
        }

    }

    private void loadContinueWatching(){
        ItemQuery query = new ItemQuery();
        query.setUserId(apiClient.getCurrentUserId());
        query.setLimit(10);
        query.setRecursive(true);
        query.setFilters(new ItemFilter[]{ItemFilter.IsResumable});

        apiClient.GetItemsAsync(query, new Response<ItemsResult>(){
            @Override
            public void onResponse(ItemsResult response) {

                ArrayList<BaseItemDto> myList = new ArrayList<>();

                for (BaseItemDto item: response.getItems()) {
                    Log.e(TAG, "loadContinueWatching ->"  + item.getName());
                    myList.add(item);
                }

                Log.e(TAG, "loadContinueWatching myList Count->"  + myList.size());
                if (myList.size()>0) {
                    FragmentContinueWatching fragmentContinueWatching = FragmentContinueWatching.newInstance();
                    fragmentContinueWatching.setMediaList(myList);
                    loadFragment(fragmentContinueWatching, R.id.fl_home_section_3);
                }
            }
        });
    }

    private void loadFragment(Fragment fragment, Integer layoutID){
        if (fragment !=null){
            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction()
                    .replace(layoutID, fragment);
            fragmentTransaction.commit();
            Log.e(TAG, "fragment loaded " + fragment.getClass().getSimpleName());
        }else {
            Log.e(TAG, "fragment is null");
        }
    }
}