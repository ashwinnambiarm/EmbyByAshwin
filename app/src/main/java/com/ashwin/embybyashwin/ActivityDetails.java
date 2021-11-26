package com.ashwin.embybyashwin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ashwin.embybyashwin.Fragment.Details.FragmentGridView;
import com.ashwin.embybyashwin.Fragment.Home.Model.MyMedia;
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

public class ActivityDetails extends AppCompatActivity {

    private String TAG = "ActivityDetails";
    private AndroidApiClient apiClient;
    private FragmentGridView fragmentGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        apiClient = GlobalClass.getInstance().getApiClient();

        Intent intent = getIntent();
        String view = intent.getStringExtra("VIEW");
        String parentID = intent.getStringExtra("PARENT_ID");
        Log.e(TAG, view);
        Log.e(TAG, parentID);

        switch (view){
            case "GRID":
                LoadGridView(parentID);
                break;
            case "Item":
                LoadItemView(parentID);
                break;
        }

    }

    private void LoadGridView(String parentID) {
        if (fragmentGrid == null) { fragmentGrid = new FragmentGridView();}

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

                ArrayList<MyMedia> myList = new ArrayList<MyMedia>();
                for (BaseItemDto item: response.getItems()) {
                    MyMedia myMedia = new MyMedia();
                    myMedia.setItemDetials(item);
                    myMedia.setName(item.getName());
                    myMedia.setId(item.getId());
                    myMedia.setThumbanilUrl(apiClient.GetImageUrl(item,options));
                    myList.add(myMedia);
                }

                fragmentGrid.setLatestList(myList);
                loadFragment(fragmentGrid, R.id.fl_activity_details);
            }
        });
    }

    private void LoadItemView(String parentID) {

    }

    private void loadFragment(Fragment fragment, Integer layoutID){
        if (fragment !=null){
            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction()
                    .replace(layoutID, fragment)
                    .addToBackStack(null);
            fragmentTransaction.commit();
            Log.e(TAG, "fragment loaded " + fragment.getClass().getName());
        }else {
            Log.e(TAG, "fragment is null");
        }
    }
}