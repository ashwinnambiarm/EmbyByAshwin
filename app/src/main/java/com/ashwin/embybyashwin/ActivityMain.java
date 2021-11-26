package com.ashwin.embybyashwin;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.ashwin.embybyashwin.Fragment.Home.FragmentLatest;
import com.ashwin.embybyashwin.Fragment.Home.FragmentMyMedia;
import com.ashwin.embybyashwin.Fragment.Home.Model.MyMedia;
import com.ashwin.embybyashwin.Fragment.Login.Model.User;
import com.ashwin.embybyashwin.emby.EmbyConnection;
import com.ashwin.embybyashwin.emby.GlobalClass;

import java.util.ArrayList;

import mediabrowser.apiinteraction.ApiClient;
import mediabrowser.apiinteraction.ConnectionResult;
import mediabrowser.apiinteraction.EmptyResponse;
import mediabrowser.apiinteraction.Response;
import mediabrowser.apiinteraction.android.AndroidApiClient;
import mediabrowser.model.drawing.ImageFormat;
import mediabrowser.model.dto.BaseItemDto;
import mediabrowser.model.dto.ImageOptions;
import mediabrowser.model.dto.UserDto;
import mediabrowser.model.entities.ImageType;
import mediabrowser.model.entities.LocationType;
import mediabrowser.model.entities.SortOrder;
import mediabrowser.model.logging.NullLogger;
import mediabrowser.model.querying.ItemFilter;
import mediabrowser.model.querying.ItemQuery;
import mediabrowser.model.querying.ItemsResult;
import mediabrowser.model.querying.LatestItemsQuery;

public class ActivityMain extends AppCompatActivity {

    private Boolean isLogin = false;
    private String TAG = "ActivityMain";
    private NullLogger logger;
    private EmbyConnection embyConnection;
    private AndroidApiClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnLogout = findViewById(R.id.btn_Logout);

        apiClient = GlobalClass.getInstance().getApiClient();

        LoadMyMedia();
//        LoadLatest();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogout();
            }
        });

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
                        Log.e(TAG, "Last Item " + finalCount);
                        fragmentLatest.setLatestList(myLatestList);
                        loadFragment(fragmentLatest, R.id.fl_home_section_2);
                    }
                }
            });
        }

    }

    private void loadFragment(Fragment fragment, Integer layoutID){
        if (fragment !=null){
            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction()
                    .replace(layoutID, fragment)
                    .addToBackStack(null);
            fragmentTransaction.commit();
            Log.e(TAG, "fragment loaded " + fragment.getTag());
        }else {
            Log.e(TAG, "fragment is null");
        }
    }

    private void userLogout(){
        apiClient.Logout(new EmptyResponse());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        apiClient = null;
    }



}
